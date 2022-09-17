package inklingMod.powers.weapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import inklingMod.InklingMod;
import inklingMod.util.TextureLoader;

public abstract class AbstractWeaponPower extends AbstractPower {
  private static final Texture tex84 = TextureLoader.getTexture(
      InklingMod.makePowerPath("AnyWeaponPower84.png"));
  private static final Texture tex32 = TextureLoader.getTexture(
      InklingMod.makePowerPath("AnyWeaponPower32.png"));

  AbstractWeaponPower() {
    this.amount = -1;
    this.type = PowerType.BUFF;
    this.isTurnBased = false;

    this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
    this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

    updateDescription();
  }

  protected abstract AbstractCard createWeaponPowerCard();

  @Override
  public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
    // When a new weapon power is applied to us, remove this weapon power,
    // and add our card to the hand.
    if (target == this.owner && AbstractWeaponPower.class.isInstance(power)) {
      // NOTE: If this is another copy of the same weapon power, it already
      // stacked onto *this* copy of the weapon power. So in that case, we
      // shouldn't remove ourselves; we should *just* return the card.
      if (power.ID != this.ID) {
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
      }
      if (this.owner.isPlayer) {
        AbstractCard card = createWeaponPowerCard();
        addToBot(new MakeTempCardInDiscardAction(card, 1));
      }
    }
  }
}
