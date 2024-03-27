public class Card {

    // Variables
    String value;
    String type;


    // Constructor
    public Card(String value, String type){
        this.value = value;
        this.type = type;
    }

    // toString method
    public String toString(){
        return value + "-" + type;
    }

    // Getters
    public int getValue() {
        if ("AJQK".contains(value)) {
            if (value == "A") {
                return 11;
            }
            return 10;
        }

        return Integer.parseInt(value); // 2-10

    }

    // Checks if the card is an Ace or not
    public boolean isAce(){
        return value == "A";

    }

}
