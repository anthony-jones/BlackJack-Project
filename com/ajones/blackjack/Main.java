package com.ajones.blackjack;

import com.ajones.blackjack.cardtypes.Deck;
import com.ajones.blackjack.cardtypes.Hand;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Welcome to the table!");
        playBlackJack();
    }

    public static void playBlackJack() {
        Scanner scanner = new Scanner(System.in);

        // Reset deck prompt
        boolean resetDeck = true;
        System.out.println("\nEnter 'Y' if you would like to keep the same deck in-between games:\r");
        String sameDeck = scanner.nextLine().toLowerCase();
        if(sameDeck.equals("y")) {
            resetDeck = false;
        }

        Deck deck = new Deck();
        while (true) {

            Hand playerHand = new Hand();
            Hand dealerHand = new Hand();

            // Deal initial hand
            playerHand.addCard(deck.drawCard());
            playerHand.addCard(deck.drawCard());
            dealerHand.addCard(deck.drawCard());
            dealerHand.addCard(deck.drawCard());

            boolean stay = false; // Flag to indicate user has completed input, dealer's turn.
            boolean blackJack = false;// Flag for instant BlackJack win!
            boolean bust = false; // Flag for user bust.

            printHands(playerHand, dealerHand, stay); // Print initial deal

            // Check for BlackJack, instant win unless dealer also has BlackJack
            if (playerHand.sum() == 21) {
                stay = true;
                blackJack = true;
                pauseForEffect();
                printHands(playerHand, dealerHand, stay);
            }

            // Player decision prompts
            String choice;
            while (!stay) {
                System.out.println("\nWould you like to hit? - Y/N\r");
                choice = scanner.nextLine().toLowerCase();
                if (choice.equals("y")) {
                    playerHand.addCard(deck.drawCard());
                    printHands(playerHand, dealerHand, stay);
                } else if (choice.equals("n")) {
                    stay = true;
                } else {
                    System.out.println("Error. Try again");
                }
                if (playerHand.sum() > 21) { // CHECK FOR BUST TO BYPASS
                    stay = true;
                    bust = true;
                }
                if (playerHand.sum() == 21) { // CHECK FOR 21 TO BYPASS
                    stay = true;
                }
            }

            // Dealer Responses
            if (!blackJack) { // Instant dealer-lose if BlackJack
                if (!bust) {
                    pauseForEffect();
                    printHands(playerHand, dealerHand, stay);
                    while (dealerHand.sum() <= playerHand.sum() && dealerHand.sum() < 21 && playerHand.sum() <= 21) {
                        pauseForEffect();
                        System.out.println("\nThe Dealer hits.");
                        pauseForEffect();
                        dealerHand.addCard(deck.drawCard());
                        printHands(playerHand, dealerHand, stay);
                    }
                    if (dealerHand.sum() > 21) {
                        System.out.println("\nThe Dealer busts.");
                    } else {
                        System.out.println("\nThe Dealer stays.");
                    }
                }
            }

            // Call for outcomes
            outcome(playerHand, dealerHand, blackJack);

            // 'Play again?' Prompt
            System.out.println("\nEnter 'Y' to play again: \r");
            choice = scanner.nextLine().toLowerCase();
            if (!choice.equals("y")) {
                System.out.println("Quitting...");
                break;
            }

            // Check to reset deck or not
            if(resetDeck){
                deck = new Deck();
            } else if (deck.getCards().size() < 10){
                pauseForEffect();
                System.out.println("Cards running low, deck will be reset now.");
                deck = new Deck();
            }
        }
    }

    // Print cards ArrayList for both hands. (Dealer only displays second card after user 'stays')
    private static void printHands(Hand playerHand, Hand dealerHand, boolean stay) {
        if (stay) {
            System.out.println("\n==============================");
            System.out.println("Your final hand: " + playerHand.getCards() + "    Total = " + playerHand.sum());
            System.out.println("Dealer: " + dealerHand.getCards() + "    Total = " + dealerHand.sum());
            System.out.println("==============================");
        } else {
            System.out.println("\n==============================");
            System.out.println("Your hand: " + playerHand.getCards() + "    Total = " + playerHand.sum());
            System.out.println("Dealer's hand: [" + dealerHand.getCards().get(0) + ", *XXXXXXXXXXX*]");
            System.out.println("==============================");
        }
    }

    // Game Outcomes
    private static void outcome(Hand playerHand, Hand dealerHand, boolean blackJack) {
        pauseForEffect();
        if (playerHand.sum() == dealerHand.sum()) {
            System.out.println("\nPUSH!");
        } else if (playerHand.sum() > 21) {
            System.out.println("\nBUST, YOU LOST!");
            printHands(playerHand, dealerHand, true);
        } else if (playerHand.sum() < 21 && dealerHand.sum() > playerHand.sum() && dealerHand.sum() <= 21) {
            System.out.println("\nYOU LOST!");
        } else if (blackJack) {
            System.out.println("\n!!!!!BLACKJACK!!!!! YOU WON!");
        } else if (playerHand.sum() < 21 && dealerHand.sum() < playerHand.sum() || dealerHand.sum() > 21) {
            System.out.println("\nYOU WON!");
        }
    }

    // Pauses to simulate dealer thinking and gives chance for user to read
    private static void pauseForEffect() {
        System.out.println();
        try {
            System.out.print(".");
            Thread.sleep(600);
        } catch (InterruptedException e) {
            System.out.println();
        }
        try {
            System.out.print(".");
            Thread.sleep(600);
        } catch (InterruptedException e) {
            System.out.println();
        }
        try {
            System.out.print(".");
            Thread.sleep(600);
        } catch (InterruptedException e) {
            System.out.println();
        }
        System.out.println();
    }
}
