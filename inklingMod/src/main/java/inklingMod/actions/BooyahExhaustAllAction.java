package inklingMod.actions;

import java.util.ArrayList;
import java.util.List;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import inklingMod.cards.Booyah;

public class BooyahExhaustAllAction extends AbstractGameAction {
  public BooyahExhaustAllAction() {
    this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
    this.duration = Settings.ACTION_DUR_FAST;
  }

  @Override
  public void update() {
    exhaustAllBooyahsInGroup(AbstractDungeon.player.hand);
    exhaustAllBooyahsInGroup(AbstractDungeon.player.drawPile);
    exhaustAllBooyahsInGroup(AbstractDungeon.player.discardPile);
    this.isDone = true;
  }

  private void exhaustAllBooyahsInGroup(CardGroup cardGroup) {
    List<AbstractCard> stableCardList = new ArrayList<AbstractCard>(cardGroup.group);
    for (AbstractCard card : stableCardList) {
      if (card.cardID == Booyah.ID) {
        cardGroup.moveToExhaustPile(card);
      }
    }
  }
}
