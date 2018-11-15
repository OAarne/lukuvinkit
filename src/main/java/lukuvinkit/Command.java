package lukuvinkit;

import java.util.function.BiConsumer;

public enum Command {

    HELP("ohje", "tulostaa ohjeen", Command::printHelpImplementation),
    CREATE("lisää", "lisää uuden lukuvinkin", Command::addReadingTipImplementation);

    private String commandString;
    private String helpText;
    private BiConsumer<CommandInterpreter, String[]> handler;

    private Command(String command, String helpText, BiConsumer<CommandInterpreter, String[]> handler) {
        this.commandString = command;
        this.helpText = helpText;
        this.handler = handler;
    }

    public String getCommandString() {
        return this.commandString;
    }

    public String getHelpText() {
        return this.helpText;
    }

    public BiConsumer<CommandInterpreter, String[]> getHandler() {
        return this.handler;
    }

    // Command methods
    public static void printHelpImplementation(CommandInterpreter interpreter, String[] args) {
        System.out.println("Tuetut komennot:");
        for (Command cmd : Command.values()) {
            System.out.println(cmd.commandString + " - " + cmd.helpText);
        }
    }

    public static void addReadingTipImplementation(CommandInterpreter interpreter, String[] args) {
        String title, description;
        if (args.length == 3) {
            title = args[1];
            description = args[2];
        } else if (args.length == 1) {
            title = interpreter.prompt("Otsikko> ", "Nimetön vinkki");
            description = interpreter.prompt("Kuvaus> ", "");
        } else {
            System.err.println("Lisää-komennolle annettiin väärä määrä argumentteja!");
            return;
        }
        ReadingTip tip = new ReadingTip(title, description, null, null, null);
        interpreter.getStorage().addReadingTip(tip);
    }
}
