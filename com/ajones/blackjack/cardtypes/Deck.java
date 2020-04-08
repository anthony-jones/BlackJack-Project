package com.ajones.blackjack.cardtypes;

import java.util.Collections;
import java.util.LinkedList;

public class Deck {
    private LinkedList<Card> cards = newDeck();

    private LinkedList<Card> newDeck() {
        LinkedList<Card> newDeck = new LinkedList<Card>();
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values()) {
                newDeck.add(new Card(rank, suit));
            }
        }
        Collections.shuffle(newDeck);
        return newDeck;
    }

    public Card drawCard() {
        Card topCard = this.cards.getFirst();
        this.cards.removeFirst();
        return topCard;
    }

    public LinkedList<Card> getCards() {
        return cards;
    }
}
