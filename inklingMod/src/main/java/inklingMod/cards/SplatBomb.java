package inklingMod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import inklingMod.InklingMod;
import inklingMod.characters.TheInkling;
import inklingMod.powers.InkPower;

public class SplatBomb extends CustomCard {
  public static final String ID = InklingMod.makeID(SplatBomb.class.getSimpleName());
  private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
  public static final String NAME = cardStrings.NAME;
  public static final String DESCRIPTION = cardStrings.DESCRIPTION;

  public static final String IMG = InklingMod.makeCardPath("SplatBomb.png");
  private static final CardRarity RARITY = CardRarity.COMMON;
  private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
  private static final CardType TYPE = CardType.SKILL;
  public static final CardColor COLOR = TheInkling.Enums.COLOR_GRAY;

  private static final int COST = 1;
  private static final int INK_AMOUNT = 5;
  private static final int UPGRADE_ADDITIONAL_INK_AMOUNT = 3;

  public SplatBomb() {
    super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

    this.baseMagicNumber = INK_AMOUNT;
    this.magicNumber = this.baseMagicNumber;
  }

  @Override
  public void use(AbstractPlayer p, AbstractMonster m) {
    // Copied from Cripping Cloud, idk why they're so particular about non-dead tho.
    // Could just be to speed up the UI in certain cases where cards are
    // auto-played?
    if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
      for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
        if (!monster.isDead && !monster.isDying) {
          addToBot(new ApplyPowerAction(
              monster, p, new InkPower(monster, this.magicNumber), this.magicNumber));
        }
      }
    }
  }

  @Override
  public void upgrade() {
    if (!upgraded) {
      upgradeName();
      upgradeMagicNumber(UPGRADE_ADDITIONAL_INK_AMOUNT);
      initializeDescription();
    }
  }
}
