import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class BaccaratDealerTest {

	@Test
	void testGenerateDeck(){
		BaccaratDealer dealer = new BaccaratDealer();
		dealer.generateDeck();
		int deckSize = dealer.deckSize();
		assertEquals(52,deckSize, "Deck size is not 52");
	}

	@Test
	void testGenerateDeckContainsAces(){
		BaccaratDealer dealer = new BaccaratDealer();
		dealer.generateDeck();

		// Check if the deck contains all four aces
		boolean hasAceOfSpades = dealer.deck.stream().anyMatch(card -> card.value == 1 && card.suite.contains("spades"));
		boolean hasAceOfDiamonds = dealer.deck.stream().anyMatch(card -> card.value == 1 && card.suite.contains("diamonds"));
		boolean hasAceOfHearts = dealer.deck.stream().anyMatch(card -> card.value == 1 && card.suite.contains("hearts"));
		boolean hasAceOfClubs = dealer.deck.stream().anyMatch(card -> card.value == 1 && card.suite.contains("clubs"));

		assertTrue(hasAceOfSpades);
		assertTrue(hasAceOfDiamonds);
		assertTrue(hasAceOfHearts);
		assertTrue(hasAceOfClubs);

	}

	@Test
	void testDealHand(){
		BaccaratDealer dealer = new BaccaratDealer();
		dealer.generateDeck();

		ArrayList<Card> hand = dealer.dealHand();

		// Check that the hand contains two cards
		assertEquals(2, hand.size());
	}

	@Test
	void testDealHandWithInsufficientCards() {
		BaccaratDealer dealer = new BaccaratDealer();
		dealer.generateDeck();
		// Remove all cards except one
		while (dealer.deckSize() > 1) {
			dealer.drawOne();
		}

		ArrayList<Card> hand = dealer.dealHand();

		// Check that the hand still contains two cards after deck refill
		assertEquals(2, hand.size());
	}

	@Test
	void testDrawOneWithSufficientCards() {
		BaccaratDealer dealer = new BaccaratDealer();
		dealer.generateDeck();

		Card drawnCard = dealer.drawOne();

		// Check that a card is drawn (not null)
		assertNotNull(drawnCard);
	}

	@Test
	void testDrawOneWithEmptyDeck() {
		BaccaratDealer dealer = new BaccaratDealer();
		dealer.generateDeck();
		// Remove all cards from the deck
		while (dealer.deckSize() > 0) {
			dealer.drawOne();
		}

		Card drawnCard = dealer.drawOne();

		// Check that the drawn card is null
		assertNull(drawnCard);
	}

	@Test
	void testDeckSizeWithNonEmptyDeck() {
		BaccaratDealer dealer = new BaccaratDealer();
		dealer.generateDeck();
		dealer.drawOne();
		int deckSize = dealer.deckSize();

		// Check that the deck size matches the expected size (52 cards)
		assertEquals(51, deckSize);
	}

	@Test
	void testDeckSizeWithEmptyDeck() {
		BaccaratDealer dealer = new BaccaratDealer();
		dealer.generateDeck();

		while (dealer.deckSize() > 0) {
			dealer.drawOne();
		}
		int deckSize = dealer.deckSize();

		// Check that the deck size is zero for an empty deck
		assertEquals(0, deckSize);
	}
}
