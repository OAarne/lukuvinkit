package lukuvinkit;

import java.util.function.BiConsumer;

public enum Command {

    HELP("ohje", "tulostaa ohjeen", Command::printHelpImplementation),
    CREATE("lisää", "lisää uuden lukuvinkin", Command::addReadingTipImplementation),
    LIST("listaa", "listaa olemassaolevat lukuvinkit", Command::listReadingTipsImplementation);

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

    // Command implementations
    
    public static void printHelpImplementation(CommandInterpreter interpreter, String[] args) {
        System.out.println("Tuetut komennot:");
        for (Command cmd : Command.values()) {
            System.out.println(cmd.commandString + " - " + cmd.helpText);
        }
    }

    public static void addReadingTipImplementation(CommandInterpreter interpreter, String[] args) {
        ReadingTipField[] fields = ReadingTipField.values();
        ReadingTip tip = new ReadingTip();
        if (args.length == 3) {
            int i = 1;
            for (ReadingTipField field : fields) {
                tip.setFieldValue(field, args[i++]);
            }
        } else if (args.length == 1) {
            for (ReadingTipField field : fields) {
                tip.setFieldValue(field, interpreter.prompt(field.getName() + "> ", ""));
            }
        } else {
            System.err.println("Lisää-komennolle annettiin väärä määrä argumentteja!");
            return;
        }
        int id = interpreter.getStorage().addReadingTip(tip);
        System.out.println("Lisättiin vinkki tunnisteella " + id + ".");
    }

    public static void listReadingTipsImplementation(CommandInterpreter interpreter, String[] args) {
        interpreter.getStorage().getReadingTips().forEach(entry -> {
            System.out.print(entry.getKey());
            ReadingTip tip = entry.getValue();
            for (ReadingTipField field : ReadingTipField.values()) {
                System.out.print(" | " + tip.getFieldValue(field));
            }
            System.out.println();
        });
    }
}
