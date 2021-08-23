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

    //UNUSED
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

    public static Set<Card> findRun(ArrayList<Card> cards) {
        int rank, distance_up=1, distance_down=1;
        Set<Card> set = new HashSet<Card>();
        Card key_card, temp;
        for(int i=0; i<cards.size(); i++){
            key_card = cards.get(i);
            rank = key_card.getRank();
            set.add(key_card);
            for(int j = (i+1)%cards.size(); j!= i; j=(j+1)%cards.size()) {
                temp = cards.get(j);
                if (temp.getRank() == rank + distance_up) {
                    set.add(temp);
                    distance_up++;
                    if(j>(i+1)%cards.size()) j = i%cards.size();
                }
                else if(temp.getRank() == rank - distance_down) {
                    set.add(temp);
                    distance_down++;
                    if(j>(i+1)%cards.size()) j = i%cards.size();
                }
            }
            if(set.size() > 2) {
                /* Run Found */
                set.forEach(System.out::println);
                break;
            }
            else    
                set.clear();
        }

        return set;
    }
    
    public static Set<Set<Card>> getRuns(ArrayList<Card> cards) {
        ArrayList<Card> original = (ArrayList<Card>)cards.clone();
        ArrayList<ArrayList<Card>> uniqueLists = allUniqueLists(cards);
        Set<Set<Card>> runs = new HashSet<Set<Card>>();
        Set<Card> temp;
        int total_points = 0;
        for(ArrayList<Card> list : uniqueLists) {
            temp = findRun(list);
            if(temp.size()>0) {
                runs.add(temp);
                total_points+=temp.size();
            }
        }
        
        System.out.println("\nAll of the sets are: ");
        runs.forEach(run-> System.out.println(run));
        System.out.println("Total points from runs = " + total_points);
        cards = original;
        return runs;
    }

    public static ArrayList<ArrayList<Card>> allUniqueLists(ArrayList<Card> cards) {
        ArrayList<ArrayList<Card>> allSets = new ArrayList<ArrayList<Card>>();
        ArrayList<Card> duplicates = new ArrayList<Card>();
        Card temp1, temp2;
        for(int i=0; i<cards.size(); i++) {
            temp1 = cards.get(i);
            for(int j=i+1; j<cards.size(); j++) {
                temp2 = cards.get(j);
                if(temp1.getRank() == temp2.getRank()) { //Then there is a duplicate
                    duplicates.add(temp2);
                    cards.remove(temp2);
                    j--;
                }
            }
        }

        if(cards.size() > 2) {
            allSets.add((ArrayList<Card>)cards.clone());
            int original_size = duplicates.size();
            for(int i=0; i < duplicates.size() && i < 2*original_size; i++){
                Card dup = duplicates.get(i);
                for(int j=0; j < cards.size(); j++) {
                    Card place_holder = cards.get(j);
                    if(cards.get(j).getRank() == dup.getRank()) {
                        cards.remove(place_holder);
                        cards.add(dup);
                        allSets.add((ArrayList<Card>)cards.clone());
                        duplicates.add(place_holder);
                        break;
                    }
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
