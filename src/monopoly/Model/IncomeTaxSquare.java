package monopoly.Model;

public class IncomeTaxSquare extends SquareBackend {
    public IncomeTaxSquare(int positionID) {
        super(SquareType.INCOMETAX, positionID, "INCOME TAX");
    }

    // calculate how much user should pay for tax
    public static int calculateTax(int moneyAmount){
        int tax = moneyAmount*Configs.taxRate/100;
        return tax - tax%10;
    }
}
