package systemstepdefs;

import cucumber.api.java.fi.Kun;
import cucumber.api.java.fi.Niin;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lukuvinkit.CommandInterpreter;
import lukuvinkit.Storage;
import lukuvinkit.StubIO;
import lukuvinkit.TipType;
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

    @Kun("^käyttäjä on lisännyt kirjan otsikolla \"([^\"]*)\" ja kuvauksella \"([^\"]*)\"$")
    public void käyttäjäOnLisännytKirjanOtsikollaJaKuvauksella(String otsikko, String kuvaus) throws Throwable {
        inputLines.add("kirja Otsikko=\"" + otsikko + "\" Kuvaus=\"" + kuvaus + "\"");
    }

    @Kun("^Ohjelma on käynnistetty uudelleen$")
    public void ohjelmaOnKäynnistettyUudelleen() throws Throwable {
        inputLines.add("lopeta");
        io = new StubIO(inputLines);
        app = new CommandInterpreter(storage, io, Optional.empty());
        app.mainLoop();
        inputLines = new ArrayList<>();
    }

    @Kun("^Vinkki otsikolla \"([^\"]*)\" ja kuvauksella \"([^\"]*)\" on poistettu$")
    public void vinkkiOtsikollaJaKuvauksellaOnPoistettu(String otsikko, String kuvaus) throws Throwable {
        inputLines.add("listaa");
        inputLines.add("lopeta");
        io = new StubIO(inputLines);
        app = new CommandInterpreter(storage, io, Optional.empty());
        app.mainLoop();
        List<String> output = io.getOutputs();
        inputLines = new ArrayList<>();

        List<String> vastaavatVinkit = output.stream().filter(s -> s.contains(otsikko) && s.contains(kuvaus)).collect(Collectors.toList());
        assertEquals(1, vastaavatVinkit.size());

        String vinkki = vastaavatVinkit.get(0);
        String[] vinkinOsat = vinkki.split(" ");
        String id = vinkinOsat[1];

        inputLines.add("poista " + id);
    }

    @Kun("^käyttäjä on lisännyt artikkelin otsikolla \"([^\"]*)\" ja kuvauksella \"([^\"]*)\"$")
    public void käyttäjäOnLisännytArtikkelinOtsikollaJaKuvauksella(String otsikko, String kuvaus) throws Throwable {
        inputLines.add("artikkeli Otsikko=\"" + otsikko + "\" Kuvaus=\"" + kuvaus + "\"");
    }

    @Niin("^listalla ei ole yhtään vinkkiä$")
    public void listallaEiOleYhtäänVinkkiä() throws Throwable {
        inputLines.add("listaa");
        inputLines.add("lopeta");
        io = new StubIO(inputLines);
        app = new CommandInterpreter(storage, io, Optional.empty());
        app.mainLoop();
        List<String> output = io.getOutputs();
        assertTrue(output.get(output.size() - 3).contains("Tunniste | Otsikko | "));
    }

    @Niin("ohjelma sulkeutuu")
    public void applicationIsClosed() throws Throwable {
        io = new StubIO(inputLines);
        app = new CommandInterpreter(storage, io, Optional.empty());
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

    @Niin("^listalla on artikkeli otsikolla \"([^\"]*)\" ja kuvauksella \"([^\"]*)\"$")
    public void listallaOnArtikkeliOtsikollaJaKuvauksella(String otsikko, String kuvaus) throws Throwable {
        inputLines.add("listaa");
        inputLines.add("lopeta");

        io = new StubIO(inputLines);
        app = new CommandInterpreter(storage, io, Optional.empty());
        app.mainLoop();
        List<String> output = io.getOutputs();

        assertTrue(output.stream().anyMatch(
            s -> s.contains(otsikko) && s.contains(kuvaus) && s.contains(TipType.ARTICLE.getFinnishTranslation())
        ));
    }

    @Niin("^listalla on kirja otsikolla \"([^\"]*)\" ja kuvauksella \"([^\"]*)\"$")
    public void listallaOnKirjaOtsikollaJaKuvauksella(String otsikko, String kuvaus) throws Throwable {
        inputLines.add("listaa");
        inputLines.add("lopeta");

        io = new StubIO(inputLines);
        app = new CommandInterpreter(storage, io, Optional.empty());
        app.mainLoop();
        List<String> output = io.getOutputs();

        assertTrue(output.stream().anyMatch(
            s -> s.contains(otsikko) && s.contains(kuvaus) && s.contains(TipType.BOOK.getFinnishTranslation())
        ));
    }

    // apumetodit

    public void esiintyyköTulosteessaVinkkiOtsikollaJaKuvauksella(String otsikko, String kuvaus, boolean esiintyy) {
        inputLines.add("listaa");
        inputLines.add("lopeta");

        io = new StubIO(inputLines);
        app = new CommandInterpreter(storage, io, Optional.empty());
        app.mainLoop();
        List<String> output = io.getOutputs();

        if (esiintyy) {
            assertTrue(output.stream().anyMatch(s -> s.contains(otsikko) && s.contains(kuvaus)));
        } else {
            assertFalse(output.stream().anyMatch(s -> s.contains(otsikko) && s.contains(kuvaus)));
        }
    }
}
