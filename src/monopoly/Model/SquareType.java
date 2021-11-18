package monopoly.Model;

/**
 * Types of squares.
 */
public enum SquareType {
    GO,
    CHANCE,
    INCOMETAX,
    GOTOJAIL,
    INJAILORJUSTVISITING,
    FREEPARKING,
    PROPERTY;

    public static SquareType parseSquareType(String type){
        switch (type){
            case "GO":
                return GO;
            case "CHANCE":
                return CHANCE;
            case "INCOMETAX":
                return INCOMETAX;
            case "GOTOJAIL":
                return GOTOJAIL;
            case "FREEPARKING":
                return FREEPARKING;
            case "PROPERTY":
                return PROPERTY;
            default:
                return null;
        }

    }
}
