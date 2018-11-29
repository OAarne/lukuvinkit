package lukuvinkit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CommandInterpreter {

    private static Map<String, Command> COMMANDS = new HashMap<>();

    static {
        for (Command command : Command.values()) {
            COMMANDS.put(command.getCommandString(), command);
        }
    }

    private Storage storage;
    private IO io;
    private Optional<String> saveFile;

    public CommandInterpreter(Storage storage, IO io, Optional<String> saveFile) {
        this.storage = storage;
        this.io = io;
        this.saveFile = saveFile;
    }

    public CommandInterpreter(IO io, String saveFile) throws IOException {
        this(FileSave.loadStorage(saveFile), io, Optional.of(saveFile));
    }

    public Storage getStorage() {
        return this.storage;
    }

    public IO getIO() {
        return this.io;
    }

    /**
     * Query an input from the user using a prompt.
     *
     * @param prompt the prompt shown to the user
     * @param defaultValue This value is returned if the input stream is closed.
     * @return The inputted text.
     */
    public String prompt(String prompt, String defaultValue) {
        return prompt(prompt).orElse(defaultValue);
    }

    /**
     * Query an input from the user using a prompt.
     *
     * @param prompt the prompt shown to the user
     * @return The inputted text, or {@code Optional.empty()} if the stream is closed.
     */
    public Optional<String> prompt(String prompt) {
        io.print(prompt);
        io.flush();
        try {
            return Optional.ofNullable(io.readLine());
        } catch (IOException e) {
            io.println("Virhe syötteen luvussa!");
            return Optional.empty();
        }
    }

    public void mainLoop() {
        io.println("Lukuvinkit-ohjelma. Kirjoita \"ohje\" saadaksesi listauksen komennoista.");
        while (true) {
            String command = prompt("> ", "lopeta");
            if (command.equals("lopeta") || command.equals("poistu")) {
                break;
            } else {
                char[] chars = command.trim().toCharArray();
                List<String> args = new ArrayList<>();
                String currentArg = "";
                boolean quote = false;
                for (int i = 0; i < chars.length; i++) {
                    if (chars[i] == '"') {
                        quote = !quote;
                    } else if (!quote && chars[i] == ' ') {
                        args.add(currentArg);
                        currentArg = "";
                    } else if (chars[i] == '\\' && i < chars.length-1) {
                        currentArg += escapeCode(chars[++i]);
                    } else {
                        currentArg += chars[i];
                    }
                }
                args.add(currentArg);
                Command cmdObj = COMMANDS.get(args.get(0));
                if (cmdObj != null) {
                    cmdObj.getHandler().accept(this, args.toArray(new String[0]));
                    if (saveFile.isPresent()) {
                        try {
                            FileSave.saveStorage(saveFile.get(), storage);
                        } catch (IOException e) {
                            io.println("Virhe tallennettaessa tiedostoon \"" + saveFile.get() + "\"!");
                            e.printStackTrace();
                        }
                    }
                } else {
                    io.println("Tuntematon komento! Syötä \"ohje\" saadaksesi lisätietoja.");
                }
            }
        }
    }

    private static String escapeCode(char code) {
        switch (code) {
            case 'n':
                return "\n";
            case 'r':
                return "\r";
            case 't':
                return "\t";
            default:
                return ""+code;
        }
    }
}
