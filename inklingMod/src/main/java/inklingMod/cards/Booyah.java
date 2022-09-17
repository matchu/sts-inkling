package inklingMod.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import inklingMod.InklingMod;
import inklingMod.characters.TheInkling;

public class Booyah extends CustomCard {
  public static final String ID = InklingMod.makeID(Booyah.class.getSimpleName());
  private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
  public static final String NAME = cardStrings.NAME;
  public static final String DESCRIPTION = cardStrings.DESCRIPTION;

  public static final String IMG = InklingMod.makeCardPath("Booyah.png");
  private static final CardRarity RARITY = CardRarity.RARE;
  private static final CardTarget TARGET = CardTarget.SELF;
  private static final CardType TYPE = CardType.SKILL;
  public static final CardColor COLOR = TheInkling.Enums.COLOR_GRAY;

  private static final int COST = 0;

  public Booyah() {
    super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
  }

  @Override
  public void use(AbstractPlayer p, AbstractMonster m) {
    addToBot(new MakeTempCardInDrawPileAction(new Booyah(), 1, true, true));
    addToBot(new DrawCardAction(1));
  }

  @Override
  public void upgrade() {
    if (!upgraded) {
      upgradeName();
      this.isInnate = true;
      this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
      initializeDescription();
    }
  }
}