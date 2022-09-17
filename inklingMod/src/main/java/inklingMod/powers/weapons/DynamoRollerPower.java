package inklingMod.powers.weapons;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import basemod.interfaces.CloneablePowerInterface;
import inklingMod.InklingMod;
import inklingMod.cards.weapons.DynamoRoller;
import inklingMod.cards.weapons.shots.DynaRoll;
import inklingMod.cards.weapons.shots.DynaSplash;

public class DynamoRollerPower extends AbstractWeaponPower implements CloneablePowerInterface {
  public static final String POWER_ID = InklingMod.makeID(DynamoRollerPower.class.getSimpleName());
  private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
  public static final String NAME = powerStrings.NAME;
  public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

  public DynamoRollerPower(final AbstractCreature owner) {
    this.name = NAME;
    this.ID = POWER_ID;
    this.owner = owner;
  }

  @Override
  public void atStartOfTurn() {
    flash();

    AbstractCard card1 = new DynaRoll();
    addToBot(new MakeTempCardInHandAction(card1, 1, false));

    AbstractCard card2 = new DynaSplash();
    addToBot(new MakeTempCardInHandAction(card2, 1, false));
  }

  @Override
  protected AbstractCard createWeaponPowerCard() {
    AbstractCard card = new DynamoRoller();
    return card;
  }

  @Override
  public void updateDescription() {
    this.description = DESCRIPTIONS[0];
  }

  @Override
  public AbstractPower makeCopy() {
    return new DynamoRollerPower(this.owner);
  }
}