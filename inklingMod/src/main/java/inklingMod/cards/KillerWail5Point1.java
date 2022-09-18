package inklingMod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import inklingMod.InklingMod;
import inklingMod.characters.TheInkling;

public class KillerWail5Point1 extends CustomCard {
  public static final String ID = InklingMod.makeID(KillerWail5Point1.class.getSimpleName());
  private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
  public static final String NAME = cardStrings.NAME;
  public static final String DESCRIPTION = cardStrings.DESCRIPTION;

  public static final String IMG = InklingMod.makeCardPath("KillerWail5Point1.png");
  private static final CardRarity RARITY = CardRarity.RARE;
  private static final CardTarget TARGET = CardTarget.ENEMY;
  private static final CardType TYPE = CardType.ATTACK;
  public static final CardColor COLOR = TheInkling.Enums.COLOR_GRAY;

  private static final int COST = 3;
  private static final int DAMAGE = 3;
  private static final int UPGRADE_PLUS_DMG = 2;

  public KillerWail5Point1() {
    super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

    this.baseDamage = DAMAGE;
    this.exhaust = true;
  }

  @Override
  public void use(AbstractPlayer p, AbstractMonster m) {
    addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
        AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
        AbstractGameAction.AttackEffect.SLASH_VERTICAL));
    addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
        AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
        AbstractGameAction.AttackEffect.SLASH_VERTICAL));
    addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
        AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
        AbstractGameAction.AttackEffect.SLASH_VERTICAL));
  }

  @Override
  public void upgrade() {
    if (!upgraded) {
      upgradeName();
      upgradeDamage(UPGRADE_PLUS_DMG);
      initializeDescription();
    }
  }
}