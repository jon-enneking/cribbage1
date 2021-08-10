import java.util.Scanner;
import java.util.ArrayList;

public class Cribbage {
    ArrayList<Player> players;
    Deck deck;

    /* Initializes list of players and creates the deck that will be used. Sets dealer to first player entered. */
    public Cribbage(int number_of_players) throws Exception {
        Scanner scanny = new Scanner(System.in);
        players = new ArrayList<Player>(number_of_players);
        String name;
        for(int i=0; i<number_of_players; i++) {
            System.out.println("Enter player " + number_of_players + "\s name: ");
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
    /*public int getPoints(Player p) {
        int fifteen = 0;
        int run = 0;

        for(Card card : p.getHand()) {

        }
    }*/
    
}