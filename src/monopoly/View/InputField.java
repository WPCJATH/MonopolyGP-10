package monopoly.View;

import monopoly.Controller.GlobalController;
import monopoly.Model.Configs;


public class InputField extends Button{
    private boolean isSelected;
    private final int maxLen;

    public InputField(int width, int x, int y){
        super(width, x, y);
        if (width <= 2)
            throw new IllegalArgumentException("The width of a Input Field cannot be set to less than 3");

        maxLen = Configs.nameLengthBound;
        setContent(GlobalController.preLoadModels.inputFieldSelected);
        isSelected = true;
    }

    @Override
    public void setSelected(){
        if (isSelected) return;
        setContent(GlobalController.preLoadModels.inputFieldSelected);
        isSelected = true;
    }

    @Override
    public void setUnselected(){
        if (!isSelected) return;
        setContent(GlobalController.preLoadModels.inputFieldUnselected);
        isSelected = false;
    }

    public void fieldAppendText(char ch){
        if (!(getTextLen() < maxLen)) return;
        appendText(ch);
    }



}
