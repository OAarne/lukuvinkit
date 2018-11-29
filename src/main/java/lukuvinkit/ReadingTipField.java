package lukuvinkit;

import lukuvinkit.fields.EnumFieldType;
import lukuvinkit.fields.FieldType;
import lukuvinkit.fields.ValidatedStringFieldType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static lukuvinkit.TipType.BOOK;
import static lukuvinkit.TipType.OTHER;
import lukuvinkit.fields.BooleanFieldType;
import static lukuvinkit.fields.StringFieldType.STRING_TYPE;

public class ReadingTipField<T> implements Translated {
    public static final List<ReadingTipField<? extends Object>> VALUES = new ArrayList<>();
    public static final Map<String, ReadingTipField<? extends Object>> VALUE_MAP = new HashMap<>();

    public static final ReadingTipField<String> TITLE =
        new ReadingTipField<>("Otsikko", new ValidatedStringFieldType(".*\\S.*"), Arrays.asList(TipType.values()), "Nimetön vinkki");
    public static final ReadingTipField<TipType> TYPE =
        new ReadingTipField<>("Tyyppi", new EnumFieldType<>(TipType.values()), Collections.emptyList(), TipType.OTHER);
    public static final ReadingTipField<String> DESCRIPTION =
        new ReadingTipField<>("Kuvaus", STRING_TYPE, Arrays.asList(TipType.values()), "");
    public static final ReadingTipField<String> ISBN =
        new ReadingTipField<>("ISBN", STRING_TYPE, Arrays.asList(BOOK, OTHER), "");
    public static final ReadingTipField<Boolean> IS_READ
        = new ReadingTipField<>("Luettu", new BooleanFieldType("luettu", "lukematta"), Collections.emptyList(), false);

    private String name;
    private List<TipType> associatedTipTypes;
    private FieldType<T> type;
    private T defaultValue;

    private ReadingTipField(String name, FieldType<T> type, List<TipType> associatedTipTypes, T defaultValue) {
        this.name = name;
        this.type = type;
        this.associatedTipTypes = associatedTipTypes;
        this.defaultValue = defaultValue;
        VALUES.add(this);
        VALUE_MAP.put(name, this);
    }

    public String getName() {
        return name;
    }

    @Override
    public String getFinnishTranslation() {
        return getName();
    }

    public FieldType<T> getType() {
        return type;
    }

    public List<TipType> getAssociatedTipTypes() {
        return associatedTipTypes;
    }

    public T getDefaultValue() {
        return defaultValue;
    }
}