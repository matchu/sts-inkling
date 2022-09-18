package inklingMod.cards.weapons.shots;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import inklingMod.InklingMod;
import inklingMod.powers.InkPower;

public class BrellaSplash extends CustomCard {
  public static final String ID = InklingMod.makeID(BrellaSplash.class.getSimpleName());
  private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
  public static final String NAME = cardStrings.NAME;
  public static final String DESCRIPTION = cardStrings.DESCRIPTION;

  public static final String IMG = InklingMod.makeCardPath("SplatBrella.png");
  private static final CardRarity RARITY = CardRarity.SPECIAL;
  private static final CardTarget TARGET = CardTarget.SELF_AND_ENEMY;
  private static final CardType TYPE = CardType.SKILL;
  public static final CardColor COLOR = CardColor.COLORLESS;

  private static final int COST = 1;
  private static final int BLOCK = 8;
  private static final int INK = 2;
  private static final int UPGRADE_PLUS_BLOCK = 2;
  private static final int UPGRADE_PLUS_INK = 2;

  public BrellaSplash() {
    super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

    this.baseBlock = BLOCK;
    this.baseMagicNumber = INK;
    this.magicNumber = this.baseMagicNumber;
    this.isEthereal = true;
    this.exhaust = true;
  }

  @Override
  public void use(AbstractPlayer p, AbstractMonster m) {
    addToBot(new GainBlockAction(p, p, this.block));
    addToBot(new ApplyPowerAction(m, p, new InkPower(m, this.magicNumber), this.magicNumber));
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