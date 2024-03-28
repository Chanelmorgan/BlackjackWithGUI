import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;


/* Potential improvements
*
*  - MAKE A PLAYER AND DEALER CLASS
*  - Make the Gui a separate class ?
*
*
*
*
*
* */



public class BlackJack {

    // Class Variable
    private ArrayList<Card> deck;
    private Random random = new Random();

    // Dealer Variables
    Card hiddenCard;
    ArrayList<Card> dealerHand;
    int dealerSum;
    int dealerAceCount;

    // Player Variables
    ArrayList<Card> playerHand;
    int playerSum;
    int playerAceCount;

    // GUI Variables
    int boardWidth = 600;
    int boardHeight = boardWidth;

    int cardWidth = 110; // ratio 1:1.4
    int cardHeight = 154;

    JFrame jFrame = new JFrame("Black Jack");
    JPanel gamePanel = new JPanel() {

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            try {
                // Draw hidden card
                Image hiddenCardImg = new ImageIcon(getClass().getResource("./cards/BACK.png")).getImage();
                g.drawImage(hiddenCardImg, 20, 20, cardWidth, cardHeight, null);

                // Draw the dealer's hand
                for(int i =0; i<dealerHand.size(); i++) {
                    Card card = dealerHand.get(i);
                    Image cardImg = new ImageIcon(getClass().getResource(card.getImagePath())).getImage();
                    g.drawImage(cardImg,cardWidth + 25 + (cardWidth + 5)*i, 20, cardWidth, cardHeight, null );
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    };
    JPanel buttonPanel = new JPanel();
    JButton hitButton = new JButton("Hit");
    JButton stayButton = new JButton("Stay");

    // Constructor
    public BlackJack(){
        startGame();

        // Generating the window
        jFrame.setVisible(true);
        jFrame.setSize(boardWidth, boardHeight);
        jFrame.setLocationRelativeTo(null); // opens the gui in the center of screen
        jFrame.setResizable(false);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Generating the main game panel
        gamePanel.setLayout(new BorderLayout());
        gamePanel.setBackground(new Color(53, 101, 77));
        jFrame.add(gamePanel);

        // Generating the panel for the buttons
        hitButton.setFocusable(false);
        buttonPanel.add(hitButton);
        stayButton.setFocusable(false);
        buttonPanel.add(stayButton);

        jFrame.add(buttonPanel, BorderLayout.SOUTH);
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
        dealerAceCount += hiddenCard.isAce() ? 1 : 0;

        Card card = deck.remove(deck.size()-1);
        dealerSum += card.getValue();
        dealerAceCount += card.isAce() ? 1 : 0;
        dealerHand.add(card);


        System.out.println("DEALER: ");
        System.out.println(hiddenCard);
        System.out.println(dealerHand);
        System.out.println(dealerSum);
        System.out.println(dealerAceCount);

        // Player
        playerHand = new ArrayList<Card>();
        playerSum =0;
        playerAceCount = 0;

        for(int i =0; i < 2; i++){
            card = deck.remove(deck.size()-1);
            playerSum += card.getValue();
            playerAceCount += card.isAce() ? 1 : 0;
            playerHand.add(card);
        }

        System.out.println("PLAYER: ");
        System.out.println(playerHand);
        System.out.println(playerSum);
        System.out.println(playerAceCount);



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
