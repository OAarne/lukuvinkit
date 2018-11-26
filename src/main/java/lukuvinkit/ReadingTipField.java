package lukuvinkit;

import lukuvinkit.fields.FieldType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static lukuvinkit.TipType.BOOK;
import static lukuvinkit.TipType.OTHER;
import static lukuvinkit.fields.BooleanFieldType.BOOLEAN_TYPE;
import static lukuvinkit.fields.StringFieldType.STRING_TYPE;

public class ReadingTipField<T> {
    public static final List<ReadingTipField<? extends Object>> VALUES = new ArrayList<>();

    public static final ReadingTipField<String> TITLE = new ReadingTipField<>("Otsikko", STRING_TYPE, Arrays.asList(TipType.values()));
    public static final ReadingTipField<String> TYPE = new ReadingTipField<>("Tyyppi", STRING_TYPE, Collections.emptyList());
    public static final ReadingTipField<String> DESCRIPTION = new ReadingTipField<>("Kuvaus", STRING_TYPE, Arrays.asList(TipType.values()));
    public static final ReadingTipField<String> ISBN = new ReadingTipField<>("ISBN", STRING_TYPE, Arrays.asList(BOOK, OTHER));
    public static final ReadingTipField<Boolean> IS_READ = new ReadingTipField<>("Luettu", BOOLEAN_TYPE, Collections.emptyList());

    private String name;
    private List<TipType> associatedTipTypes;
    private FieldType<T> type;

    private ReadingTipField(String name, FieldType<T> type, List<TipType> associatedTipTypes) {
        this.name = name;
        this.type = type;
        this.associatedTipTypes = associatedTipTypes;
        VALUES.add(this);
    }

    public String getName() {
        return this.name;
    }

    public FieldType<T> getType() {
        return this.type;
    }

    public List<TipType> getAssociatedTipTypes() {
        return associatedTipTypes;
    }
}