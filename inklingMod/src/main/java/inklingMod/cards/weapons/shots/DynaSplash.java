package inklingMod.cards.weapons.shots;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import inklingMod.InklingMod;
import inklingMod.powers.InkPower;

public class DynaSplash extends CustomCard {
  public static final String ID = InklingMod.makeID(DynaSplash.class.getSimpleName());
  private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
  public static final String NAME = cardStrings.NAME;
  public static final String DESCRIPTION = cardStrings.DESCRIPTION;

  public static final String IMG = InklingMod.makeCardPath("DynamoRoller.png");
  private static final CardRarity RARITY = CardRarity.SPECIAL;
  private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
  private static final CardType TYPE = CardType.ATTACK;
  public static final CardColor COLOR = CardColor.COLORLESS;

  private static final int COST = 1;
  private static final int DAMAGE = 16;
  private static final int UPGRADE_PLUS_DMG = 4;

  public DynaSplash() {
    super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

    this.baseDamage = DAMAGE;
    this.isMultiDamage = true;
    this.isEthereal = true;
    this.exhaust = true;
  }

  @Override
  public void use(AbstractPlayer p, AbstractMonster m) {
    addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn,
        AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
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