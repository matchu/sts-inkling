package inklingMod.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import inklingMod.InklingMod;
import inklingMod.characters.TheInkling;

public class Defend extends CustomCard {
  public static final String ID = InklingMod.makeID(Defend.class.getSimpleName());
  private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
  public static final String NAME = cardStrings.NAME;
  public static final String DESCRIPTION = cardStrings.DESCRIPTION;

  public static final String IMG = InklingMod.makeCardPath("Defend.png");
  private static final CardRarity RARITY = CardRarity.BASIC;
  private static final CardTarget TARGET = CardTarget.SELF;
  private static final CardType TYPE = CardType.SKILL;
  public static final CardColor COLOR = TheInkling.Enums.COLOR_GRAY;

  private static final int COST = 1;
  private static final int BLOCK = 5;
  private static final int UPGRADE_PLUS_BLOCK = 3;

  public Defend() {
    super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

    baseBlock = BLOCK;

    // A character's basic defend cards need this!
    this.tags.add(CardTags.STARTER_DEFEND);
  }

  @Override
  public void use(AbstractPlayer p, AbstractMonster m) {
    addToBot(new GainBlockAction(p, p, block));
  }

  @Override
  public void upgrade() {
    if (!upgraded) {
      upgradeName();
      upgradeBlock(UPGRADE_PLUS_BLOCK);
      initializeDescription();
    }
  }
}
