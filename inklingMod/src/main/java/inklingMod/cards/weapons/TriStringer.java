package inklingMod.cards.weapons;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import basemod.abstracts.CustomCard;
import inklingMod.InklingMod;
import inklingMod.cards.weapons.shots.TriShot;
import inklingMod.characters.TheInkling;
import inklingMod.powers.weapons.TriStringerPlusPower;
import inklingMod.powers.weapons.TriStringerPower;

public class TriStringer extends CustomCard {
  public static final String ID = InklingMod.makeID(TriStringer.class.getSimpleName());
  private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
  public static final String NAME = cardStrings.NAME;
  public static final String DESCRIPTION = cardStrings.DESCRIPTION;

  public static final String IMG = InklingMod.makeCardPath("TriStringer.png");
  private static final CardRarity RARITY = CardRarity.UNCOMMON;
  private static final CardTarget TARGET = CardTarget.SELF;
  private static final CardType TYPE = CardType.POWER;
  public static final CardColor COLOR = TheInkling.Enums.COLOR_GRAY;

  private static final int COST = 2;

  public TriStringer() {
    super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    this.cardsToPreview = new TriShot();
  }

  @Override
  public void use(AbstractPlayer p, AbstractMonster m) {
    AbstractPower power = this.upgraded ? new TriStringerPlusPower(p) : new TriStringerPower(p);
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