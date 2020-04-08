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
        Deck deck = new Deck();
        while (true) {

            Hand myHand = new Hand();
            Hand dealerHand = new Hand();

            // Deal initial hand
            myHand.addCard(deck.drawCard());
            myHand.addCard(deck.drawCard());
            dealerHand.addCard(deck.drawCard());
            dealerHand.addCard(deck.drawCard());

            boolean stay = false; // Flag to indicate user has completed input, dealer's turn.
            boolean bypassPrint = false; // Flag to indicate an additional printHands is not necessary.
            boolean blackJack = false;// Flag for instant BlackJack win!

            printHands(myHand, dealerHand, stay); // Print initial Deal

            if (myHand.sum() == 21) { // Check for BlackJack, instant win
                stay = true;
                bypassPrint = true;
                blackJack = true;
            }

            //Player decision prompts
            String choice;
            while (!stay) {
                System.out.println("\nWould you like to hit? - Y/N\r");
                choice = scanner.nextLine();
                if (choice.equals("Y")) {
                    myHand.addCard(deck.drawCard());
                    printHands(myHand, dealerHand, stay);
                } else if (choice.equals("N")) {
                    stay = true;
                } else {
                    System.out.println("Error. Try again");
                }
                if (myHand.sum() > 21) { // CHECK FOR BUST TO BYPASS
                    stay = true;
                    bypassPrint = true;
                }
                if (myHand.sum() == 21) { // CHECK FOR 21 TO BYPASS
                    stay = true;
                }
            }

            if (!bypassPrint) {
                printHands(myHand, dealerHand, stay);
            }

            // Dealer simulator
            if (!blackJack) {
                if ((dealerHand.sum() < myHand.sum() && dealerHand.sum() <= 16 && myHand.sum() <= 21) ||
                        (myHand.sum() <= 21 && dealerHand.sum() < myHand.sum())) {
                    if (myHand.sum() == 21) {
                        while (dealerHand.sum() <= 21) {
                            pauseForEffect();
                            System.out.println("\nThe Dealer hits.\n");
                            pauseForEffect();
                            dealerHand.addCard(deck.drawCard());
                            printHands(myHand, dealerHand, stay);
                            pauseForEffect();
                        }
                    } else {
                        while (dealerHand.sum() <= 16) {
                            pauseForEffect();
                            System.out.println("\nThe Dealer hits.");
                            pauseForEffect();
                            dealerHand.addCard(deck.drawCard());
                            printHands(myHand, dealerHand, stay);
                        }
                    }
                    if (dealerHand.sum() > 21) {
                        pauseForEffect();
                        System.out.println("\nThe Dealer busts.");
                    } else {
                        pauseForEffect();
                        System.out.println("\nThe Dealer stays.");
                    }
                }
            }

            // Call for outcomes
            outcome(myHand, dealerHand, blackJack);

            // 'Play again?' Prompt
            System.out.println("\nEnter 'Y' to play again: \r");
            choice = scanner.nextLine();
            if (!choice.equals("Y")) {
                System.out.println("Quitting...");
                break;
            } else {
                if (deck.getCards().size() < 10) { // Forces reset of deck if there are not many cards left
                    System.out.println("There are less than 10 cards left, resetting the deck...");
                    deck = new Deck();
                } else {
                    System.out.println("Would you like to continue with the same deck? Y/N"); // Retain same deck order and used cards
                    String deckChoice = scanner.nextLine();
                    if (!deckChoice.equals("Y")) {
                        deck = new Deck();
                    }
                }
            }
        }
    }

    // Print cards ArrayList for both hands. (Dealer only displays second card after user 'stays')
    private static void printHands(Hand myHand, Hand dealerHand, boolean stay) {
        if (stay) {
            System.out.println("\n==============================");
            System.out.println("Your final hand: " + myHand.getCards() + "    Total = " + myHand.sum());
            System.out.println("Dealer: " + dealerHand.getCards() + "    Total = " + dealerHand.sum());
            System.out.println("==============================");
        } else {
            System.out.println("\n==============================");
            System.out.println("Your hand: " + myHand.getCards() + "    Total = " + myHand.sum());
            System.out.println("Dealer's hand: [" + dealerHand.getCards().get(0) + ", *XXXXXXXXXXX*]");
            System.out.println("==============================");
        }
    }

    //Game Outcomes
    private static void outcome(Hand myHand, Hand dealerHand, boolean blackJack) {
        pauseForEffect();
        if (myHand.sum() == dealerHand.sum()) {
            System.out.println("\nDRAW!");
        } else if (myHand.sum() > 21) {
            System.out.println("\nBUST, YOU LOST!");
        } else if (myHand.sum() < 21 && dealerHand.sum() > myHand.sum() && dealerHand.sum() <= 21) {
            System.out.println("\nYOU LOST!");
        } else if (blackJack) {
            System.out.println("\n!!!!!BLACKJACK!!!!! YOU WON!");
        } else if (myHand.sum() < 21 && dealerHand.sum() < myHand.sum() || dealerHand.sum() > 21) {
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
