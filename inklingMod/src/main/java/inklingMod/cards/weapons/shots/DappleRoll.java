package inklingMod.cards.weapons.shots;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import inklingMod.InklingMod;

public class DappleRoll extends CustomCard {
  public static final String ID = InklingMod.makeID(DappleRoll.class.getSimpleName());
  private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
  public static final String NAME = cardStrings.NAME;
  public static final String DESCRIPTION = cardStrings.DESCRIPTION;

  public static final String IMG = InklingMod.makeCardPath("DappleDualies.png");
  private static final CardRarity RARITY = CardRarity.SPECIAL;
  private static final CardTarget TARGET = CardTarget.SELF;
  private static final CardType TYPE = CardType.SKILL;
  public static final CardColor COLOR = CardColor.COLORLESS;

  private static final int COST = 0;
  private static final int BLOCK = 3;
  private static final int UPGRADE_PLUS_BLOCK = 2;

  public DappleRoll() {
    super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

    this.baseBlock = BLOCK;
    this.isEthereal = true;
    this.exhaust = true;
  }

  @Override
  public void use(AbstractPlayer p, AbstractMonster m) {
    addToBot(new GainBlockAction(p, p, this.block));
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