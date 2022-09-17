package inklingMod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import basemod.interfaces.CloneablePowerInterface;
import inklingMod.InklingMod;
import inklingMod.util.TextureLoader;

public class NinjaSquidPower extends AbstractPower implements CloneablePowerInterface {
  public static final String POWER_ID = InklingMod.makeID(NinjaSquidPower.class.getSimpleName());
  private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
  public static final String NAME = powerStrings.NAME;
  public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

  private static final Texture tex84 = TextureLoader.getTexture(
      InklingMod.makePowerPath("NinjaSquidPower84.png"));
  private static final Texture tex32 = TextureLoader.getTexture(
      InklingMod.makePowerPath("NinjaSquidPower32.png"));

  public NinjaSquidPower(final AbstractCreature owner) {
    this.name = NAME;
    this.ID = POWER_ID;

    this.owner = owner;
    this.amount = -1;

    this.type = PowerType.BUFF;
    this.isTurnBased = false;

    this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
    this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

    updateDescription();
  }

  public float atDamageFinalReceive(float damage, DamageInfo.DamageType type) {
    if (type == DamageInfo.DamageType.NORMAL && damage > 1.0F) {
      damage = 1.0F;
    }
    return damage;
  }

  @Override
  public void updateDescription() {
    this.description = DESCRIPTIONS[0];
  }

  @Override
  public AbstractPower makeCopy() {
    return new NinjaSquidPower(this.owner);
  }
}