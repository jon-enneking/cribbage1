import java.util.ArrayList;
import java.util.Random;

public class Deck {
    private ArrayList<Card> cards;

    Deck() {
        cards = new ArrayList<Card>(52);
    }

    /* The shufle method will clear the arraylist, starting from scratch, and fill the deck in order. */
    public void refresh(){
        cards.clear();
        SUIT suit = SUIT.HEART;
        int rank = 1;
        try {
            for(int i=1; i<=13; i++) {
                rank = i;
                for (int j=1; j<=4; j++) {
                    switch(j) {
                        case(1): suit = SUIT.SPADE; break;
                        case(2): suit = SUIT.CLUB; break;
                        case(3): suit = SUIT.HEART; break;
                        case(4): suit = SUIT.DIAMOND; break;

                        default: throw new RuntimeException("Tried generating invalid card SUIT");
                    }

                    cards.add(new Card(rank, suit));
                }
                
            }
        } catch (RuntimeException ex) { System.out.println("Uh oh!!"); ex.toString(); }
        
    }

    public void shuffle() {
        Random r = new Random();
        int position;
        Card temp;

        /* Go through the deck swapping the current card with a random position */
        for(int i=0; i<52; i++) {
            position = r.nextInt(52);
            temp = cards.get(i);
            cards.set(i,cards.get(position));
            cards.set(position,temp);
        }
    }

    public ArrayList<Card> getCards() {
        return cards;
    }
    
    /* Pick the first (top) card, remove it from the deck, and return it */
    public void drawCard(Player p) {
        if(getCards().isEmpty()) {
            throw new RuntimeException("Cannot draw another card - deck is empty!");
        }

        p.getHand().add(cards.get(0));
        cards.remove(0);
    }

    public void deal(int n, ArrayList<Player> player_list) {
        for(Player p : player_list){
            for(int i=0; i<n; i++){
                drawCard(p);
            }
        }
    }

    public void printDeck() {
        for(Card card : cards) {
            System.out.println(card.toString());
        }
    }
    
}
