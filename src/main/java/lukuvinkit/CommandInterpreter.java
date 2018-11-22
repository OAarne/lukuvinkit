package lukuvinkit;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CommandInterpreter {

    private static Map<String, Command> COMMANDS = new HashMap<>();

    static {
        for (Command command : Command.values()) {
            COMMANDS.put(command.getCommandString(), command);
        }
    }

    private Storage storage;
    private IO io;

    public CommandInterpreter(Storage storage, IO io) {
        this.storage = storage;
        this.io = io;
    }

    public Storage getStorage() {
        return this.storage;
    }

    public IO getIO() {
        return this.io;
    }

    /**
     * Query an input from the user using a prompt
     *
     * @param prompt the prompt shown to the user
     * @param defaultValue This value is returned if the input stream is closed.
     * @return The inputted text.
     */
    public String prompt(String prompt, String defaultValue) {
        io.print(prompt);
        io.flush();
        try {
            String input = io.readLine();
            return input == null ? defaultValue : input;
        } catch (IOException e) {
            io.println("Virhe syötteen luvussa!");
            return defaultValue;
        }
    }

    public void mainLoop() {
        io.println("Lukuvinkit-ohjelma. Kirjoita \"ohje\" saadaksesi listauksen komennoista.");
        while (true) {
            String command = prompt("> ", "lopeta");
            if (command.equals("lopeta") || command.equals("poistu")) {
                break;
            } else {
                String[] args = command.split(" ");
                Command cmdObj = COMMANDS.get(args[0]);
                if (cmdObj != null) {
                    cmdObj.getHandler().accept(this, args);
                } else {
                    io.println("Tuntematon komento! Syötä \"ohje\" saadaksesi lisätietoja.");
                }
            }
        }
    }
}
