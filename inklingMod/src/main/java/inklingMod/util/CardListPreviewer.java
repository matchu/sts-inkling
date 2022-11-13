package inklingMod.util;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.CardLibrary;

import inklingMod.InklingMod;

/**
 * A helper class a card can use to rotate between cards to preview!
 * 
 * Call `addCard(card)` when setting up, to add cards to the list.
 *
 * Then call `update()` every time the card updates (can optimize by skipping
 * when not hovered), and `getCard()` to see what the `cardsToPreview` value
 * should be!
 *
 * Adapted from code decompiled from the Downfall mod. Thank you!!
 */
public class CardListPreviewer {
  private final List<AbstractCard> cards = new ArrayList<AbstractCard>();
  private final float numSecondsToShowEachCard = 2.0f;

  private int currentIndex = 0;
  private float numSecondsUntilNextCard = this.numSecondsToShowEachCard;

  public void addCard(AbstractCard card) {
    this.cards.add(card);
  }

  public AbstractCard getCard() {
    // Use try-catch to be maximally defensive: I don't want it to be possible
    // at ALL to trigger a crash by having things somehow get out of sync, even
    // though I'm not aware of a specific case that could trigger it!
    try {
      return this.cards.get(this.currentIndex);
    } catch (IndexOutOfBoundsException e) {
      InklingMod.logger.warn(
          "CardListPreviewer tried to show card {} of {}, which doesn't exist. Showing Madness instead.",
          this.currentIndex,
          this.cards.size());
      return CardLibrary.cards.get("Madness");
    }
  }

  public void update() {
    this.numSecondsUntilNextCard -= Gdx.graphics.getDeltaTime();
    if (this.numSecondsUntilNextCard <= 0.0f) {
      this.numSecondsUntilNextCard = this.numSecondsToShowEachCard;
      this.goToNextCard();
    }
  }

  private void goToNextCard() {
    // Make sure we're not going to trigger a zero-division error when
    // modding the index by size. (We also be maximally defensive against
    // concurrency issues by getting the size once, so it's impossible for
    // it to change to zero between the check and the math. I'm not aware
    // of a case where that could happen, but, in Java, it's wise to make
    // it impossible!)
    int size = this.cards.size();
    if (size == 0) {
      this.currentIndex = 0;
      return;
    }

    this.currentIndex = (this.currentIndex + 1) % size;
  }

  public void upgradeAll() {
    for (AbstractCard card : cards) {
      card.upgrade();
    }
  }
}
