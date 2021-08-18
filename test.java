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
        
        //int points = game.getPoints(one);
        ArrayList<Card> test_hand = new ArrayList<Card>();;
        test_hand.add(new Card(3, SUIT.DIAMOND));
        test_hand.add(new Card(5, SUIT.HEART));
        test_hand.add(new Card(3, SUIT.HEART));
        test_hand.add(new Card(4, SUIT.DIAMOND));
        test_hand.add(new Card(4, SUIT.HEART));
        test_hand.add(new Card(7, SUIT.CLUB));
        
        int fifteens = Cribbage.fifteens(15, test_hand);
        //Player.getRuns(test_hand);
        //one.displayHand();
        ArrayList<ArrayList<Card>> lists = Player.allUniqueLists(test_hand);
        lists.forEach(System.out::println);



        System.out.println("\nThere are " + fifteens + " fifteens");
        //System.out.println("\nThere are " + runs + " points from runs");

        

        
    }

    

    
}
