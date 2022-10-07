package inklingMod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import inklingMod.InklingMod;
import inklingMod.characters.TheInkling;
import inklingMod.powers.SplatWallPower;

public class SplatWall extends CustomCard {
  public static final String ID = InklingMod.makeID(SplatWall.class.getSimpleName());
  private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
  public static final String NAME = cardStrings.NAME;
  public static final String DESCRIPTION = cardStrings.DESCRIPTION;

  public static final String IMG = InklingMod.makeCardPath("SplatWall.png");
  private static final CardRarity RARITY = CardRarity.COMMON;
  private static final CardTarget TARGET = CardTarget.SELF;
  private static final CardType TYPE = CardType.SKILL;
  public static final CardColor COLOR = TheInkling.Enums.COLOR_GRAY;

  private static final int COST = 2;
  private static final int BLOCK = 12;
  private static final int UPGRADE_PLUS_BLOCK = 4;
  private static final int INK = 3;
  private static final int UPGRADE_PLUS_INK = 1;

  public SplatWall() {
    super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

    this.baseBlock = BLOCK;
    this.baseMagicNumber = INK;
    this.magicNumber = this.baseMagicNumber;
  }

  @Override
  public void use(AbstractPlayer p, AbstractMonster m) {
    addToBot(new GainBlockAction(p, p, this.block));
    addToBot(new ApplyPowerAction(p, p, new SplatWallPower(p, this.magicNumber), this.magicNumber));
  }

  @Override
  public void upgrade() {
    if (!upgraded) {
      upgradeName();
      upgradeBlock(UPGRADE_PLUS_BLOCK);
      upgradeMagicNumber(UPGRADE_PLUS_INK);
      initializeDescription();
    }
  }
}