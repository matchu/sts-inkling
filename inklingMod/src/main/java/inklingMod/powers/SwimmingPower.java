package inklingMod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import basemod.interfaces.CloneablePowerInterface;
import inklingMod.InklingMod;
import inklingMod.relics.FreshKicks;
import inklingMod.util.TextureLoader;

public class SwimmingPower extends AbstractPower implements CloneablePowerInterface {
  public static final String POWER_ID = InklingMod.makeID(SwimmingPower.class.getSimpleName());
  private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
  public static final String NAME = powerStrings.NAME;
  public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

  private static final Texture tex84 = TextureLoader.getTexture(
      InklingMod.makePowerPath("swimming_power84.png"));
  private static final Texture tex32 = TextureLoader.getTexture(
      InklingMod.makePowerPath("swimming_power32.png"));

  public SwimmingPower(final AbstractCreature owner) {
    this.name = NAME;
    this.ID = POWER_ID;

    this.owner = owner;
    this.amount = -1;

    this.type = PowerType.BUFF;
    this.isTurnBased = false;

    // We load those txtures here.
    this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
    this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

    updateDescription();
  }

  public void updateDescription() {
    this.description = DESCRIPTIONS[0];
  }

  @Override
  public boolean canPlayCard(AbstractCard card) {
    return (card.type != AbstractCard.CardType.ATTACK);
  }

  @Override
  public void onAfterCardPlayed(AbstractCard card) {
    flash();
    addToBot(new GainEnergyAction(1));
  }

  @Override
  public void atStartOfTurn() {
    flash();
    addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));

    // It's hard for relics to listen for this event, so we check for the
    // relics here, and trigger their stuff on their behalf!
    if (this.owner.isPlayer) {
      AbstractPlayer player = (AbstractPlayer) this.owner;
      if (player.hasRelic(FreshKicks.ID)) {
        player.getRelic(FreshKicks.ID).flash();
        addToBot(new DrawCardAction(1));
      }
    }
  }

  @Override
  public AbstractPower makeCopy() {
    return new SwimmingPower(this.owner);
  }
}
