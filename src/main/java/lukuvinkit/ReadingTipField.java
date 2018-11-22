package lukuvinkit;

import lukuvinkit.fields.FieldType;

import java.util.Arrays;
import java.util.List;

import static lukuvinkit.TipType.BOOK;
import static lukuvinkit.fields.StringFieldType.STRING_TYPE;

public enum ReadingTipField {
    TITLE("Otsikko", STRING_TYPE, Arrays.asList(TipType.values())),
    DESCRIPTION("Kuvaus", STRING_TYPE, Arrays.asList(TipType.values())),
    ISBN("ISBN", STRING_TYPE, Arrays.asList(BOOK));

    private String name;

    private List<TipType> associatedTipTypes;

    private FieldType type;

    private ReadingTipField(String name, FieldType type, List<TipType> associatedTipTypes) {
        this.name = name;
        this.type = type;
        this.associatedTipTypes = associatedTipTypes;
    }

    public String getName() {
        return this.name;
    }

    public FieldType getType() {
        return this.type;
    }

    public List<TipType> getAssociatedTipTypes() {
        return associatedTipTypes;
    }
}