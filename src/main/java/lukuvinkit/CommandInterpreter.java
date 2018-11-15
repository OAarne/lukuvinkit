package lukuvinkit;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class CommandInterpreter implements AutoCloseable {
    private static Map<String, Command> COMMANDS = new HashMap<>();
    static {
        for (Command command : Command.values()) {
            COMMANDS.put(command.commandString, command);
        }
    }
    
    public static enum Command {
        HELP("ohje", "tulostaa ohjeen", (interpreter, args) -> {
            System.out.println("Tuetut komennot:");
            for (Command cmd : Command.values()) {
                System.out.println(cmd.commandString + " - " + cmd.helpText);
            }
        }),
        CREATE("lisää", "lisää uuden lukuvinkin", (interpreter, args) -> {
            String title, description;
            if (args.length == 3) {
                title = args[1];
                description = args[2];
            }
            else if (args.length == 1) {
                title = interpreter.prompt("Otsikko> ", "Nimetön vinkki");
                description = interpreter.prompt("Kuvaus> ", "");
            }
            else {
                System.err.println("Lisää-komennolle annettiin väärä määrä argumentteja!");
                return;
            }
            ReadingTip tip = new ReadingTip(title, description, null, null, null);
            interpreter.storage.addEntry(tip);
        });
        
        private String commandString;
        private String helpText;
        private BiConsumer<CommandInterpreter, String[]> handler;
        
        private Command(String command, String helpText, BiConsumer<CommandInterpreter, String[]> handler) {
            this.commandString = command;
            this.helpText = helpText;
            this.handler = handler;
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
    
    private String prompt(String prompt, String defaultValue) {
        System.out.print(prompt);
        System.out.flush();
        try {
            String input = reader.readLine();
            return input == null ? defaultValue : input;
        } catch (IOException e) {
            System.err.println("Virhe syötteen luvussa!");
            return "";
        }
    }
    
    public void mainLoop() {
        System.out.println(COMMANDS.values().size());
        loop: while (true) {
            String command = prompt("> ", "lopeta");
            if (command.equals("lopeta") || command.equals("poistu")) {
                break;
            } else {
                String[] args = command.split(" ");
                Command cmdObj = COMMANDS.get(args[0]);
                if (cmdObj != null) {
                    cmdObj.handler.accept(this, args);
                }
                else {
                    System.err.println("Tuntematon komento! Syötä \"ohje\" saadaksesi lisätietoja.");
                }
            }
        }
    }
}
