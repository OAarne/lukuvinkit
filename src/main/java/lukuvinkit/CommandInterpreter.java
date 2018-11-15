package lukuvinkit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class CommandInterpreter implements AutoCloseable {

    private static Map<String, Command> COMMANDS = new HashMap<>();

    static {
        for (Command command : Command.values()) {
            COMMANDS.put(command.getCommandString(), command);
        }
    }

    private Storage storage;
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public CommandInterpreter(Storage storage) {
        this.storage = storage;
    }

    public void close() throws IOException {
        this.reader.close();
    }

    public Storage getStorage() {
        return this.storage;
    }

    /**
     * Query an input from the user using a prompt
     *
     * @param prompt the prompt shown to the user
     * @param defaultValue This value is returned if the input stream is closed.
     * @return The inputted text.
     */
    public String prompt(String prompt, String defaultValue) {
        System.out.print(prompt);
        System.out.flush();
        try {
            String input = reader.readLine();
            return input == null ? defaultValue : input;
        } catch (IOException e) {
            System.err.println("Virhe syötteen luvussa!");
            return defaultValue;
        }
    }

    public void mainLoop() {
        System.out.println(COMMANDS.values().size());
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
                    System.err.println("Tuntematon komento! Syötä \"ohje\" saadaksesi lisätietoja.");
                }
            }
        }
    }
}
