package monopoly.View;

import java.util.Arrays;

public class PreLoadModels {
    static class  DiceModel {
        private static final String unknown = """
             .------.\s
            |   ┌─┐  |
            |    ┌┘  |
            |    o   |
             '------'\s""";

        private static final String number1 = """
             .------.\s
            |        |
            |    o   |
            |        |
             '------'\s""";

        private static final String number2 = """
             .------.\s
            |    o   |
            |        |
            |    o   |
             '------'\s""";

        private static final String number3 = """
             .------.\s
            |    o   |
            |    o   |
            |    o   |
             '------'\s""";

        private static final String number4 = """
             .------.\s
            |  o   o |
            |        |
            |  o   o |
             '------'\s""";

        private static final String number5 = """
             .------.\s
            |  o   o |
            |    o   |
            |  o   o |
             '------'\s""";

        private static final String number6 = """
             .------.\s
            |  o   o |
            |  o   o |
            |  o   o |
             '------'\s""";
    }

    static class CardModel {
        private static final String selected = """
                   ------  \s
                 |.------.|\s
                 ||      ||\s
                →||      ||←
                 ||      ||\s
                 |'------'|\s
                   ------  \s""";
        private static final String unselected = """
                \s          \s
                \s .------. \s
                \s |      | \s
                \s |      | \s
                \s |      | \s
                \s '------' \s
                \s          \s""";
    }

    static class MenuModel{
        private static final String menu = """
                 .-------------------------------------------.\s
                |                                             |
                |                                             |
                |                                             |
                |                                             |
                |                                             |
                |                                             |
                |                                             |
                |                                             |
                |                                             |
                |                                             |
                |                                             |
                |                                             |
                |                                             |
                |                                             |
                |                                             |
                 '-------------------------------------------'\s""";

        private static final String inputFieldSelected = """
                  --------------------------- \s
                →||                         ||←
                  --------------------------- \s""";

        private static final String inputFieldUnselected = """
                \s       ---------------       \s
                \s                             \s
                \s       ---------------       \s""";
    }

    static class MessageBox{
        private static final String messageBox = """
                 .-------------------------------------------------------------.\s
                |                                                               |
                |                                                               |
                |                                                               |
                |                                                               |
                |                                                               |
                |                                                               |
                |                                                               |
                |                                                               |
                |                                                               |
                |                                                               |
                |                                                               |
                |                                                               |
                |                                                               |
                |                                                               |
                |                                                               |
                 '-------------------------------------------------------------'\s""";

        private static final String house = """
             _______\s
            /  //  /\\
            |█▀█▀█|█|
            |█▀█▀█|█|
            |██▀██|█|""";

        private static final String jail = """
            ▃▃▃▃▃▃▃▃▃▃▃
             | | | | |\s
             | | | | |\s
             | | | | |\s
            ▀▀▀▀▀▀▀▀▀▀▀""";
    }

    static class MainPageModel {
        private static final String mainPage = """
                 .----------------------------------------------------------------------------------------------------------------.\s
                |                                                                                                                  |
                |              __          __  _                            _              ____________    ________                |
                |              \\ \\        / / | |                          | |            / _____/ ___ \\  <  / ___ \\               |
                |               \\ \\  /\\  / /__| | ___ ___  _ __ ___   ___  | |_ ___      / /  __/ /__/ /  / / /  / /               |
                |                \\ \\/  \\/ / _ \\ |/ __/ _ \\| '_ ` _ \\ / _ \\ | __/ _ \\    / /  / /  ____/  / / /  / /                |
                |                 \\  /\\  /  __/ | (_| (_) | | | | | |  __/ | || (_) |  / /__/ /  /      / / /__/ /                 |
                |                  \\/  \\/ \\___|_|\\___\\___/|_| |_| |_|\\___|  \\__\\___/   \\___ _/__/      /_/\\_____/                  |
                |                                                                                                                  |
                |          .___  ___.   ______   .__   __.   ______   .______     ______    __      ____    ____  __               |
                |          |   \\/   |  /  __  \\  |  \\ |  |  /  __  \\  |   _  \\   /  __  \\  |  |     \\   \\  /   / |  |              |
                |          |  \\  /  | |  |  |  | |   \\|  | |  |  |  | |  |_)  | |  |  |  | |  |      \\   \\/   /  |  |              |
                |          |  |\\/|  | |  |  |  | |  . `  | |  |  |  | |   ___/  |  |  |  | |  |       \\_    _/   |  |              |
                |          |  |  |  | |  `--'  | |  |\\   | |  `--'  | |  |      |  `--'  | |  `----.    |  |     |__|              |
                |          |__|  |__|  \\______/  |__| \\__|  \\______/  | _|       \\______/  |_______|    |__|     (__)              |
                |                                                                                                                  |
                |                                                                                                                  |
                |                                                                                                                  |
                |                                                                                                                  |
                |                                                                                                                  |
                |                                                                                                                  |
                |                                                                                                                  |
                |                                                                                                                  |
                |                                                                                                                  |
                |                                                                                                                  |
                |                                                                                                                  |
                |                                                                                                                  |
                |                                                                                                                  |
                |                                                                                                                  |
                |                                                                                                                  |
                |                                                                                                                  |
                |                                                                                                                  |
                |                                                                                                                  |
                |                                                                                                                  |
                |                                                                                                                  |
                |                                                                                                                  |
                |                                                                                                                  |
                |                                                                                                                  |
                |                                                                                                                  |
                |                                                                                                                  |
                 '----------------------------------------------------------------------------------------------------------------'\s""";

        private static final String settingTag = """
                 ____       _   _   _                \s
                / ___|  ___| |_| |_(_)_ __   __ _ ___\s
                \\___ \\ / _ \\ __| __| | '_ \\ / _` / __|
                 ___) |  __/ |_| |_| | | | | (_| \\__ \\
                |____/ \\___|\\__|\\__|_|_| |_|\\__, |___/
                                            |___/    \s""";

        private static final String menuTag = """
                 __  __ ___ _  _ _   _\s
                |  \\/  | __| \\| | | | |
                | |\\/| | _|| .` | |_| |
                |_|  |_|___|_|\\_|\\___/\s""";

    }

    private static class GameBoardModel{
        private static final String GameBoard = """
                 .---------------.                               .---------------.                                .---------------.\s
                | 1               |                             |  2              |                              |  3              |
                |                 |                             |                 |                              |                 |
                |                 |                             |                 |                              |                 |
                 '---------------'                               '---------------'                                '---------------'\s
                        .---------------.----------------.----------------.----------------.----------------.---------------.      \s
                       | 11             | 12             | 13       ┌─┐   | 14             | 15             | 16             |     \s
                       |      Free      |                |           ┌┘   |                |                |     Go TO      |     \s
                       |     Parking    |                |   CHANCE  o    |                |                |      JAIL      |     \s
                       |                |                |                |                |                |                |     \s
                       |----------------|----------------'----------------'----------------'----------------|----------------|     \s
                       | 10             |    s                          :                    Round:    /    | 17             |     \s
                       |                |                                                                   |                |     \s
                       |                |                       ________    ______                          |                |     \s
                       |                |                      / ___/ _ \\  <  / _ \\                         |                |     \s
                       |----------------|                     / (_ / ___/  / / // /                         |----------------|     \s
                       | 09       ┌─┐   |                     \\___/_/     /_/\\___/                          | 18             |     \s
                       |           ┌┘   |         ___  ________ _   _ ___________ _____ _   __   __         |                |     \s
                       |   CHANCE  o    |         |  \\/  |  _  | \\ | |  _  | ___ \\  _  | |  \\ \\ / /         |                |     \s
                       |                |         | .  . | | | |  \\| | | | | |_/ / | | | |   \\ V /          |                |     \s
                       |----------------|         | |\\/| | | | | . ` | | | |  __/| | | | |    \\ /           |----------------|     \s
                       | 08             |         | |  | \\ \\_/ / |\\  \\ \\_/ / |   \\ \\_/ / |____| |           | 19       ┌─┐   |     \s
                       |                |         \\_|  |_/\\___/\\_| \\_/\\___/\\_|    \\___/\\_____/\\_/           |           ┌┘   |     \s
                       |                |                                                                   |   CHANCE  o    |     \s
                       |                |                                                                   |                |     \s
                       |----------------|                                                                   |----------------|     \s
                       | 07             |                                                                   | 20             |     \s
                       |                |                                                                   |                |     \s
                       |                |                                                                   |                |     \s
                       |                |                              Pause (P)                            |                |     \s
                       |----------------|----------------.----------------.----------------.----------------|----------------|     \s
                       | 06   | IN JAIL | 05             | 04             | 03             | 02             | 01    GO       |     \s
                       |      '---------|                |   INCOME TAX   |                |                |   On Passing:  |     \s
                       |  JUST VISITING |                |     PAY   %    |                |                |   +      HKD   |     \s
                       |                |                |                |                |                | ←              |     \s
                        '---------------'----------------'----------------'----------------'----------------'---------------'      \s
                 .---------------.                               .---------------.                                .---------------.\s
                |  4              |                             |  5              |                              |  6              |
                |                 |                             |                 |                              |                 |
                |                 |                             |                 |                              |                 |
                 '---------------'                               '---------------'                                '---------------'\s""";

        private static final String messageBar = """
                 .-------------------------------------------------------------.\s
                |                                                               |
                |                                                               |
                 '-------------------------------------------------------------'\s""";
    }

    public char[][] diceUnknown;
    public char[][] diceNumber1;
    public char[][] diceNumber2;
    public char[][] diceNumber3;
    public char[][] diceNumber4;
    public char[][] diceNumber5;
    public char[][] diceNumber6;

    public char[][] luckyDrawCardSelected;
    public char[][] luckyDrawCardUnselected;

    public char[][] menu;
    public char[][] inputFieldSelected;
    public char[][] inputFieldUnselected;
    public char[][] messageBox;
    public char[][] house;
    public char[][] jail;

    public char[][] mainPage;
    public char[][] settingTag;
    public char[][] menuTag;

    public char[][] gameBoard;
    public char[][] messageBar;

    public PreLoadModels(){
        diceUnknown = load(DiceModel.unknown);
        diceNumber1 = load(DiceModel.number1);
        diceNumber2 = load(DiceModel.number2);
        diceNumber3 = load(DiceModel.number3);
        diceNumber4 = load(DiceModel.number4);
        diceNumber5 = load(DiceModel.number5);
        diceNumber6 = load(DiceModel.number6);

        luckyDrawCardSelected = load(CardModel.selected);
        luckyDrawCardUnselected = load(CardModel.unselected);

        menu = load(MenuModel.menu);
        inputFieldSelected = load(MenuModel.inputFieldSelected);
        inputFieldUnselected = load(MenuModel.inputFieldUnselected);
        messageBox = load(MessageBox.messageBox);
        house = load(MessageBox.house);
        jail = load(MessageBox.jail);

        mainPage = load(MainPageModel.mainPage);
        settingTag = load(MainPageModel.settingTag);
        menuTag = load(MainPageModel.menuTag);

        gameBoard = load(GameBoardModel.GameBoard);
        messageBar = load(GameBoardModel.messageBar);

    }


    private char[][] load(String model){
        char[][] content = getWidthAndHeight(model);
        int w = 0;
        int h = 0;
        for (int i=0; i<model.length();i++){
            char character = model.charAt(i);
            if (character=='\n'){
                h++;
                w=0;
                continue;
            }
            content[h][w] = character;
            w++;
        }
        return content;
    }

    private char[][] getWidthAndHeight(String model){
        int width = 0,height = 1;
        for (int i=0; i<model.length(); i++){
            if ((model.charAt(i)=='\n')){
                width = i;
                height = (model.length()+1) / (width + 1);
                break;
            }
        }
        return new char[height][width];
    }

    public static void main(String[] args){

        PreLoadModels models = new PreLoadModels();


        Window.printContent(models.diceUnknown);
        Window.printContent(models.diceNumber1);
        Window.printContent(models.diceNumber2);
        Window.printContent(models.diceNumber3);
        Window.printContent(models.diceNumber4);
        Window.printContent(models.diceNumber5);
        Window.printContent(models.diceNumber6);

        Window.printContent(models.luckyDrawCardSelected);
        Window.printContent(models.luckyDrawCardUnselected);

        Window.printContent(models.menu);
        Window.printContent(models.inputFieldUnselected);
        Window.printContent(models.inputFieldSelected);

        Window.printContent(models.messageBox);
        Window.printContent(models.house);
        Window.printContent(models.jail);

        Window.printContent(models.mainPage);
        Window.printContent(models.settingTag);
        Window.printContent(models.menuTag);

        Window.printContent(models.gameBoard);
        Window.printContent(models.messageBar);

    }
}
