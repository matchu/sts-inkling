package inklingMod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import inklingMod.InklingMod;
import inklingMod.characters.TheInkling;

public class BooyahBomb extends CustomCard {
  public static final String ID = InklingMod.makeID(BooyahBomb.class.getSimpleName());
  private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
  public static final String NAME = cardStrings.NAME;
  public static final String DESCRIPTION = cardStrings.DESCRIPTION;

  public static final String IMG = InklingMod.makeCardPath("BooyahBomb.png");
  private static final CardRarity RARITY = CardRarity.SPECIAL;
  private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
  private static final CardType TYPE = CardType.ATTACK;
  public static final CardColor COLOR = TheInkling.Enums.COLOR_GRAY;

  private static final int COST = 3;
  private static final int DAMAGE = 20;
  private static final int NUM_ATTACKS = 3;
  private static final int UPGRADE_PLUS_NUM_ATTACKS = 1;

  public BooyahBomb() {
    super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

    this.retain = true;
    this.exhaust = true;
    this.baseDamage = DAMAGE;
    this.baseMagicNumber = NUM_ATTACKS;
    this.magicNumber = this.baseMagicNumber;
    this.cardsToPreview = new Booyah();
  }

  @Override
  public void use(AbstractPlayer p, AbstractMonster m) {
    for (int i = 0; i < this.magicNumber; i++) {
      AttackEffect effect = i == 0 ? AbstractGameAction.AttackEffect.SLASH_HORIZONTAL
          : AbstractGameAction.AttackEffect.SLASH_VERTICAL;
      addToBot(new DamageAllEnemiesAction(p, damage, damageTypeForTurn, effect));
    }

    AbstractCard generatedCard = new Booyah();
    if (this.upgraded) {
      generatedCard.upgrade();
    }
    addToBot(new MakeTempCardInHandAction(generatedCard));
  }

  @Override
  public void upgrade() {
    if (!upgraded) {
      upgradeName();
      upgradeMagicNumber(UPGRADE_PLUS_NUM_ATTACKS);
      this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
      this.cardsToPreview.upgrade();
      initializeDescription();
    }
  }
}