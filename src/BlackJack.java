import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class BlackJack {

    // Class Variable
    private ArrayList<Card> deck;
    private Random random = new Random();

    // Dealer Variables
    Card hiddenCard;
    ArrayList<Card> dealerHand;
    int dealerSum;
    int dealerAceCount;

    // Constructor
    public BlackJack(){
        startGame();
    }

    // Method to start the game
    public void startGame(){
        // Generate deck
        buildDeck();
        // Shuffles the deck randomly
        shuffleDeck();

        // Dealer
        dealerHand = new ArrayList<Card>();
        dealerSum = 0;
        dealerAceCount= 0;

        hiddenCard = deck.remove(deck.size() -1); // remove card at last index
        dealerSum += hiddenCard.getValue();


    }

    // Method that generates the deck of cards
    private void buildDeck() {
        deck = new ArrayList<Card>();
        String[] values = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        String[] types = {"C", "D", "H", "S"};

        for (int i = 0; i < types.length; i++) {
            for (int j = 0; j < values.length; j++) {
                Card card = new Card(values[j], types[i]);
                deck.add(card);
            }

        }

        System.out.println("BUILD DECK: ");
        System.out.println(deck);
    }

    private void shuffleDeck() {
        for (int i = 0; i < deck.size(); i++) {
            int j = random.nextInt(deck.size());
            Card currentCard = deck.get(i);
            Card randomCard = deck.get(j);
            deck.set(i, randomCard);
            deck.set(j, currentCard);
        }
        System.out.println("AFTER SHUFFLE: ");
        System.out.println(deck);
    }





}
