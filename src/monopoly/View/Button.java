package monopoly.View;

import java.util.Arrays;

public class Button extends Widget{
    private final Label label;
    private char[] selectedTop;
    private char[] unselectedTopAndBottom;
    private char[] selectedBottom;
    private boolean isSelected;

    /**
     * Constructor.
     * @param width width of button.
     * @param x x-location of button.
     * @param y y-location of button.
     */
    public Button(int width , int x, int y){
        super(width, 3, x, y);
        if (width < 5)
            throw new IllegalArgumentException("The width of a button cannot be set less than 5");
        label = new Label(width - 4, 2, 1);
        initialContent();
        addChildComponent(label);
        isSelected = false;
        setSelectionIssue();
    }

    /**
     * Constructor.
     * @param width width of button.
     * @param x x-location of button.
     * @param y y-location of button.
     * @param text text shown of the button.
     */
    public Button(int width , int x, int y, String text){
        super(width, 3, x, y);
        if (width < 5)
            throw new IllegalArgumentException("The width of a button cannot be set less than 5");
        label = new Label(width - 4, 2, 1);
        addChildComponent(label);
        initialContent();
        setText(text);
        isSelected = false;
        setSelectionIssue();
    }

    /**
     * Select mark of the button.
     */
    private void setSelectionIssue(){
        selectedTop = new char[getWidth()];
        selectedBottom = new char[getWidth()];
        unselectedTopAndBottom = new char[getWidth()];
        Arrays.fill(selectedTop, '_');
        Arrays.fill(selectedBottom, '\'');
        Arrays.fill(unselectedTopAndBottom, ' ');
        selectedTop[0] = selectedTop[getWidth()-1] = selectedBottom[0] = selectedBottom[getWidth()-1] = ' ';
        selectedTop[1] = selectedTop[getWidth()-2] = selectedBottom[1] = selectedBottom[getWidth()-2] = ' ';
    }

    public void setText(String text){
        label.setText(text);
    }

    /**
     * Append text to the label on the button.
     * @param ch the text to be appended.
     */
    public void appendText(char ch){
        label.appendText(ch);
    }

    public String getText(){
        return label.text;
    }


    /**
     * Enter a backSpace to the label of the button.
     */
    public void backSpace(){
        label.backSpace();
    }

    /**
     * @return text length of the button label/
     */
    public int getTextLen(){
        return label.getTextLen();
    }

    /**
     * Selected mark of the button.
     */
    public void setSelected(){
        if (isSelected) return;
        char[][] content = getContent();
        content[0] = selectedTop;
        content[2] = selectedBottom;
        content[1][1] = content[1][getWidth()-2] = '|';
        content[1][0] = '→';
        content[1][getWidth()-1] = '←';
        setContent(content);
        isSelected = true;
    }

    /**
     * UI of unselecting the button.
     */
    public void setUnselected(){
        if (!isSelected) return;
        char[][] content = getContent();
        content[0] = content[2] = unselectedTopAndBottom;
        content[1][0] = content[1][1] = content[1][getWidth()-1] = content[1][getWidth()-2] = ' ';
        setContent(content);
        isSelected = false;
    }
}
