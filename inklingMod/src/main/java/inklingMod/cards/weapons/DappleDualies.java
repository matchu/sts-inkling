package inklingMod.cards.weapons;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import basemod.abstracts.CustomCard;
import inklingMod.InklingMod;
import inklingMod.cards.weapons.shots.DappleShot;
import inklingMod.characters.TheInkling;
import inklingMod.powers.weapons.DappleDualiesPlusPower;
import inklingMod.powers.weapons.DappleDualiesPower;

public class DappleDualies extends CustomCard {
  public static final String ID = InklingMod.makeID(DappleDualies.class.getSimpleName());
  private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
  public static final String NAME = cardStrings.NAME;
  public static final String DESCRIPTION = cardStrings.DESCRIPTION;

  public static final String IMG = InklingMod.makeCardPath("DappleDualies.png");
  private static final CardRarity RARITY = CardRarity.COMMON;
  private static final CardTarget TARGET = CardTarget.SELF;
  private static final CardType TYPE = CardType.POWER;
  public static final CardColor COLOR = TheInkling.Enums.COLOR_GRAY;

  private static final int COST = 1;

  public DappleDualies() {
    super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    this.cardsToPreview = new DappleShot();
    // TODO: Use Downfall's "Tinkerer's Toolbox" code to rotate thru previews.
  }

  @Override
  public void use(AbstractPlayer p, AbstractMonster m) {
    AbstractPower power = this.upgraded ? new DappleDualiesPlusPower(p) : new DappleDualiesPower(p);
    addToBot(new ApplyPowerAction(p, p, power));
  }

  @Override
  public void upgrade() {
    if (!upgraded) {
      upgradeName();
      this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
      this.cardsToPreview.upgrade();
      initializeDescription();
    }
  }
}