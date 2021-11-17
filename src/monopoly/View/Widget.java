package monopoly.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class Widget {
    private ArrayList<Widget> childComponents;
    private char[][] content;
    private char[][] contentBackup;
    private int width;
    private int height;
    private final int x;
    private final int y;
    private boolean isChanged;
    private boolean isChildComponentsLocked;

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
        isChildComponentsLocked = false;
    }

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

    public void addChildComponent(Widget component) {
        if (isChildComponentsLocked) {
            try {TimeUnit.MILLISECONDS.sleep(10);} catch (InterruptedException ignored){}
            addChildComponent(component);
        }

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
        if (isChildComponentsLocked) {
            try {TimeUnit.MILLISECONDS.sleep(10);} catch (InterruptedException ignored){}
            removeChildComponent(index);
        }

        if (index < 0 || index > childComponents.size() - 1)
            throw new IllegalArgumentException("Index out of range.");
        childComponents.remove(index);
        isChanged = true;
    }

    public void removeChildComponent(Widget child){
        if (isChildComponentsLocked) {
            try {TimeUnit.MILLISECONDS.sleep(10);} catch (InterruptedException ignored){}
            removeChildComponent(child);
        }

        childComponents.remove(child);
        for (int h = 0; h < child.getHeight(); h++){
            if (child.getWidth() >= 0)
                System.arraycopy(contentBackup[child.getY() + h], child.getX(), content[child.getY() + h],
                        child.getX(), child.getWidth());
        }
        isChanged = true;
    }

    public void clear(){
        if (isChildComponentsLocked) {
            try {TimeUnit.MILLISECONDS.sleep(10);} catch (InterruptedException ignored){}
            clear();
        }

        for (int i=0; i<height;i++)
            this.content[i] = Arrays.copyOf(contentBackup[i], width);
        childComponents = new ArrayList<>();
        isChanged = true;
    }

    public char[][] update(){
        if (!isChanged()) return content;
        isChildComponentsLocked = true;
        for (Widget child: childComponents){
            char[][] ChildContent = child.update();
            for (int h = 0; h < child.getHeight(); h++){
                if (child.getWidth() >= 0)
                    System.arraycopy(ChildContent[h], 0, content[child.getY() + h],
                            child.getX(), child.getWidth());
            }
        }
        isChildComponentsLocked = false;
        isChanged = false;
        return content;
    }

    public boolean isChanged() {
        if (isChanged) return true;
        isChildComponentsLocked = true;
        for (Widget child: childComponents){
            if (child.isChanged()){
                isChildComponentsLocked = false;
                return true;
            }
        }
        isChildComponentsLocked = false;
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
