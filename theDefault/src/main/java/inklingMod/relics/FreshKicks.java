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

  // TODO: Actually implement the swimming effect!

  @Override
  public String getUpdatedDescription() {
    return DESCRIPTIONS[0];
  }
}
