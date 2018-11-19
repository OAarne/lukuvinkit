import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
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

    @When("^komento \"([^\"]*)\" syötetään$")
    public void commandIsEntered(String command) {
        inputLines.add(command);
    }


    @Then("^ohjelma sulkeutuu$")
    public void applicationIsClosed() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        io = new StubIO(inputLines);
        app = new CommandInterpreter(storage, io);
    }
}
