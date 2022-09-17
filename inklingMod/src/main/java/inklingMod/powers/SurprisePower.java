package inklingMod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import basemod.interfaces.CloneablePowerInterface;
import inklingMod.InklingMod;
import inklingMod.util.TextureLoader;

public class SurprisePower extends AbstractPower implements CloneablePowerInterface {
  public static final String POWER_ID = InklingMod.makeID(SurprisePower.class.getSimpleName());
  private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
  public static final String NAME = powerStrings.NAME;
  public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

  private static final Texture tex84 = TextureLoader.getTexture(
      InklingMod.makePowerPath("SurprisePower84.png"));
  private static final Texture tex32 = TextureLoader.getTexture(
      InklingMod.makePowerPath("SurprisePower32.png"));

  public SurprisePower(final AbstractCreature owner, final int amount) {
    this.name = NAME;
    this.ID = POWER_ID;

    this.owner = owner;
    this.amount = amount;

    this.type = PowerType.BUFF;
    this.isTurnBased = false;

    this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
    this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

    updateDescription();
  }

  // NOTE: We don't know how to have powers listen for power removals, so we
  // have SwimmingPower manually call `onResurface` for us!
  public void onResurface() {
    flash();
    if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
      AbstractMonster monster = AbstractDungeon.getMonsters().getRandomMonster(
          null, true, AbstractDungeon.cardRandomRng);
      addToBot(new ApplyPowerAction(
          monster, this.owner, new InkPower(monster, this.amount), this.amount));
    }
    addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
  }

  @Override
  public void updateDescription() {
    this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
  }

  @Override
  public AbstractPower makeCopy() {
    return new SurprisePower(this.owner, this.amount);
  }
}