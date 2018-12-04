package unitstepdefs;

import cucumber.api.java.fi.Kun;
import cucumber.api.java.fi.Niin;
import cucumber.api.java.fi.Oletetaan;
import java.util.Optional;
import lukuvinkit.ReadingTip;
import static lukuvinkit.ReadingTipField.*;
import lukuvinkit.Storage;
import static org.junit.Assert.*;

public class StorageUnitTestStepdefs {

    private Storage storage;
    private Storage jsonstorage;
    private int previousId;

    @Oletetaan("että varasto on vasta luotu")
    public void että_varasto_on_tyhjä() {
        storage = new Storage();
    }

    @Kun("varastoon on lisätty vinkki otsikolla {string}")
    public void varastoon_on_lisätty_vinkki_otsikolla(String title) {
        ReadingTip tip = new ReadingTip();
        tip.setFieldValueString(TITLE, title);
        previousId = storage.addReadingTip(tip);
    }

    @Kun("^varastosta on poistettu vinkki varaston palauttamalla tunnisteella$")
    public void varastostaOnPoistettuVinkkiVarastonPalauttamallaTunnisteella() throws Throwable {
        storage.removeReadingTipById(previousId);
    }

    @Kun("^luodaan varasto jsonmuotoisesta vinkistä \"Kissa\"$")
    public void luodaanVarastoJsonMuotoisestaVinkistä() throws Throwable {
        jsonstorage = Storage.fromJSON("{\"0\":{\"Otsikko\":\"Kissa\"}}");
    }

    @Kun("^luodaan varasto jsonmuotoisesta vinkistä \"Kissa\" indeksillä 1$")
    public void luodaanVarastoJsonMuotoisestaVinkistä2() throws Throwable {
        jsonstorage = Storage.fromJSON("{\"1\":{\"Otsikko\":\"Kissa\"}}");
    }

    @Niin("varaston palauttamalla tunnisteella haetun vinkin otsikko on {string}")
    public void varaston_palauttamalla_tunnisteella_haetun_vinkin_otsikko_on(String title) {
        Optional<ReadingTip> tip = storage.getReadingTipById(previousId);
        assertTrue(tip.isPresent());
        assertEquals(tip.get().getFieldValueString(TITLE), title);
    }

    @Niin("^varaston palauttamalla tunnisteella ei löydy vinkkiä$")
    public void varastonPalauttamallaTunnisteellaEiLöydyVinkkiä() throws Throwable {
        Optional<ReadingTip> tip = storage.getReadingTipById(previousId);
        assertFalse(tip.isPresent());
    }

    @Niin("^varaston palauttamalla listalla on kaksi vinkkiä$")
    public void varastonPalauttamallaListallaOnKaksiVinkkiä() throws Throwable {
        assertEquals(2, storage.getReadingTips().size());
    }

    @Niin("varastosta palautetaan komennolla jsoniksi json-muotoinen string")
    public void komentoJsoniksiPalauttaaJsonMuotoisenVinkin() throws Throwable {
        assertEquals("{\"0\":{\"Otsikko\":\"Kissa\"}}", storage.toJSON());
    }

    @Niin("^varastojen sisällöt ovat samat$")
    public void varastojenSisällötOvatSamat() throws Throwable {
        Optional<ReadingTip> tip1 = storage.getReadingTipById(0);
        Optional<ReadingTip> tip2 = jsonstorage.getReadingTipById(0);

        assertEquals(tip1, tip2);
    }

    @Niin("^varastossa on \"Kissa\" indeksillä 1$")
    public void varastossaOnKissaIndeksillä1() throws Throwable {
        assertEquals("{\"1\":{\"Otsikko\":\"Kissa\"}}", storage.toJSON());
    }

}
