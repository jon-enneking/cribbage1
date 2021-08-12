import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Cribbage {
    private ArrayList<Player> players;
    private Deck deck;

    /* Initializes list of players and creates the deck that will be used. Sets dealer to first player entered. */
    public Cribbage(int number_of_players) throws Exception {
        Scanner scanny = new Scanner(System.in);
        players = new ArrayList<Player>(number_of_players);
        String name;
        for(int i=0; i<number_of_players; i++) {
            System.out.println("Enter player " + i+1 + "\s name: ");
            name = scanny.nextLine();
            players.add(new Player(name));
        }

        players.get(0).setDealer();
        deck = new Deck();
        scanny.close();
    }

    public void round() {
        /* Prepare deck */
        deck.refresh();
        deck.shuffle();

        /* Deal cards, locate dealer */
        deck.deal(6, players);
        Player dealer = getDealer();

        /* Initialie crib and fill with users' discards */
        Player cur_player;
        ArrayList<Card> crib = new ArrayList<Card>(5);
        for(int i = 0; i<players.size(); i++) {
            cur_player = players.get(i);
            cur_player.displayHand();
            System.out.println(cur_player.getName() + ", choose 2 cards to place in the crib");
            System.out.println("Choice 1: ");
            crib.add(cur_player.chooseCard());
            cur_player.displayHand();
            System.out.println("Choice 2: ");
        }

        /* Calculate points */
        for(int i = 0; i<players.size(); i++) {
            cur_player = players.get(i);
            cur_player.displayHand();
            //System.out.println(cur_player.getName() + " receives " + getPoints(cur_player));
        }
    }

    public Player getDealer() {
        Player player = null;
        for(Player p : players) {
            if(p.isDealer())
                return p;
        }
        return player;
    }

    /* Not sure yet what to do here.
    Idea 1: For each card, search to see if there is a consecutive value and a 15 that can be made.
    Idea 2: Create an array of ints and sort it. Then check for consecutive values and 15's.
    Idea 3: If card i is 10, search for 5's or sums that add to 5. If i + (i+1) is < 15, try adding
    cards (i+2) and/or (i+3) (using mod 4). This seems like a stack problem. Double for-loop stack.
    How would we remove duplicates? */

    public static int getPoints(Player p) {
        int fifteens = 0, runs = 0, sum = 0;
        ArrayList<Card> hand = p.getHand();

        /* Find fifteens.. not sure how to remove duplicates yet. */
        for(int i=0; i<hand.size()-1; i++) {
            sum = hand.get(i).getValue();
            int j = i+1;
            while(j%hand.size() != i) {
                int temp = sum;
                System.out.println("Adding value at " + (j%(hand.size())));
                sum += hand.get(j%(hand.size())).getValue();
                if(sum > 15) 
                    sum = temp;
                else if(sum == 15) {
                    fifteens++;
                    sum = temp;
                }
                j++;
            }
        }

        return fifteens;
    }

    /* Search the list for the key. If the sum after adding one of the cards is less than 15,
    do a recursive call. */
    public static int fifteens(int key, ArrayList<Card> cards) {
        int total = 0;
        int cur = 0;

        for(int i=0; i<cards.size(); i++) {
            cur = cards.get(i).getValue(); //The current card we are working with.

            // If there is a card that results in a fifteen, increase total.
            if(cur == key)
                total++;

            /* If there is a card that results in a sum less than fifteen, call the function 
            again with the remaining cards looking for the new key that will create a fiteen. */ 
            else if(cur < key) {
                List<Card> sub = cards.subList(i+1, cards.size());
                ArrayList<Card> sub_list = new ArrayList<Card>(sub);
                total += fifteens(key - cur, sub_list);
            }
        }
        return total;
    }

    /* Inputs are the rank of the card we are starting with, the rank we are looking for, and
    the list that we are looking through.
    Will always find longest series above input before looking at series below. */
    public static int runs (int start, int key, ArrayList<Card> cards) {
        int total = 0, cur, temp=-1;
        Card removed;

        for(int i=0; i<cards.size(); i++) {
            cur = cards.get(i).getRank(); //The current card we are working with.
            /*if(total == temp) {
                start = cur;
                key = cur+1;
            }*/
            temp = total;
            if(cur == key) {
                /* Remove current card, then search remaining to see if there is a consecutive */
                removed = cards.remove(i);
                if(key > start) total += runs(start, key+1, cards);
                else total += runs(start, key-1, cards);

                cards.add(i, removed);
                if(total == temp) continue; //No consecutive value was found for current i.
            }
            else {
                if((key - start) > 2){
                    total += (key-start);
                }
            }
        }

        return total;
   }

    /* Methods for testing purposes */
    public Deck getDeck() {return deck;}
    public ArrayList<Player> getPlayers() {return players;}
    }
