package com.ajones.blackjack.cardtypes;

public class Card {
    public enum Rank {
        ACE(11), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9),
        TEN(10), JACK(10), QUEEN(10), KING(10);

        private int value;

        Rank(int value) {
            this.value = value;
        }
    }

    public enum Suit {
        SPADES, HEARTS, CLUBS, DIAMONDS;
    }

    private Rank rank;
    private Suit suit;
    private int value;

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
        this.value = this.rank.value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}
