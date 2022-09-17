package inklingMod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import inklingMod.InklingMod;
import inklingMod.characters.TheInkling;

public class FizzyBomb extends CustomCard {
  public static final String ID = InklingMod.makeID(FizzyBomb.class.getSimpleName());
  private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
  public static final String NAME = cardStrings.NAME;
  public static final String DESCRIPTION = cardStrings.DESCRIPTION;

  public static final String IMG = InklingMod.makeCardPath("FizzyBomb.png");
  private static final CardRarity RARITY = CardRarity.UNCOMMON;
  private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
  private static final CardType TYPE = CardType.ATTACK;
  public static final CardColor COLOR = TheInkling.Enums.COLOR_GRAY;

  private static final int COST = -1;
  private static final int DAMAGE = 6;
  private static final int INK = 2;

  public FizzyBomb() {
    super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

    this.baseDamage = DAMAGE;
    this.baseMagicNumber = INK;
    this.magicNumber = this.baseMagicNumber;
  }

  @Override
  public void use(AbstractPlayer p, AbstractMonster m) {
    // TODO!
  }

  @Override
  public void upgrade() {
    if (!upgraded) {
      upgradeName();
      // TODO!
      initializeDescription();
    }
  }
}