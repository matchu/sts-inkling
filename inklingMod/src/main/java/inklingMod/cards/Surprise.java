package inklingMod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import inklingMod.InklingMod;
import inklingMod.characters.TheInkling;
import inklingMod.powers.SurprisePower;
import inklingMod.powers.SwimmingPower;

public class Surprise extends CustomCard {
  public static final String ID = InklingMod.makeID(Surprise.class.getSimpleName());
  private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
  public static final String NAME = cardStrings.NAME;
  public static final String DESCRIPTION = cardStrings.DESCRIPTION;

  public static final String IMG = InklingMod.makeCardPath("Surprise.png");
  private static final CardRarity RARITY = CardRarity.UNCOMMON;
  private static final CardTarget TARGET = CardTarget.SELF;
  private static final CardType TYPE = CardType.SKILL;
  public static final CardColor COLOR = TheInkling.Enums.COLOR_GRAY;

  private static final int COST = 1;
  private static final int INK = 6;
  private static final int UPGRADE_PLUS_INK = 2;

  public Surprise() {
    super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

    this.baseMagicNumber = INK;
    this.magicNumber = this.baseMagicNumber;
  }

  @Override
  public void use(AbstractPlayer p, AbstractMonster m) {
    addToBot(new ApplyPowerAction(p, p, new SwimmingPower(p)));
    addToBot(new ApplyPowerAction(p, p, new SurprisePower(p, this.magicNumber)));
  }

  @Override
  public void upgrade() {
    if (!upgraded) {
      upgradeName();
      upgradeMagicNumber(UPGRADE_PLUS_INK);
      initializeDescription();
    }
  }
}