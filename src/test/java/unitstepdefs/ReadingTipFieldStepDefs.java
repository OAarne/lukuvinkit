package unitstepdefs;

import cucumber.api.PendingException;
import cucumber.api.java.fi.Niin;
import lukuvinkit.ReadingTipField;

import static org.junit.Assert.assertEquals;

public class ReadingTipFieldStepDefs {
    @Niin("{string} on validi ISBN")
    public void onValidiIsbn(String isbn){
        assertEquals(true, ReadingTipField.validateIsbnImplementation(isbn));
    }

    @Niin("{string} ei ole validi ISBN")
    public void eiOleValidiIsbn(String isbn){
        assertEquals(false, ReadingTipField.validateIsbnImplementation(isbn));
    }

}
