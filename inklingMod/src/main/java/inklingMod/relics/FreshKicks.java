package inklingMod.relics;

import com.badlogic.gdx.graphics.Texture;

import basemod.abstracts.CustomRelic;
import inklingMod.InklingMod;
import inklingMod.util.TextureLoader;

public class FreshKicks extends CustomRelic {
  public static final String ID = InklingMod.makeID(FreshKicks.class.getSimpleName());
  private static final Texture IMG = TextureLoader.getTexture(InklingMod.makeRelicPath("FreshKicks.png"));
  private static final Texture OUTLINE = TextureLoader
      .getTexture(InklingMod.makeRelicOutlinePath("FreshKicks.png"));

  public FreshKicks() {
    super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
  }

  // NOTE: The effect is applied in `SwimmingPower`, because relics don't seem
  // to have a good way to listen for power changes. We could implement this as
  // a stance, but that's not especially built into basemod either, we'd need
  // to look deeper at how Downfall does it!

  @Override
  public String getUpdatedDescription() {
    return DESCRIPTIONS[0];
  }
}
