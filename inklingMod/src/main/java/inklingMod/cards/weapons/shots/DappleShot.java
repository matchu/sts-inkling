package inklingMod.cards.weapons.shots;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import inklingMod.InklingMod;

public class DappleShot extends CustomCard {
  public static final String ID = InklingMod.makeID(DappleShot.class.getSimpleName());
  private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
  public static final String NAME = cardStrings.NAME;
  public static final String DESCRIPTION = cardStrings.DESCRIPTION;

  public static final String IMG = InklingMod.makeCardPath("DappleDualies.png");
  private static final CardRarity RARITY = CardRarity.SPECIAL;
  private static final CardTarget TARGET = CardTarget.ENEMY;
  private static final CardType TYPE = CardType.ATTACK;
  public static final CardColor COLOR = CardColor.COLORLESS;

  private static final int COST = 1;
  private static final int DAMAGE = 5;
  private static final int UPGRADE_PLUS_DMG = 1;

  public DappleShot() {
    super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

    this.baseDamage = DAMAGE;
    this.isEthereal = true;
    this.exhaust = true;
  }

  @Override
  public void use(AbstractPlayer p, AbstractMonster m) {
    addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
        AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
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