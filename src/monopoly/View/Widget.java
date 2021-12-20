package monopoly.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;

/**
 * The most basic class for storing the display information
 * */
public class Widget {
    private ArrayList<Widget> childComponents;
    private char[][] content;
    // a copy of content (content will be changed during update)
    private char[][] contentBackup;
    private int width;
    private int height;
    private final int x;
    private final int y;
    // store change state, if not changed then won't be refreshed
    private boolean isChanged;

    public Widget(int width, int height, int x, int y) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        childComponents = new ArrayList<>();
        if (width>0 && height>=0){
            content = new char[height][width];
            contentBackup = new char[height][width];
        }

        isChanged = true;
    }

    // create an empty content (fill in blank)
    public void initialContent(){
        for (int i=0; i<height;i++){
            Arrays.fill(content[i], (char)32);
            Arrays.fill(contentBackup[i], (char)32);
        }
        isChanged = true;
    }

    public ArrayList<Widget> getChildComponents() {
        return childComponents;
    }

    // add child component
    public void addChildComponent(Widget component) {
        // child content cannot beyond the border of parent
        if ((component.getWidth() + component.getX() > width)){
            System.out.printf("Beyond Border: child width %d, child X %d, parent X %d\n",
                    component.getWidth(), component.getX(), width);
            throw new IllegalArgumentException();
        }
        if ((component.getHeight() + component.getY() > height )){
            System.out.printf("Beyond Border: child height %d, child Y %d, parent Y %d\n",
                    component.getHeight(), component.getY(), height);
            throw new IllegalArgumentException();
        }

        childComponents.add(component);
        isChanged = true;
    }

    public void removeChildComponent(int index){
        if (index < 0 || index > childComponents.size() - 1)
            throw new IllegalArgumentException("Index out of range.");

        childComponents.remove(index);
        isChanged = true;
    }

    // remove the child content and recover the original content
    public void removeChildComponent(Widget child){
        childComponents.remove(child);

        for (int h = 0; h < child.getHeight(); h++){
            if (child.getWidth() >= 0)
                System.arraycopy(contentBackup[child.getY() + h], child.getX(), content[child.getY() + h],
                        child.getX(), child.getWidth());
        }
        isChanged = true;
    }

    // clear all child components and recover the original content
    public void clear(){
        for (int i=0; i<height;i++)
            this.content[i] = Arrays.copyOf(contentBackup[i], width);

        childComponents = new ArrayList<>();
        isChanged = true;
    }

    // recursively update the content
    public char[][] update() throws ConcurrentModificationException {
        if (!isChanged()) return content;
        for (Widget child: childComponents){
            char[][] ChildContent = child.update();
            for (int h = 0; h < child.getHeight(); h++){
                if (child.getWidth() >= 0)
                    System.arraycopy(ChildContent[h], 0, content[child.getY() + h],
                            child.getX(), child.getWidth());
            }
        }
        isChanged = false;
        return content;
    }

    // recursively check is changed
    public boolean isChanged() throws ConcurrentModificationException{
        if (isChanged) return true;
        for (Widget child: childComponents){
            if (child.isChanged()){
                return true;
            }
        }
        return false;
    }

    public void setContent(char[][] content){
        height = content.length;
        width = content[0].length;
        this.content = new char[height][width];
        this.contentBackup = new char[height][width];
        for (int i=0; i<height;i++){
            this.content[i] = Arrays.copyOf(content[i], width);
            this.contentBackup[i] = Arrays.copyOf(content[i], width);
        }
        isChanged = true;
    }

    public int listenOnSelection(){
        return 0;
    }

    public char[][] getContent() {
        return content;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
