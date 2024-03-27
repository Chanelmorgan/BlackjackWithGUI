import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class BlackJack {

    // Class Variable
    private ArrayList<Card> deck;

    // Constructor
    public BlackJack(){
        startGame();
    }

    // Method to start the game
    public void startGame(){
        // deck
        buildDeck();

    }

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
}
