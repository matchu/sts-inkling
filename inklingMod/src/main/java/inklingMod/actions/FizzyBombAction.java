package inklingMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
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
    if (effect > 0) {
      for (int i = 0; i < effect; i++) {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
          AbstractMonster randomMonster = AbstractDungeon.getMonsters().getRandomMonster(
              null, true, AbstractDungeon.cardRandomRng);
          addToBot(new DamageAction(randomMonster,
              new DamageInfo(this.player, this.damage, this.damageType),
              AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
          addToBot(new DamageAction(randomMonster,
              new DamageInfo(this.player, this.damage, this.damageType),
              AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        }
      }
      if (!this.freeToPlayOnce) {
        this.player.energy.use(EnergyPanel.totalCount);
      }
    }
    this.isDone = true;
  }
}
