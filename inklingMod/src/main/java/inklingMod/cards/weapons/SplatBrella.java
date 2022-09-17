package inklingMod.cards.weapons;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import basemod.abstracts.CustomCard;
import inklingMod.InklingMod;
import inklingMod.cards.weapons.shots.BrellaSplash;
import inklingMod.characters.TheInkling;
import inklingMod.powers.weapons.SplatBrellaPlusPower;
import inklingMod.powers.weapons.SplatBrellaPower;

public class SplatBrella extends CustomCard {
  public static final String ID = InklingMod.makeID(SplatBrella.class.getSimpleName());
  private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
  public static final String NAME = cardStrings.NAME;
  public static final String DESCRIPTION = cardStrings.DESCRIPTION;

  public static final String IMG = InklingMod.makeCardPath("SplatBrella.png");
  private static final CardRarity RARITY = CardRarity.COMMON;
  private static final CardTarget TARGET = CardTarget.SELF;
  private static final CardType TYPE = CardType.POWER;
  public static final CardColor COLOR = TheInkling.Enums.COLOR_GRAY;

  private static final int COST = 1;

  public SplatBrella() {
    super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    this.cardsToPreview = new BrellaSplash();
  }

  @Override
  public void use(AbstractPlayer p, AbstractMonster m) {
    AbstractPower power = this.upgraded ? new SplatBrellaPlusPower(p) : new SplatBrellaPower(p);
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