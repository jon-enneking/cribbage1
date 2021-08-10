public enum SUIT {
    HEART, SPADE, DIAMOND, CLUB;

    public static SUIT stringToSUIT(String s) {
        s = s.substring(0, s.length()-1);
        return SUIT.valueOf(s);
    }
}