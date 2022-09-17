package inklingMod.powers.weapons;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import basemod.interfaces.CloneablePowerInterface;
import inklingMod.InklingMod;
import inklingMod.cards.weapons.TriStringer;
import inklingMod.cards.weapons.shots.TriShot;

public class TriStringerPower extends AbstractWeaponPower implements CloneablePowerInterface {
  public static final String POWER_ID = InklingMod.makeID(TriStringerPower.class.getSimpleName());
  private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
  public static final String NAME = powerStrings.NAME;
  public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

  public TriStringerPower(final AbstractCreature owner) {
    this.name = NAME;
    this.ID = POWER_ID;
    this.owner = owner;
  }

  @Override
  public void atStartOfTurn() {
    flash();
    AbstractCard card = new TriShot();
    addToBot(new MakeTempCardInHandAction(card, 3, false));
  }

  @Override
  protected AbstractCard createWeaponPowerCard() {
    AbstractCard card = new TriStringer();
    return card;
  }

  @Override
  public void updateDescription() {
    this.description = DESCRIPTIONS[0];
  }

  @Override
  public AbstractPower makeCopy() {
    return new TriStringerPower(this.owner);
  }
}