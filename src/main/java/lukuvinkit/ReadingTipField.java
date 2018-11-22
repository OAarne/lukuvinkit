package lukuvinkit;

import lukuvinkit.fields.FieldType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static lukuvinkit.TipType.BOOK;
import static lukuvinkit.fields.BooleanFieldType.BOOLEAN_TYPE;
import static lukuvinkit.fields.StringFieldType.STRING_TYPE;

public enum ReadingTipField {
    TITLE("Otsikko", STRING_TYPE, Arrays.asList(TipType.values())),
    TYPE("Tyyppi", STRING_TYPE, Collections.emptyList()),
    DESCRIPTION("Kuvaus", STRING_TYPE, Arrays.asList(TipType.values())),
    ISBN("ISBN", STRING_TYPE, Arrays.asList(BOOK)),
    IS_READ("Luettu", BOOLEAN_TYPE, Collections.emptyList());

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