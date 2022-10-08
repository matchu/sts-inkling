package inklingMod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import basemod.interfaces.CloneablePowerInterface;
import inklingMod.InklingMod;
import inklingMod.util.TextureLoader;

public class SplatWallPower extends AbstractPower implements CloneablePowerInterface {
  public static final String POWER_ID = InklingMod.makeID(SplatWallPower.class.getSimpleName());
  private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
  public static final String NAME = powerStrings.NAME;
  public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

  private static final Texture tex84 = TextureLoader.getTexture(
      InklingMod.makePowerPath("SplatWallPower84.png"));
  private static final Texture tex32 = TextureLoader.getTexture(
      InklingMod.makePowerPath("SplatWallPower32.png"));

  public SplatWallPower(final AbstractCreature owner, final int initialAmount) {
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
  public void updateDescription() {
    this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
  }

  @Override
  public void stackPower(int stackAmount) {
    // Copied from FlameBarrierPower.java in the base game
    if (this.amount == -1) {
      InklingMod.logger.info(this.name + " does not stack");
      return;
    }
    this.fontScale = 8.0F;
    this.amount += stackAmount;
    updateDescription();
  }

  @Override
  public int onAttacked(DamageInfo info, int damageAmount) {
    // Adapted from FlameBarrierPower.java in the base game
    if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS
        && info.owner != this.owner) {
      flash();
      addToTop(new ApplyPowerAction(info.owner, this.owner, new InkPower(info.owner, this.amount), this.amount));
    }
    return damageAmount;
  }

  @Override
  public void atStartOfTurn() {
    addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
  }

  @Override
  public AbstractPower makeCopy() {
    return new SplatWallPower(this.owner, this.amount);
  }
}