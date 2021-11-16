package monopoly.Model;

public enum SquareType {
    GO,
    CHANCE,
    INCOMETAX,
    GOTOJAIL,
    INJAILORJUSTVISITING,
    FREEPARKING,
    PROPERTY;

    public static SquareType parseSquareType(String type){
        return GO;
    }
}
