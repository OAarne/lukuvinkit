package systemstepdefs;

import cucumber.api.PendingException;
import cucumber.api.java.fi.Kun;
import cucumber.api.java.fi.Niin;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import cucumber.api.java.fi.Oletetaan;
import lukuvinkit.ui.CommandInterpreter;
import lukuvinkit.Storage;
import lukuvinkit.StubIO;
import lukuvinkit.TipType;
import static org.junit.Assert.*;

public class Stepdefs {
    CommandInterpreter app;

    StubIO io;
    Storage storage = new Storage();
    List<String> inputLines = new ArrayList<>();

    @Oletetaan("on lisätty vinkit parametrein {string} ja {string}")
    public void onLisättyVinkitParametreinJa(String pars1, String pars2) {
        inputLines.add("luo " + pars1);
        inputLines.add("luo " + pars2);
    }

    @Kun("komento {string} syötetään")
    public void commandIsEntered(String command) {
        inputLines.add(command);
    }

    @Kun("käyttäjä on lisännyt vinkin ja poistanut sen annetun tunnisteen avulla")
    public void käyttäjäOnLisääVinkinJaPoistaaSenAnnetunTunnisteenAvulla() {
        inputLines.add("lisää Kirja takakansiteksti 1234");
        inputLines.add("poista 0");
    }

    @Kun("Vinkki otsikolla {string} ja kuvauksella {string} on lisätty")
    public void vinkkiOtsikollaJaKuvauksellaOnLisätty(String otsikko, String kuvaus) {
        inputLines.add("vinkki Otsikko=\"" + otsikko + "\" Kuvaus=\"" + kuvaus + "\"");
    }

    @Kun("käyttäjä on lisännyt kirjan otsikolla {string} ja kuvauksella {string}")
    public void käyttäjäOnLisännytKirjanOtsikollaJaKuvauksella(String otsikko, String kuvaus) {
        inputLines.add("kirja Otsikko=\"" + otsikko + "\" Kuvaus=\"" + kuvaus + "\"");
    }

    @Kun("Ohjelma on käynnistetty uudelleen")
    public void ohjelmaOnKäynnistettyUudelleen() {
        inputLines.add("lopeta");
        io = new StubIO(inputLines);
        app = new CommandInterpreter(storage, io, Optional.empty());
        app.mainLoop();
        inputLines = new ArrayList<>();
    }

    @Kun("Vinkki otsikolla {string} ja kuvauksella {string} on poistettu")
    public void vinkkiOtsikollaJaKuvauksellaOnPoistettu(String otsikko, String kuvaus) {
        inputLines.add("listaa");
        List<String> output = aja();
        inputLines = new ArrayList<>();

        List<String> vastaavatVinkit = output.stream().filter(s -> s.contains(otsikko) && s.contains(kuvaus)).collect(Collectors.toList());
        assertEquals(1, vastaavatVinkit.size());

        String vinkki = vastaavatVinkit.get(0);
        String[] vinkinOsat = vinkki.split(" ");
        String id = vinkinOsat[1];

        inputLines.add("poista " + id);
    }

    @Kun("käyttäjä on lisännyt artikkelin otsikolla {string} ja kuvauksella {string}")
    public void käyttäjäOnLisännytArtikkelinOtsikollaJaKuvauksella(String otsikko, String kuvaus) {
        inputLines.add("artikkeli Otsikko=\"" + otsikko + "\" Kuvaus=\"" + kuvaus + "\"");
    }

    @Kun("lukija on tehnyt haun parametreilla {string}")
    public void lukijaOnTehnytHaunParametreilla(String pars) {
        inputLines.add("hae " + pars);
    }

    @Niin("listalla ei ole yhtään vinkkiä")
    public void listallaEiOleYhtäänVinkkiä() {
        inputLines.add("listaa");
        List<String> output = aja();
        assertTrue(output.get(output.size() - 3).contains("Tunniste | Otsikko | "));
    }

    @Niin("ohjelma sulkeutuu")
    public void applicationIsClosed() {
        io = new StubIO(inputLines);
        app = new CommandInterpreter(storage, io, Optional.empty());
        app.mainLoop();
    }

    @Niin("tulosteessa esiintyy vinkki otsikolla {string} ja kuvauksella {string}")
    public void tulosteessaEsiintyyVinkkiOtsikollaJaKuvauksella(String otsikko, String kuvaus) {
        esiintyyköTulosteessaVinkkiOtsikollaJaKuvauksella(otsikko, kuvaus, true);

    }

    @Niin("tulosteessa ei esiinny vinkkiä otsikolla {string} ja kuvauksella {string}")
    public void tulosteessaEiEsiinnyVinkkiäOtsikollaJaKuvauksella(String otsikko, String kuvaus) {
        esiintyyköTulosteessaVinkkiOtsikollaJaKuvauksella(otsikko, kuvaus, false);
    }

    @Niin("listalla on artikkeli otsikolla {string} ja kuvauksella {string}")
    public void listallaOnArtikkeliOtsikollaJaKuvauksella(String otsikko, String kuvaus) {
        inputLines.add("listaa");
        List<String> output = aja();

        assertTrue(output.stream().anyMatch(
            s -> s.contains(otsikko) && s.contains(kuvaus) && s.contains(TipType.ARTICLE.getFinnishTranslation())
        ));
    }

    @Niin("listalla on kirja otsikolla {string} ja kuvauksella {string}")
    public void listallaOnKirjaOtsikollaJaKuvauksella(String otsikko, String kuvaus) {
        inputLines.add("listaa");
        List<String> output = aja();

        assertTrue(output.stream().anyMatch(
            s -> s.contains(otsikko) && s.contains(kuvaus) && s.contains(TipType.BOOK.getFinnishTranslation())
        ));
    }

    @Niin("tulostetaan vain vinkki jonka on kirjoittanut {string}")
    public void tulostetaanVainVinkkiJonkaOnKirjoittanut(String kirjoittajat) {
        List<String> output = aja();

        output.get(output.size()-3).contains("| Tunniste | Otsikko | Tyyppi |");
        output.get(output.size()-1).contains(kirjoittajat);
    }

    @Niin("tulostetaan vain vinkki jonka otsikko on {string}")
    public void tulostetaanVainVinkkiJonkaOtsikkoOn(String otsikko) {
        List<String> output = aja();

        output.get(output.size()-3).contains("| Tunniste | Otsikko | Tyyppi |");
        output.get(output.size()-1).contains(otsikko);
    }

    // apumetodit
    
    public List<String> aja() {
        inputLines.add("lopeta");

        io = new StubIO(inputLines);
        app = new CommandInterpreter(storage, io, Optional.empty());
        app.mainLoop();
        return io.getOutputs();
    } 

    public void esiintyyköTulosteessaVinkkiOtsikollaJaKuvauksella(String otsikko, String kuvaus, boolean esiintyy) {
        inputLines.add("listaa");
        List<String> output = aja();

        if (esiintyy) {
            assertTrue(output.stream().anyMatch(s -> s.contains(otsikko) && s.contains(kuvaus)));
        } else {
            assertFalse(output.stream().anyMatch(s -> s.contains(otsikko) && s.contains(kuvaus)));
        }
    }
}
