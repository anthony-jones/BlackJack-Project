package com.ajones.blackjack.cardtypes;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    private List<Card> cards = new ArrayList<Card>();

    public List<Card> getCards() {
        return cards;
    }

    public void addCard(Card card) {
        this.cards.add(card);
    }

    public int sum() {
        int sum = 0;
        for (int i = 0; i < this.cards.size(); i++) {
            sum += this.cards.get(i).getValue();
        }
        return sum;
    }
}
