package inklingMod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;

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

  // NOTE: We don't know how to have relics listen for power removals, so we
  // have SwimmingPower manually call `onResurface` for us!
  public void onResurface() {
    flash();
    addToBot(new DrawCardAction(1));
  }

  @Override
  public String getUpdatedDescription() {
    return DESCRIPTIONS[0];
  }
}
