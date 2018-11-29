package lukuvinkit;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.IntStream;

public enum Command {

    HELP("ohje", "tulostaa ohjeen", Command::printHelpImplementation),
    CREATE("vinkki", "lisää uuden muu-tyyppisen lukuvinkin", Command::addReadingTipImplementation),
    CREATE_BOOK("kirja", "lisää uuden kirja-tyyppisen lukuvinkin", Command::addBookImplementation),
    CREATE_ARTICLE("artikkeli", "lisää uuden artikkeli-tyyppisen lukuvinkin", Command::addArticleImplementation),
    MARK_READ("luettu", "merkitsee lukuvinkin luetuksi", Command::markReadImplementation),
    REMOVE("poista", "poistaa lukuvinkin", Command::removeReadingTipImplementation),
    LIST("listaa", "listaa olemassaolevat lukuvinkit", Command::listReadingTipsImplementation),
    SEARCH("hae", "listaa annettuja tietoja vastaavat vinkit", Command::searchReadingTipImplementation),
    PRINT_JSON("jsoniksi", "tulostaa nykyiset vinkit JSON-muodossa", Command::printJSONImplementation);

    private String commandString;

    private String helpText;
    private BiConsumer<CommandInterpreter, String[]> handler;

    private Command(String command, String helpText, BiConsumer<CommandInterpreter, String[]> handler) {
        this.commandString = command;
        this.helpText = helpText;
        this.handler = handler;
    }

    // Command implementations
    public static void printHelpImplementation(CommandInterpreter interpreter, String[] args) {
        interpreter.getIO().println("Tuetut komennot:");
        for (Command cmd : Command.values()) {
            interpreter.getIO().println(cmd.commandString + " - " + cmd.helpText);
        }
    }

    public static void addReadingTipImplementation(CommandInterpreter interpreter, String[] args) {
        addReadingTipOfGivenType(interpreter, args, TipType.OTHER);
    }

    private static void addBookImplementation(CommandInterpreter interpreter, String[] args) {
        addReadingTipOfGivenType(interpreter, args, TipType.BOOK);
    }

    private static void addArticleImplementation(CommandInterpreter interpreter, String[] args) {
        addReadingTipOfGivenType(interpreter, args, TipType.ARTICLE);
    }

    private static void addReadingTipOfGivenType(CommandInterpreter interpreter, String[] args, TipType tipType) {
        ReadingTipField<?>[] fields =
            ReadingTipField.VALUES.stream()
            .filter(field -> field.getAssociatedTipTypes().contains(tipType))
            .toArray(ReadingTipField[]::new);
        ReadingTip tip = new ReadingTip();
        tip.setFieldValue(ReadingTipField.TYPE, tipType);

        if (args.length > 1) {
            for (int i = 1; i < args.length; i++) {
                int sepIndex = args[i].indexOf('=');
                if (sepIndex == -1) {
                    interpreter.getIO().println("Argumentti `" + args[i] + "' ei ole kelvollinen (pitää olla muotoa `Kenttä=arvo').");
                    return;
                }
                String fieldName = args[i].substring(0, sepIndex);
                String fieldValue = args[i].substring(sepIndex+1);
                ReadingTipField<?> field = ReadingTipField.VALUE_MAP.get(fieldName);
                if (field == null) {
                    interpreter.getIO().println("Kenttää `" + fieldName + "' ei ole olemassa.");
                    return;
                }
                if (!field.getType().validateString(fieldValue)) {
                    interpreter.getIO().println("Kentän `" + fieldName + "' arvo `" + fieldValue + "' ei ole kelvollinen.");
                    return;
                }
                tip.setFieldValueString(field, fieldValue);
            }
        } else if (args.length == 1) {
            for (ReadingTipField<?> field : fields) {
                Optional<String> value = Optional.empty();
                do {
                    if (value.isPresent()) {
                        interpreter.getIO().println("Kentän `" + field.getName() + "' arvo ei ole kelvollinen.");
                    }
                    value = interpreter.prompt(field.getName() + "> ");
                } while (value.isPresent() && !field.getType().validateString(value.get()));
                if (value.isPresent()) {
                    tip.setFieldValueString(field, value.get());
                } else {
                    interpreter.getIO().println("Kentän `" + field.getName() + "' arvoa ei annettu. Lopetetaan lisääminen.");
                    return;
                }
            }
        } else {
            interpreter.getIO().println("Lisää-komennolle annettiin väärä määrä argumentteja!");
            return;
        }
        int id = interpreter.getStorage().addReadingTip(tip);
        interpreter.getIO().println("Lisättiin vinkki tunnisteella " + id + ".");
    }

    public static void listReadingTipsImplementation(CommandInterpreter interpreter, String[] args) {
        printTipList(interpreter, interpreter.getStorage().getReadingTips());
    }

    public static void markReadImplementation(CommandInterpreter interpreter, String[] args) {
        int id;
        if (args.length == 1) {
            id = promptId(interpreter);
        } else if (args.length == 2) {
            try {
                id = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                interpreter.getIO().println("Annettu tunniste on virheellinen.");
                return;
            }
        } else {
            interpreter.getIO().println("Poista-komennolle annettiin väärä määrä argumentteja!");
            return;
        }
        if (!validateId(interpreter, id)) {
            return;
        }
        interpreter.getStorage().getReadingTipById(id).get().setFieldValue(ReadingTipField.IS_READ, Boolean.TRUE);
    }

    public static void removeReadingTipImplementation(CommandInterpreter interpreter, String[] args) {
        int id;
        if (args.length == 1) {
            id = promptId(interpreter);
        } else if (args.length == 2) {
            try {
                id = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                interpreter.getIO().println("Annettu tunniste on virheellinen.");
                return;
            }
        } else {
            interpreter.getIO().println("Poista-komennolle annettiin väärä määrä argumentteja!");
            return;
        }
        if (!validateId(interpreter, id)) {
            return;
        }
        interpreter.getStorage().removeReadingTipById(id);
    }

    private static void searchReadingTipImplementation(CommandInterpreter interpreter, String[] args) {
        ReadingTipField<?>[] fields = ReadingTipField.VALUES.stream().toArray(ReadingTipField[]::new);
        HashMap<ReadingTipField<?>, String> tip = new HashMap<>();

        if (args.length > 1) {
            interpreter.getIO().println("Tällaista hakua ei vielä tueta");
        } else if (args.length == 1) {
            for (ReadingTipField<?> field : fields) {
                Optional<String> value = Optional.empty();
                value = interpreter.prompt(field.getName() + "> ");
                tip.put(field, value.get());
            }
        } else {
            interpreter.getIO().println("Komennolle annettiin väärä määrä argumentteja!");
            return;
        }
        List<Map.Entry<Integer, ReadingTip>> searchSpace = interpreter.getStorage().getReadingTips();
        ArrayList<Map.Entry<Integer, ReadingTip>> results = new ArrayList<>();

        for (Map.Entry<Integer, ReadingTip> entry : searchSpace) {
            boolean match = true;
            for (ReadingTipField<?> field : fields) {
                String entryField = entry.getValue().getFieldValueString(field);
                String tipField = tip.get(field);
                String[] terms = tipField.split(" ");
                if (!Arrays.stream(terms).anyMatch(s -> entryField.contains(s))) {
                    match = false;
                    break;
                }
            }
            if (match) results.add(entry);
        }

        printTipList(interpreter, results);
    }

    private static int promptId(CommandInterpreter interpreter) {
        int id = -1;
        do {
            try {
                id = Integer.parseInt(interpreter.prompt("Tunniste> ", ""));
            } catch (NumberFormatException e) {
                interpreter.getIO().println("Annettu tunniste on virheellinen.");
                continue;
            }
            if (!validateId(interpreter, id)) {
                continue;
            }
        } while (false);
        return id;
    }

    private static boolean validateId(CommandInterpreter interpreter, int id) {
        if (!interpreter.getStorage().getReadingTipById(id).isPresent()) {
            interpreter.getIO().println("Annetulla tunnisteella ei ole lukuvinkkiä.");
            return false;
        } else {
            return true;
        }
    }

    private static void printTipList(CommandInterpreter interpreter, List<Map.Entry<Integer, ReadingTip>> tips) {
        List<Map.Entry<Integer, ReadingTip>> entries = tips;
        List<ReadingTipField<?>> fields = ReadingTipField.VALUES;

        String[][] outputMatrix = new String[ReadingTipField.VALUES.size() + 1][entries.size() + 1];
        int[] columnMaxWidth = new int[ReadingTipField.VALUES.size() + 1];

        outputMatrix[0][0] = "Tunniste";
        columnMaxWidth[0] = 8;
        for (int x = 1; x <= fields.size(); x++) {
            ReadingTipField<?> field = fields.get(x - 1);
            outputMatrix[x][0] = field.getName();
            columnMaxWidth[x] = field.getName().length();
            for (int y = 1; y <= entries.size(); y++) {
                String value = entries.get(y-1).getValue().getFieldValueString(field);
                if (value.length() > 20) {
                    if (value.length() > 40) value = value.substring(0, 40);
                    value += "...";
                }
                outputMatrix[x][y] = value;
                if (value.length() > columnMaxWidth[x]) columnMaxWidth[x] = value.length();
            }
        }
        IntStream
            .rangeClosed(1, entries.size())
            .forEachOrdered(y -> outputMatrix[0][y] = entries.get(y-1).getKey().toString());

        IO io = interpreter.getIO();
        for (int y = 0; y <= entries.size(); y++) {
            io.print("|");
            for (int x = 0; x <= fields.size(); x++) {
                io.print(" ");
                io.print(outputMatrix[x][y]);
                IntStream
                    .range(0, columnMaxWidth[x] - outputMatrix[x][y].length())
                    .forEach(_i -> io.print(" "));
                io.print(" |");
            }
            io.println();

            if (y == 0) {
                io.println(String.join("", Collections.nCopies(IntStream.of(columnMaxWidth).map(i -> i + 3).sum() + 1, "-")));
            }
        }
    }

    public static void printJSONImplementation(CommandInterpreter interpreter, String[] args) {
        interpreter.getIO().println(interpreter.getStorage().toJSON());
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
}
