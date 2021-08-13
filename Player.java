import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Player {
    private ArrayList<Card> hand;
    private boolean dealer = false;
    private String name;

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<Card>();
    }

    public static ArrayList<Integer> cardsToRanks(ArrayList<Card> cards){
        ArrayList<Integer> rank_hand = new ArrayList<Integer>(cards.size());
        for(Card card : cards) {
            rank_hand.add(card.getRank());
        }
        return rank_hand;
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

    /* Determines the length of a run given the rank of the starting card,
    the direction, and the ArrayList of ranks */
    public static int lengthOfRun(int start, boolean up, ArrayList<Integer> ranks) {
        int total_distance = 1;
        int next;
        if(up) next = 1;
        else next = -1;

        int index = ranks.indexOf(start+next);
        if(index != -1) {
            total_distance++;
            int removed = ranks.remove(index);
            total_distance += lengthOfRun(removed, up, ranks);
            ranks.add(index, removed);
        }

        return total_distance;
    }

    public static Set<Card> findConsecutiveCards(Card card, ArrayList<Card> list) {
        Set<Card> set = new HashSet<Card>();
        int rank = card.getRank();
        int distance_down = 1;
        int distance_up = 1;
        set.add(card);
        list.remove(card);
        Card cur;
        for(int i=0; i<list.size(); i++) {
            cur = list.get(i);
            /*if(cur.getRank() == rank) {
                set.addAll(findConsecutiveCards(cur, list));
            }*/
            if(cur.getRank() == rank-distance_down) {
                set.add(cur);
                distance_down++;
                if(i > 0) i=-1; //Accounts for skipped cards that could be in run.
            }
            else if(cur.getRank() == rank+distance_up) {
                set.add(cur);
                distance_up++;
                if(i > 0) i=-1; //Accounts for skipped cards that could be in run.
            }        
        }

        if(set.size() < 3)
            System.out.println("Set of consecutive cards is too small to be considered a run");
        else
            System.out.println("Run found: ");
        set.forEach(System.out::println);
        return set;
    }
    public static Set<Set<Card>> getRuns(ArrayList<Card> cards) {
        Set<Set<Card>> runs = new HashSet<Set<Card>>();
        Set<Card> temp;
        Card cur;
        ArrayList<Card> copy = (ArrayList<Card>) cards.clone();

        /* Pick a card. Search for consecutive cards around it and add them
        to a set. Once all of the consecutive cards have been found, do the
        same thing with the remaining cards in the list.
        If there is a duplicate of the card, run the same search twice. */
        while(cards.size() > 0) {
            cur = cards.get(0);
            temp = findConsecutiveCards(cur, cards);
            if(temp.size() > 2)
                runs.add(temp);
        }

        System.out.println("\nAll of the sets are: ");
        runs.forEach(System.out::println);
        cards.addAll(copy); //Puts all of the cards back into the hand
        return runs;
    }
}
