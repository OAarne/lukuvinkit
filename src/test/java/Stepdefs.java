import cucumber.api.java.fi.Kun;
import cucumber.api.java.fi.Niin;
import lukuvinkit.CommandInterpreter;
import lukuvinkit.Storage;
import lukuvinkit.StubIO;

import java.util.ArrayList;
import java.util.List;

public class Stepdefs {
    CommandInterpreter app;
    StubIO io;
    Storage storage = new Storage();
    List<String> inputLines = new ArrayList();

//    @Given("^application is running$")
//    public void application_is_running() throws Throwable { }

    @Kun("^komento {string} syötetään$")
    public void commandIsEntered(String command) {
        inputLines.add(command);
    }


    @Niin("^ohjelma sulkeutuu$")
    public void applicationIsClosed() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        io = new StubIO(inputLines);
        app = new CommandInterpreter(storage, io);
    }
}
