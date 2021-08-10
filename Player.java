import java.util.ArrayList;
import java.util.Scanner;

public class Player {
    private ArrayList<Card> hand;
    private boolean dealer = false;
    private String name;

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<Card>();
    }

    public void displayHand() {
        System.out.println(name + "\'s hand is:");
        for(Card card : hand) {
            System.out.println(card.toString());
        }
    }

    public void removeCard(Card c) {
        boolean success = hand.remove(c);
        if(!success)
            System.out.println(name+" does not have a " + c.toString());
    }
    
    public ArrayList<Card> getHand() {
        return hand;
    }

    public String getName() {
        return name;
    }

    public Card getCard(int rank, SUIT suit) {
        for (Card card : hand) {
            if(card.getRank() == rank && card.getSuit() == suit)
                return card;
        }

        return null;
    }

    public void setDealer() {
        System.out.println(name + " is now the dealer");
        dealer = true;
    }

    public boolean isDealer() {
        return dealer;
    }
    
    /* Allows player to select a card, removes it from their hand, and returns the value */
    public Card chooseCard() {
        Scanner scanny = new Scanner(System.in);
        String input = scanny.nextLine();
        input.trim();
        String[] card_selection = input.split(" ");
        int r = Card.stringtoRank(card_selection[0]);
        SUIT suit = SUIT.stringToSUIT(card_selection[2]);
        Card c = getCard(r, suit);
        removeCard(c);
        scanny.close();
        return c;
    }
}
