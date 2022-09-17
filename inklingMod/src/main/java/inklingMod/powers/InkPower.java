package inklingMod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import basemod.interfaces.CloneablePowerInterface;
import inklingMod.InklingMod;
import inklingMod.util.TextureLoader;

public class InkPower extends AbstractPower implements CloneablePowerInterface {
  public static final String POWER_ID = InklingMod.makeID(InkPower.class.getSimpleName());
  private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
  public static final String NAME = powerStrings.NAME;
  public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

  private static final Texture tex84 = TextureLoader.getTexture(
      InklingMod.makePowerPath("ink_power84.png"));
  private static final Texture tex32 = TextureLoader.getTexture(
      InklingMod.makePowerPath("ink_power32.png"));

  public InkPower(AbstractCreature owner, int initialAmount) {
    this.name = NAME;
    this.ID = POWER_ID;

    this.owner = owner;
    this.amount = initialAmount;

    this.type = PowerType.DEBUFF;
    this.isTurnBased = false;

    // We load those txtures here.
    this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
    this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

    updateDescription();
  }

  public void updateDescription() {
    this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
  }

  @Override
  public float atDamageReceive(float damage, DamageType damageType) {
    return damage + this.amount;
  }

  @Override
  public void atEndOfTurn(boolean isPlayer) {
    // In Java, dividing a positive integer automatically rounds-down the result.
    int newAmount = this.amount / 2;

    if (newAmount == 0) {
      addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    } else {
      int amountToRemove = this.amount - newAmount;
      addToBot(new ReducePowerAction(this.owner, this.owner, POWER_ID, amountToRemove));
    }
  }

  @Override
  public AbstractPower makeCopy() {
    return new InkPower(this.owner, this.amount);
  }
}
