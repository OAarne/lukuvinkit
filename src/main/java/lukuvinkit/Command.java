package lukuvinkit;

import java.util.function.BiConsumer;

public enum Command {

    HELP("ohje", "tulostaa ohjeen", Command::printHelpImplementation),
    CREATE("lisää", "lisää uuden lukuvinkin", Command::addReadingTipImplementation),
    REMOVE("poista", "poistaa lukuvinkin", Command::removeReadingTipImplementation),
    LIST("listaa", "listaa olemassaolevat lukuvinkit", Command::listReadingTipsImplementation),
    PRINT_JSON("jsoniksi", "tulostaa nykyiset vinkit JSON-muodossa", Command::printJSONImplementation);

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
        interpreter.getIO().println("Tuetut komennot:");
        for (Command cmd : Command.values()) {
            interpreter.getIO().println(cmd.commandString + " - " + cmd.helpText);
        }
    }

    public static void addReadingTipImplementation(CommandInterpreter interpreter, String[] args) {
        ReadingTipField[] fields = ReadingTipField.values();
        ReadingTip tip = new ReadingTip();
        if (args.length == 1 + fields.length) {
            int i = 1;
            for (ReadingTipField field : fields) {
                if (!field.getType().validateString(args[i])) {
                    interpreter.getIO().println("Kentän " + field.getName() + " arvo ei ole kelvollinen.");
                    return;
                }
                tip.setFieldValueString(field, args[i]);
                i++;
            }
        } else if (args.length == 1) {
            for (ReadingTipField field : fields) {
                String value = null;
                do {
                    if (value != null) {
                        interpreter.getIO().println("Kentän " + field.getName() + " arvo ei ole kelvollinen.");
                        return;
                    }
                    value = interpreter.prompt(field.getName() + "> ", "");
                } while (!field.getType().validateString(value));
                tip.setFieldValueString(field, value);
            }
        } else {
            interpreter.getIO().println("Lisää-komennolle annettiin väärä määrä argumentteja!");
            return;
        }
        int id = interpreter.getStorage().addReadingTip(tip);
        interpreter.getIO().println("Lisättiin vinkki tunnisteella " + id + ".");
    }

    public static void listReadingTipsImplementation(CommandInterpreter interpreter, String[] args) {
        interpreter.getStorage().getReadingTips().forEach(entry -> {
            interpreter.getIO().print(entry.getKey());
            ReadingTip tip = entry.getValue();
            for (ReadingTipField field : ReadingTipField.values()) {
                interpreter.getIO().print(" | " + tip.getFieldValueString(field));
            }
            interpreter.getIO().println();
        });
    }

    public static void removeReadingTipImplementation(CommandInterpreter interpreter, String[] args) {
        int id;
        try {
            if (args.length == 1) {
                id = Integer.parseInt(interpreter.prompt("Tunniste> ", ""));
            } else if (args.length == 2) {
                id = Integer.parseInt(args[1]);
            } else {
                interpreter.getIO().println("Poista-komennolle annettiin väärä määrä argumentteja!");
                return;
            }
        } catch (NumberFormatException e) {
            interpreter.getIO().println("Annettu tunniste on virheellinen.");
            return;
        }
        if (!interpreter.getStorage().getReadingTipById(id).isPresent()) {
            interpreter.getIO().println("Annetulla tunnisteella ei ole lukuvinkkiä.");
            return;
        }
        interpreter.getStorage().removeReadingTipById(id);
    }

    public static void printJSONImplementation(CommandInterpreter interpreter, String[] args) {
        interpreter.getIO().println(interpreter.getStorage().toJSON());
    }
}
