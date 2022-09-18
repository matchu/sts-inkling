package inklingMod.actions;

import java.util.ArrayList;
import java.util.List;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class FizzyBombAction extends AbstractGameAction {
  private final AbstractPlayer player;
  private final boolean freeToPlayOnce;
  private final int energyOnUse;
  private final int damage;
  private final DamageType damageType;

  public FizzyBombAction(
      final AbstractPlayer player, final boolean freeToPlayOnce,
      final int energyOnUse, final int damage, final DamageType damageType) {
    this.player = player;
    this.freeToPlayOnce = freeToPlayOnce;
    this.energyOnUse = energyOnUse;
    this.damage = damage;
    this.damageType = damageType;
    // Not sure what these do, but the other X-cost actions I see are doing it too!
    this.duration = Settings.ACTION_DUR_XFAST;
    this.actionType = AbstractGameAction.ActionType.SPECIAL;
  }

  public void update() {
    int effect = EnergyPanel.totalCount;
    if (this.energyOnUse != -1)
      effect = this.energyOnUse;
    if (this.player.hasRelic("Chemical X")) {
      effect += 2;
      this.player.getRelic("Chemical X").flash();
    }
    if (effect > 0 && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
      // Note that we're repeating for N enemies total, NOT just N times!
      // This prevents us from being a cheap Killer Wail lol; it's crowd
      // control specifically!
      List<AbstractMonster> targets = getRandomMonsterTargets(
          effect, AbstractDungeon.cardRandomRng);
      for (AbstractMonster target : targets) {
        for (int i = 0; i < effect; i++) {
          addToBot(new DamageAction(target,
              new DamageInfo(this.player, this.damage, this.damageType),
              i % 2 == 0 ? AbstractGameAction.AttackEffect.SLASH_HORIZONTAL
                  : AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        }
      }
      if (!this.freeToPlayOnce) {
        this.player.energy.use(EnergyPanel.totalCount);
      }
    }
    this.isDone = true;
  }

  private List<AbstractMonster> getRandomMonsterTargets(int numToTarget, Random rng) {
    // Find all monsters that are not "basically dead".
    List<AbstractMonster> validTargets = new ArrayList<AbstractMonster>();
    for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
      if (!monster.isDying && !monster.isEscaping) {
        validTargets.add(monster);
      }
    }

    // If all of the monsters fit in `numToTarget`, just return them all.
    if (validTargets.size() <= numToTarget) {
      return validTargets;
    }

    // Otherwise, randomly choose `numToTarget` of them to target.
    // We'll mutate `validTargets` to do this, which is fine, because we're not
    // going to return it so no one else will ever see it!
    List<AbstractMonster> targets = new ArrayList<AbstractMonster>();
    for (int i = 0; i < numToTarget; i++) {
      // Choose a random index in `validTargets`. Remove the monster at that
      // index from `validTargets` and add it to `targets`.
      int randomIndexOfValidTargets = rng.random(0, validTargets.size() - 1);
      AbstractMonster targetMonster = validTargets.get(randomIndexOfValidTargets);
      validTargets.remove(randomIndexOfValidTargets);
      targets.add(targetMonster);
    }

    return targets;
  }
}
