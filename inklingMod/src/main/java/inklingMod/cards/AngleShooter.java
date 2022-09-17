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

public class AngleShooter extends CustomCard {
  public static final String ID = InklingMod.makeID(AngleShooter.class.getSimpleName());
  private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
  public static final String NAME = cardStrings.NAME;
  public static final String DESCRIPTION = cardStrings.DESCRIPTION;

  public static final String IMG = InklingMod.makeCardPath("AngleShooter.png");
  private static final CardRarity RARITY = CardRarity.COMMON;
  private static final CardTarget TARGET = CardTarget.ENEMY;
  private static final CardType TYPE = CardType.ATTACK;
  public static final CardColor COLOR = TheInkling.Enums.COLOR_GRAY;

  private static final int COST = 1;
  private static final int DAMAGE = 4;
  private static final int UPGRADE_PLUS_DMG = 2;

  public AngleShooter() {
    super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

    baseDamage = DAMAGE;
  }

  @Override
  public void use(AbstractPlayer p, AbstractMonster m) {
    boolean enemyHasBlock = m.currentBlock > 0;
    addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
        AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
        AbstractGameAction.AttackEffect.SLASH_VERTICAL));
    if (enemyHasBlock) {
      addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
          AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }
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