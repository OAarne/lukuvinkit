import cucumber.api.java.fi.Kun;
import cucumber.api.java.fi.Niin;
import lukuvinkit.CommandInterpreter;
import lukuvinkit.Storage;
import lukuvinkit.StubIO;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

public class Stepdefs {
    CommandInterpreter app;
    StubIO io;
    Storage storage = new Storage();
    List<String> inputLines = new ArrayList<>();

//    @Given("^application is running$")
//    public void application_is_running() throws Throwable { }

    @Kun("^komento \"([^\"]*)\" syötetään$")
    public void commandIsEntered(String command) {
        inputLines.add(command);
    }

    @Kun("^käyttäjä on lisännyt vinkin ja poistanut sen annetun tunnisteen avulla$")
    public void käyttäjäOnLisääVinkinJaPoistaaSenAnnetunTunnisteenAvulla() throws Throwable {
        inputLines.add("lisää Kirja takakansiteksti 1234");
        inputLines.add("poista 0");
    }

    @Niin("ohjelma sulkeutuu")
    public void applicationIsClosed() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        io = new StubIO(inputLines);
        app = new CommandInterpreter(storage, io);
        app.mainLoop();
    }

    @Niin("^listalla ei ole yhtään vinkkiä$")
    public void listallaEiOleYhtäänVinkkiä() throws Throwable {
        inputLines.add("listaa");
        inputLines.add("lopeta");
        io = new StubIO(inputLines);
        app = new CommandInterpreter(storage, io);
        app.mainLoop();
        List<String> output = io.getOutputs();
        assertTrue(output.get(output.size()-2).contains("Tunniste | Otsikko | "));
    }

    @Kun("^Vinkki otsikolla \"([^\"]*)\" ja kuvauksella \"([^\"]*)\" on lisätty$")
    public void vinkkiOtsikollaJaKuvauksellaOnLisätty(String otsikko, String kuvaus) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        inputLines.add("vinkki otsikko=\"" + otsikko + "\" kuvaus=\"" + kuvaus + "\"");
    }

    @Kun("^Ohjelma on käynnistetty uudelleen$")
    public void ohjelmaOnKäynnistettyUudelleen() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        inputLines.add("lopeta");
        io = new StubIO(inputLines);
        app = new CommandInterpreter(storage, io);
        app.mainLoop();
        inputLines = new ArrayList<>();
    }

    @Niin("^tulosteessa esiintyy vinkki otsikolla \"([^\"]*)\" ja kuvauksella \"([^\"]*)\"$")
    public void tulosteessaEsiintyyVinkkiOtsikollaJaKuvauksella(String otsikko, String kuvaus) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        inputLines.add("listaa");
        inputLines.add("lopeta");

        io = new StubIO(inputLines);
        app = new CommandInterpreter(storage, io);
        app.mainLoop();
        List<String> output = io.getOutputs();

        assertTrue(output.stream().anyMatch(s -> s.contains(otsikko) && s.contains(kuvaus)));
    }
}
