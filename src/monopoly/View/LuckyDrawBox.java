package monopoly.View;

import monopoly.Controller.GlobalController;

public class LuckyDrawBox extends Widget{
    private final Card[] cards;
    private int currentSelectIndex;


    public LuckyDrawBox(int id, String name, int[] cardNumbers) {
        super(-1,-1,26,12);
        setContent(GlobalController.preLoadModels.messageBox);

        Label headLabel = new Label(63, 1, 1,
                " ↑ Time Limit         CHANCE Lucky Draw                        ");

        Label callNameLabel = new Label(25, 1, 2, "To Player " + id + ' ' + name+':');
        Label messageLabel = new Label(63, 1, 3,
                "                  Pick one card and flip it.                   ");

        cards = new Card[]{
                new Card(5, 7, cardNumbers[0]),
                new Card(19, 7, cardNumbers[1]),
                new Card(34, 7, cardNumbers[2]),
                new Card(48, 7, cardNumbers[3])
        };

        Label label1 = new Label(63, 1, 14, "Push Enter after selection.");

        addChildComponent(headLabel);
        addChildComponent(messageLabel);
        for (Card card:cards)
            addChildComponent(card);
        cards[0].setSelected();

        addChildComponent(label1);
    }

    public int listenOnSelection(){
        while(true){
            switch (GlobalController.keyboardListener.listenCharInput()){
                case 'a':
                case 'A':
                    goPrevious();
                    break;
                case 'd':
                case 'D':
                    goNext();
                    break;
                case 10:
                    display();
                    return currentSelectIndex;
                case 'p':
                case 'P':
                    return -1;
            }
        }
    }

    private void goPrevious(){
        cards[currentSelectIndex].setUnselected();
        if (currentSelectIndex>0)
            currentSelectIndex--;
        else
            currentSelectIndex = cards.length-1;
        cards[currentSelectIndex].setSelected();
    }

    private void goNext(){
        cards[currentSelectIndex].setUnselected();
        if (currentSelectIndex<cards.length-1)
            currentSelectIndex++;
        else
            currentSelectIndex = 0;
        cards[currentSelectIndex].setSelected();

    }

    public void display() {
        cards[currentSelectIndex].flip();
        for (Card card: cards)
            card.flip();
    }
}
