package lukuvinkit.ui;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.IntStream;
import lukuvinkit.ReadingTip;
import lukuvinkit.ReadingTipField;
import lukuvinkit.TipType;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toSet;

public enum Command {

    HELP("ohje", "Ohjekomennot", "tulostaa ohjeen", Command::printHelpImplementation),
    CREATE("luo", "Luomiskomennot", "lisää uuden lukuvinkin", Command::addReadingTipImplementation),
    CREATE_OTHER("vinkki", "Luomiskomennot", "lisää uuden muu-tyyppisen lukuvinkin", Command::addOtherImplementation),
    CREATE_BOOK("kirja", "Luomiskomennot", "lisää uuden kirja-tyyppisen lukuvinkin", Command::addBookImplementation),
    CREATE_ARTICLE("artikkeli", "Luomiskomennot", "lisää uuden artikkeli-tyyppisen lukuvinkin", Command::addArticleImplementation),
    MARK_READ("luettu", "Muokkauskomennot", "merkitsee lukuvinkin luetuksi", Command::markReadImplementation),
    REMOVE("poista", "Muokkauskomennot", "poistaa lukuvinkin", Command::removeReadingTipImplementation),
    LIST("listaa", "Näyttämiskomennot", "listaa olemassaolevat lukuvinkit", Command::listReadingTipsImplementation),
    SEARCH("hae", "Näyttämiskomennot", "listaa annettuja tietoja vastaavat vinkit", Command::searchReadingTipImplementation),
    SHOW("näytä", "Näyttämiskomennot", "näyttää lukuvinkin tiedot", Command::showReadingTipImplementation),
    PRINT_JSON("jsoniksi", "Näyttämiskomennot", "tulostaa nykyiset vinkit JSON-muodossa", Command::printJSONImplementation);

    private String commandString;

    private String helpText;
    private String group;
    private BiConsumer<CommandInterpreter, String[]> handler;

    private Command(String command, String group, String helpText, BiConsumer<CommandInterpreter, String[]> handler) {
        this.commandString = command;
        this.group = group;
        this.helpText = helpText;
        this.handler = handler;
    }

    // Command implementations
    public static void printHelpImplementation(CommandInterpreter interpreter, String[] args) {
        IO io = interpreter.getIO();
        List<Command> commands = Arrays.asList(values());
        Set<String> groups = commands.stream().map(Command::getGroup).collect(toSet());
        groups.stream().sorted().forEachOrdered(group -> {
            io.println(group + ":");
            commands.stream()
                .filter(c -> c.group.equals(group))
                .sorted((a, b) -> a.commandString.compareTo(b.commandString))
                .forEachOrdered(c -> io.println("  " + c.commandString + " - " + c.helpText));
        });
    }

    public static void addReadingTipImplementation(CommandInterpreter interpreter, String[] args) {
        addReadingTipOfGivenType(interpreter, args, Optional.empty());
    }

    public static void addOtherImplementation(CommandInterpreter interpreter, String[] args) {
        addReadingTipOfGivenType(interpreter, args, Optional.of(TipType.OTHER));
    }

    private static void addBookImplementation(CommandInterpreter interpreter, String[] args) {
        addReadingTipOfGivenType(interpreter, args, Optional.of(TipType.BOOK));
    }

    private static void addArticleImplementation(CommandInterpreter interpreter, String[] args) {
        addReadingTipOfGivenType(interpreter, args, Optional.of(TipType.ARTICLE));
    }

    private static void addReadingTipOfGivenType(CommandInterpreter interpreter, String[] args, Optional<TipType> tipType) {
        Optional<ReadingTip> tip = readReadingTip(interpreter, args, tipType, true);
        if (!tip.isPresent()) return;
        int id = interpreter.getStorage().addReadingTip(tip.get());
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
        int id = argsOrPromptId(interpreter, args);
        if (id == -1) return;
        interpreter.getStorage().removeReadingTipById(id);
    }

    public static void showReadingTipImplementation(CommandInterpreter interpreter, String[] args) {
        int id = argsOrPromptId(interpreter, args);
        if (id == -1) return;
        ReadingTip tip = interpreter.getStorage().getReadingTipById(id).get();
        interpreter.getIO().println("Tunniste: " + id);
        for (ReadingTipField<?> field : ReadingTipField.VALUES) {
            interpreter.getIO().println(field.getName() + ": " + tip.getFieldValueString(field));
        }
    }

    private static int argsOrPromptId(CommandInterpreter interpreter, String[] args) {
        int id;
        if (args.length == 1) {
            id = promptId(interpreter);
        } else if (args.length == 2) {
            try {
                id = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                interpreter.getIO().println("Annettu tunniste on virheellinen.");
                return -1;
            }
        } else {
            interpreter.getIO().println("Poista-komennolle annettiin väärä määrä argumentteja!");
            return -1;
        }
        if (!validateId(interpreter, id)) {
            return -1;
        }
        return id;
    }

    private static void searchReadingTipImplementation(CommandInterpreter interpreter, String[] args) {
        Optional<ReadingTip> tip = readReadingTip(interpreter, args, Optional.empty(), false);
        if (!tip.isPresent()) return;
        TipType tipType = tip.get().getFieldValue(ReadingTipField.TYPE);

        List<Map.Entry<Integer, ReadingTip>> searchSpace = interpreter.getStorage().getReadingTips();
        ArrayList<Map.Entry<Integer, ReadingTip>> results = new ArrayList<>();

        for (Map.Entry<Integer, ReadingTip> entry : searchSpace) {
            TipType entryType = entry.getValue().getFieldValue(ReadingTipField.TYPE);
            if (tipType != TipType.OTHER && entryType != tipType) continue; // FIXME
            boolean match = true;
            for (ReadingTipField<?> field : tip.get().getPresentFields()) {
                String entryField = entry.getValue().getFieldValueString(field);
                String tipField = tip.get().getFieldValueString(field);
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

    private static Optional<ReadingTip> readReadingTip(CommandInterpreter interpreter, String[] argsArray, Optional<TipType> tipType, boolean validate) {

        List<String> args = new ArrayList<>(Arrays.asList(argsArray));
        args.remove(0); // poistetaan komennon nimi

        if (!tipType.isPresent() && args.size() >= 1 && args.get(0).indexOf("=") == -1) {
            String typeName = args.remove(0);
            TipType type = TipType.TIP_TYPES.get(typeName); // voi olla null
            if (type != null) {
                tipType = Optional.of(type);
            } else {
                interpreter.getIO().println(
                    "Argumentti `"
                    + typeName
                    + "' ei ole kelvollinen vinkkityyppi (joita ovat: "
                    + Arrays.asList(TipType.values()).stream().map(TipType::getName).collect(joining(", "))
                    + ").");
                return Optional.empty();
            }
        }

        final TipType finalTipType = tipType.orElse(TipType.OTHER);
        ReadingTipField<?>[] fields =
            ReadingTipField.VALUES.stream()
            .filter(field -> field.getAssociatedTipTypes().contains(finalTipType))
            .toArray(ReadingTipField[]::new);
        ReadingTip tip = new ReadingTip();
        tip.setFieldValue(ReadingTipField.TYPE, finalTipType);

        if (args.size() > 1) {
            for (int i = 0; i < args.size(); i++) {
                String arg = args.get(i);
                int sepIndex = arg.indexOf('=');
                if (sepIndex == -1) {
                    interpreter.getIO().println("Argumentti `" + arg + "' ei ole kelvollinen (pitää olla muotoa `Kenttä=arvo').");
                    return Optional.empty();
                }
                String fieldName = arg.substring(0, sepIndex);
                String fieldValue = arg.substring(sepIndex+1);
                ReadingTipField<?> field = ReadingTipField.VALUE_MAP.get(fieldName);
                if (field == null) {
                    interpreter.getIO().println("Kenttää `" + fieldName + "' ei ole olemassa.");
                    return Optional.empty();
                }
                if (validate && !field.getType().validateString(fieldValue)) {
                    interpreter.getIO().println("Kentän `" + fieldName + "' arvo `" + fieldValue + "' ei ole kelvollinen.");
                    return Optional.empty();
                }
                tip.setFieldValueString(field, fieldValue);
            }
        } else if (args.size() == 0) {
            for (ReadingTipField<?> field : fields) {
                Optional<String> value = Optional.empty();
                do {
                    if (value.isPresent()) {
                        interpreter.getIO().println("Kentän `" + field.getName() + "' arvo ei ole kelvollinen.");
                    }
                    value = interpreter.prompt(field.getName() + "> ");
                } while (value.isPresent() && validate && !field.getType().validateString(value.get()));
                if (value.isPresent()) {
                    tip.setFieldValueString(field, value.get());
                } else {
                    interpreter.getIO().println("Kentän `" + field.getName() + "' arvoa ei annettu. Lopetetaan lisääminen.");
                    return Optional.empty();
                }
            }
        } else {
            interpreter.getIO().println("Komennolle annettiin väärä määrä argumentteja!");
            return Optional.empty();
        }
        return Optional.of(tip);
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
        List<ReadingTipField<?>> fields = ReadingTipField.VISIBLE_VALUES;
        int numFields = fields.size() + 1;

        int terminalWidth = interpreter.getIO().getTerminalWidth() - 2;
        int maxWidth = terminalWidth <= 0 ? 40 : terminalWidth / numFields - 2;

        String[][] outputMatrix = new String[numFields][tips.size() + 1];
        int[] columnMaxWidth = new int[numFields];

        outputMatrix[0][0] = "Tunniste";
        columnMaxWidth[0] = 8;
        for (int x = 1; x <= fields.size(); x++) {
            ReadingTipField<?> field = fields.get(x - 1);
            outputMatrix[x][0] = field.getName();
            columnMaxWidth[x] = field.getName().length();
            for (int y = 1; y <= tips.size(); y++) {
                String value = tips.get(y-1).getValue().getFieldValueString(field);
                if (value.length() > maxWidth) {
                    value = value.substring(0, maxWidth-3) + "...";
                }
                outputMatrix[x][y] = value;
                if (value.length() > columnMaxWidth[x]) columnMaxWidth[x] = value.length();
            }
        }
        IntStream
            .rangeClosed(1, tips.size())
            .forEachOrdered(y -> outputMatrix[0][y] = tips.get(y-1).getKey().toString());

        IO io = interpreter.getIO();
        io.println();
        for (int y = 0; y <= tips.size(); y++) {
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
        io.println();
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

    public String getGroup() {
        return this.group;
    }

    public BiConsumer<CommandInterpreter, String[]> getHandler() {
        return this.handler;
    }
}
