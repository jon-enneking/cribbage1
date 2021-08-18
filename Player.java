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

    /*  Problem: Card is removed that may be in other future runs 
        (Ex: 3,3,4,4,5 -> the first 3 is used in two different runs).
        Easy fix: don't remove cards from hand. Use ifContainsAll method for sets to make 
        sure that no duplicates are added. 
        Other fix: Only occassion of this is when there are two pairs (4 points of pairs),
        and a run. Could make a condition to check this. */

    public static Set<Card> findConsecutiveCards(Card card, ArrayList<Card> list) {
        Set<Card> set = new HashSet<Card>();
        int rank = card.getRank();
        Card cur;
        int distance_down = 1, distance_up = 1;
        set.add(card);
        
        
        for(int i=1; i<list.size(); i++) {
            cur = list.get(i);

            if(cur.getRank() == rank-distance_down) {
                set.add(cur);
                distance_down++;
                if(i > 0) 
                    i=-1; //Accounts for skipped cards that could be in run.
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

        /* Remove card from the front and add it to the end */
        list.remove(card);
        list.add(card); 

        return set;
    }

    //Only send lists of cards with no duplicates to findConsecutiveCards
    public static Set<Set<Card>> getRuns(ArrayList<Card> cards) {
        Set<Set<Card>> runs = new HashSet<Set<Card>>();
        Set<Card> temp;
        Card cur, last_card = cards.get(cards.size()-1);
        ArrayList<Card> copy = (ArrayList<Card>) cards.clone();

        /* Pick a card. Search for consecutive cards around it and add them
        to a set. Once all of the consecutive cards have been found, do the
        same thing with the remaining cards in the list.*/
        while(cards.get(0) != last_card) {
            cur = cards.get(0);
            temp = findConsecutiveCards(cur, cards);

            /* Sets cannot have duplicates, so it will skip duplicate entries */
            if(temp.size() > 2)
                runs.add(temp);
        }

        System.out.println("\nAll of the sets are: ");
        runs.forEach(System.out::println);
        cards.addAll(copy); //Puts all of the cards back into the hand
        return runs;
    }

    public static ArrayList<ArrayList<Card>> allUniqueLists(ArrayList<Card> cards) {
        ArrayList<ArrayList<Card>> allSets = new ArrayList<ArrayList<Card>>();
        Card temp1, temp2;
        int finish_index = cards.size();
        for(int i=0; i<finish_index; i++) {
            temp1 = cards.get(i);
            for(int j=i+1; j<cards.size(); j++) {
                temp2 = cards.get(j);
                if(temp1.getRank() == temp2.getRank()) { //Then there is a duplicate
                    /* Remove first instance of duplicate and send remaining cards to look for duplicates */
                    cards.remove(temp1);
                    allSets.addAll(allUniqueLists(cards));

                    /* Add first instance of duplicate back in, remove the second instance,
                    and look for duplicates in the remaining cards */
                    cards.add(i,temp1);
                    cards.remove(temp2);
                    allSets.addAll(allUniqueLists(cards));

                    /* Place card back into original position */
                    cards.add(j,temp2);

                    /* Because of the use of recursion, duplicates within the hand
                    that don't use the first card of the hand are accounted for. Without
                    changing this finish_index variable to the index of the last found duplicate,
                    we would end up duplicates of each unique list. */
                    finish_index = j;
                }
            }
        }

        /* If not duplicates were found, then the current list of cards is unique.
        Create a copy of this so that we can keep editing the original without changing
        the contents of the previously added list. */
        if(allSets.size() == 0)
            allSets.add((ArrayList<Card>)cards.clone());
        
        return allSets;
    }

    /* Method for calculating runs:
        Find all unique lists of cards (based on rank) - allUniqueLists
        Search each unique list of cards for runs
        Total results
    */
}
