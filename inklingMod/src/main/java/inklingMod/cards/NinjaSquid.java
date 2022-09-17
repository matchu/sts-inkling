package inklingMod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import inklingMod.InklingMod;
import inklingMod.characters.TheInkling;
import inklingMod.powers.NinjaSquidPower;

public class NinjaSquid extends CustomCard {
  public static final String ID = InklingMod.makeID(NinjaSquid.class.getSimpleName());
  private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
  public static final String NAME = cardStrings.NAME;
  public static final String DESCRIPTION = cardStrings.DESCRIPTION;

  public static final String IMG = InklingMod.makeCardPath("NinjaSquid.png");
  private static final CardRarity RARITY = CardRarity.RARE;
  private static final CardTarget TARGET = CardTarget.SELF;
  private static final CardType TYPE = CardType.POWER;
  public static final CardColor COLOR = TheInkling.Enums.COLOR_GRAY;

  private static final int COST = 3;

  public NinjaSquid() {
    super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    this.isEthereal = true;
  }

  @Override
  public void use(AbstractPlayer p, AbstractMonster m) {
    addToBot(new ApplyPowerAction(p, p, new NinjaSquidPower(p)));
  }

  @Override
  public void upgrade() {
    if (!upgraded) {
      upgradeName();
      this.isEthereal = false;
      this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
      initializeDescription();
    }
  }
}