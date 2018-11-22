package lukuvinkit;

import java.util.Arrays;
import java.util.List;

public enum ReadingTipField {
    TITLE("Otsikko", Arrays.asList(TipType.values())),
    DESCRIPTION("Kuvaus", Arrays.asList(TipType.values())),
    ISBN("ISBN", Arrays.asList(TipType.BOOK));

    private String name;
    private List<TipType> associatedTypes;

    private ReadingTipField(String name, List<TipType> associatedTypes) {
        this.name = name;
        this.associatedTypes = associatedTypes;
    }

    public String getName() {
        return this.name;
    }
}