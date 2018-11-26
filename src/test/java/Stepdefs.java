import cucumber.api.java.fi.Kun;
import cucumber.api.java.fi.Niin;
import lukuvinkit.CommandInterpreter;
import lukuvinkit.Storage;
import lukuvinkit.StubIO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class Stepdefs {
    CommandInterpreter app;
    StubIO io;
    Storage storage = new Storage();
    List<String> inputLines = new ArrayList<>();

    @Kun("^komento \"([^\"]*)\" syötetään$")
    public void commandIsEntered(String command) {
        inputLines.add(command);
    }

    @Kun("^käyttäjä on lisännyt vinkin ja poistanut sen annetun tunnisteen avulla$")
    public void käyttäjäOnLisääVinkinJaPoistaaSenAnnetunTunnisteenAvulla() throws Throwable {
        inputLines.add("lisää Kirja takakansiteksti 1234");
        inputLines.add("poista 0");
    }

    @Kun("^Vinkki otsikolla \"([^\"]*)\" ja kuvauksella \"([^\"]*)\" on lisätty$")
    public void vinkkiOtsikollaJaKuvauksellaOnLisätty(String otsikko, String kuvaus) throws Throwable {
        inputLines.add("vinkki Otsikko=\"" + otsikko + "\" Kuvaus=\"" + kuvaus + "\"");
    }

    @Kun("^Ohjelma on käynnistetty uudelleen$")
    public void ohjelmaOnKäynnistettyUudelleen() throws Throwable {
        inputLines.add("lopeta");
        io = new StubIO(inputLines);
        app = new CommandInterpreter(storage, io);
        app.mainLoop();
        inputLines = new ArrayList<>();
    }

    @Kun("^Vinkki otsikolla \"([^\"]*)\" ja kuvauksella \"([^\"]*)\" on poistettu$")
    public void vinkkiOtsikollaJaKuvauksellaOnPoistettu(String otsikko, String kuvaus) throws Throwable {
        inputLines.add("listaa");
        inputLines.add("lopeta");
        io = new StubIO(inputLines);
        app = new CommandInterpreter(storage, io);
        app.mainLoop();
        List<String> output = io.getOutputs();
        inputLines = new ArrayList<>();

        List<String> vastaavatVinkit = output.stream().filter(s -> s.contains(otsikko) && s.contains(kuvaus)).collect(Collectors.toList());
        assertEquals(1, vastaavatVinkit.size());

        String vinkki = vastaavatVinkit.get(0);
        String[] vinkinOsat = vinkki.split(" ");
        String id = vinkinOsat[0];

        inputLines.add("poista " + id);
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

    @Niin("ohjelma sulkeutuu")
    public void applicationIsClosed() throws Throwable {
        io = new StubIO(inputLines);
        app = new CommandInterpreter(storage, io);
        app.mainLoop();
    }

    @Niin("^tulosteessa esiintyy vinkki otsikolla \"([^\"]*)\" ja kuvauksella \"([^\"]*)\"$")
    public void tulosteessaEsiintyyVinkkiOtsikollaJaKuvauksella(String otsikko, String kuvaus) throws Throwable {
        esiintyyköTulosteessaVinkkiOtsikollaJaKuvauksella(otsikko, kuvaus, true);

    }

    @Niin("^tulosteessa ei esiinny vinkkiä otsikolla \"([^\"]*)\" ja kuvauksella \"([^\"]*)\"$")
    public void tulosteessaEiEsiinnyVinkkiäOtsikollaJaKuvauksella(String otsikko, String kuvaus) throws Throwable {
        esiintyyköTulosteessaVinkkiOtsikollaJaKuvauksella(otsikko, kuvaus, false);
    }
    
    // apumetodit

    public void esiintyyköTulosteessaVinkkiOtsikollaJaKuvauksella(String otsikko, String kuvaus, boolean esiintyy) {
        inputLines.add("listaa");
        inputLines.add("lopeta");

        io = new StubIO(inputLines);
        app = new CommandInterpreter(storage, io);
        app.mainLoop();
        List<String> output = io.getOutputs();

        if (esiintyy) {
            assertTrue(output.stream().anyMatch(s -> s.contains(otsikko) && s.contains(kuvaus)));
        } else {
            assertFalse(output.stream().anyMatch(s -> s.contains(otsikko) && s.contains(kuvaus)));
        }
    }
}
