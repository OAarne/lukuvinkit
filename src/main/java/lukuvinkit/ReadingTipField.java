package lukuvinkit;

import lukuvinkit.fields.FieldType;

import static lukuvinkit.fields.StringFieldType.STRING_TYPE;

public enum ReadingTipField {
    TITLE("Otsikko", STRING_TYPE),
    DESCRIPTION("Kuvaus", STRING_TYPE),
    ISBN("ISBN", STRING_TYPE);

    private String name;
    private FieldType type;

    private ReadingTipField(String name, FieldType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public FieldType getType() {
        return this.type;
    }
}