package monopoly.View;

import java.util.Arrays;

public class Label extends Widget{
    public String layout = "center";
    public String text;

    public Label(int width,  int x, int y){
        super(width, 1, x, y);
        if (width < 1)
            throw new IllegalArgumentException("The width of a label cannot be set to less than 1.");
        setText("");
    }

    public Label(int width,  int x, int y, String text){
        super(width, 1, x, y);
        if (width < 1)
            throw new IllegalArgumentException("The width of a label cannot be set to less than 1.");
        setText(text);
    }

    public void setLayout(String layout){
        this.layout = layout;
        setText(text);
    }

    public void setText(String text) {
        char[][] content = new char[1][getWidth()];
        Arrays.fill(content[0], (char)32);
        if (text.length() <= getWidth()){
            if (layout.equals("center")){
                int leftSpace = getWidth() - text.length() - (getWidth() - text.length())/2;
                System.arraycopy(text.toCharArray(), 0, content[0], leftSpace, text.length());
            }
            else if (layout.equals("right")) {
                System.arraycopy(text.toCharArray(), 0, content[0], getWidth()-text.length(), text.length());
            }
            else{
                System.arraycopy(text.toCharArray(), 0, content[0], 0, text.length());
            }
        }
        else{
            System.arraycopy(text.toCharArray(), 0, content[0], 0, getWidth()-3);
            content[0][getWidth()-3] = '.';
            content[0][getWidth()-2] = '.';
            content[0][getWidth()-1] = '.';
        }
        setContent(content);
        this.text = text;
    }

    public void appendText(char ch){
        setText(text + ch);
    }

    public void backSpace(){
        if (text.length()==0) return;
        setText(text.substring(0, text.length()-1));
    }

    public int getTextLen(){
        return text.length();
    }
}
