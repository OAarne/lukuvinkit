import cucumber.api.java.fi.Kun;
import cucumber.api.java.fi.Niin;
import cucumber.api.java.fi.Oletetaan;
import static org.junit.Assert.*;

import java.util.Optional;

import lukuvinkit.Storage;
import lukuvinkit.ReadingTip;
import static lukuvinkit.ReadingTipField.*;

public class StorageUnitTestStepdefs {
    private Storage storage;
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
}