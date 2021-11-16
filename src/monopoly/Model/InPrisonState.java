package monopoly.Model;

public enum InPrisonState {
    FREE,
    INJAILROUND1,
    INJAILROUND2,
    INJAILROUND3;

    public static InPrisonState goNextState(InPrisonState current){
        return switch (current) {
            case FREE -> INJAILROUND1;
            case INJAILROUND1 -> INJAILROUND2;
            case INJAILROUND2 -> INJAILROUND3;
            case INJAILROUND3 -> FREE;
        };
    }

    public static boolean isInJail(InPrisonState state)
    {
        return !state.equals(FREE);
    }

    public static boolean isOutNextRound(InPrisonState state) {return state==INJAILROUND3;}

    public static InPrisonState parseState(String state){
        return FREE;
    }
}
