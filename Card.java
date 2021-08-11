public class Card {
    protected int rank;
    protected SUIT suit;

    public Card(int rank, SUIT suit) {
        this.suit = suit;
        this.rank = rank;
    }

    @Override
    public String toString(){
        String rank_string;
        if(rank == 1)
            rank_string = "A";
        else if(rank == 11) 
            rank_string = "J";
        else if(rank == 12) 
            rank_string = "Q";
        else if(rank == 13) 
            rank_string = "K";
        else
            rank_string = "" + rank;

        return rank_string + " of " + suit.toString() + "S";
    }

    public int getRank() {
        return rank;
    }

    public SUIT getSuit() {
        return suit;
    }

    public int getValue() {
        int val = getRank();

        /* The value of Jacks, Queens, and Kings is 10 */
        if(rank > 10)
            val = 10;
        
        return val;
    }

    public static int stringtoRank(String s) {
        if(s.equalsIgnoreCase("a"))
            return 1;
        else if(s.equalsIgnoreCase("j"))
            return 11;
        else if(s.equalsIgnoreCase("q"))
            return 12;
        else if(s.equalsIgnoreCase("k"))
            return 13;
        else
            return Integer.valueOf(s);
    }

}