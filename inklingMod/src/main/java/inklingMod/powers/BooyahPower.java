package inklingMod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import basemod.interfaces.CloneablePowerInterface;
import inklingMod.InklingMod;
import inklingMod.actions.BooyahExhaustAllAction;
import inklingMod.cards.BooyahBomb;
import inklingMod.util.TextureLoader;

public class BooyahPower extends AbstractPower implements CloneablePowerInterface {
  public static final String POWER_ID = InklingMod.makeID(BooyahPower.class.getSimpleName());
  private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
  public static final String NAME = powerStrings.NAME;
  public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

  private static final Texture tex84 = TextureLoader.getTexture(
      InklingMod.makePowerPath("BooyahPower84.png"));
  private static final Texture tex32 = TextureLoader.getTexture(
      InklingMod.makePowerPath("BooyahPower32.png"));

  public BooyahPower(final AbstractCreature owner, final int initialAmount) {
    this.name = NAME;
    this.ID = POWER_ID;

    this.owner = owner;
    this.amount = initialAmount;

    this.type = PowerType.BUFF;
    this.isTurnBased = false;

    this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
    this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

    updateDescription();
  }

  @Override
  public void stackPower(int stackAmount) {
    // Adapted from MantraPower.java in the base game
    super.stackPower(stackAmount);
    if (this.amount >= 16) {
      // Trash this power. (I add this to top because most power things do so?)
      addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));

      // TODO: Somehow keep track of whether a Booyah!+ was involved, and
      // grant a Booyah Bomb+ if so?
      // (And I add this to top because I want to guarantee it makes it into
      // the hand, and isn't blocked by your Booyah card draw effect.)
      addToTop(new MakeTempCardInHandAction(new BooyahBomb()));

      // Exhaust all Booyahs in your hand and deck and discard.
      // (We do this immediately, to clear extra Booyahs out of your hand to
      // make room for your card draw after getting Booyah Bomb if possible;
      // and we also do it again after, to trash the Booyah you just played
      // and just generated.)
      addToTop(new BooyahExhaustAllAction());
      addToBot(new BooyahExhaustAllAction());
    }
  }

  @Override
  public void updateDescription() {
    this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
  }

  @Override
  public AbstractPower makeCopy() {
    return new BooyahPower(this.owner, this.amount);
  }
}