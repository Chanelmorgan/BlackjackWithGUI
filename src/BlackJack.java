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
                if(!stayButton.isEnabled()) { // stay button hasn't been clicked
                    hiddenCardImg = new ImageIcon((getClass().getResource(hiddenCard.getImagePath()))).getImage();
                }
                g.drawImage(hiddenCardImg, 20, 20, cardWidth, cardHeight, null);

                // Draw the dealer's hand
                for(int i =0; i<dealerHand.size(); i++) {
                    Card card = dealerHand.get(i);
                    Image cardImg = new ImageIcon(getClass().getResource(card.getImagePath())).getImage();
                    g.drawImage(cardImg,cardWidth + 25 + (cardWidth + 5)*i, 20, cardWidth, cardHeight, null );
                }

                // Draw players hand
                for(int i = 0; i<playerHand.size(); i++) {
                    Card card = playerHand.get(i);
                    Image cardImg = new ImageIcon(getClass().getResource(card.getImagePath())).getImage();
                    g.drawImage(cardImg, 20 + (cardWidth + 5)*i, 320, cardWidth, cardHeight, null);

                }

                // Printing statements
                if(!stayButton.isEnabled()){
                    dealerSum = reduceDealerAce();
                    playerSum = reducePlayerAce();
                    System.out.println("STAY: ");
                    System.out.println(dealerSum);
                    System.out.println(playerSum);

                    String message = "";
                    if(playerSum > 21) {
                        message = "You lose!";
                    } else if(dealerSum > 21) {
                        message = "You win!";
                    }
                    // Both player and dealer have <= 21
                    else if(playerSum == dealerSum) {
                        message = "Tie!";
                    } else if(playerSum > dealerSum) {
                        message = "You Win!";
                    } else if(playerSum < dealerSum) {
                        message = "You lose";
                    }

                    g.setFont(new Font("Arial", Font.PLAIN, 30));
                    g.setColor(Color.WHITE);
                    g.drawString(message, 220, 250);
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

        // Adding function to the hit button
        hitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Card card = deck.remove(deck.size() -1);
                playerSum += card.getValue();
                playerAceCount += card.isAce() ? 1 : 0;
                playerHand.add(card);
                if(reducePlayerAce() > 21){
                    hitButton.setEnabled(false);
                }
                gamePanel.repaint();
            }
        });

        // Adding function to the stay button
        stayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hitButton.setEnabled(false);
                stayButton.setEnabled(false);

                // Dealer must draw until they have a sum of 17 or greater
                while(dealerSum < 17){
                    Card card = deck.remove(deck.size()-1);
                    dealerSum += card.getValue();
                    dealerAceCount += card.isAce()? 1: 0;
                    dealerHand.add(card);
                    gamePanel.repaint();
                }
            }
        });

        // Calls repaint within the constructor
        gamePanel.repaint();
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

    // Method to shuffle the deck of cards
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

    // Method to hnadle the change of ace from 11 to 1 if hand over 21
    private int reducePlayerAce(){
        while(playerSum > 21 && playerAceCount > 0) {
            playerSum -= 10;
            playerAceCount -= 1;
        }
        return playerSum;
    }

    private int reduceDealerAce(){
        while(dealerSum > 21 && dealerAceCount > 0) {
            dealerSum -= 10;
            dealerAceCount -= 1;
        }
        return dealerSum;
    }





}
