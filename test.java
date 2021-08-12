import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class test {
    public static void main(String[] args) throws Exception {
        Cribbage game = new Cribbage(2);
        ArrayList<Player> players = game.getPlayers();
        Deck deck = game.getDeck();

        deck.refresh();
        deck.shuffle();
        deck.deal(5, players);

        Player one = players.get(0);
        //one.displayHand();
        //int points = game.getPoints(one);
        ArrayList<Card> test_hand = new ArrayList<Card>();
        test_hand.add(new Card(9, SUIT.HEART));
        test_hand.add(new Card(10, SUIT.HEART));
        test_hand.add(new Card(3, SUIT.HEART));
        test_hand.add(new Card(2, SUIT.HEART));
        test_hand.add(new Card(4, SUIT.HEART));
        
        //int points = game.fifteens(15, one.getHand());
        int fifteens = game.fifteens(15, test_hand);
        //int runs = game.runs(10, 8, test_hand, false);
        ArrayList<Integer> test_ranks = one.cardsToRanks(test_hand);
        int start = 5;
        boolean up = false;
        int distance_up = one.lengthOfRun(start, up, test_ranks);



        System.out.println("\nThere are " + fifteens + " fifteens");
        System.out.println("Consecutive cards from " + start + " going " + up + " is " + distance_up);
        //System.out.println("\nThere are " + runs + " points from runs");

        

        
    }

    

    
}
