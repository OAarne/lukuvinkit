package lukuvinkit.fields;

import java.util.Arrays;

import lukuvinkit.Translated;

public class EnumFieldType<T extends Translated> implements FieldType<T> {
    private T[] values;

    public EnumFieldType(T[] values) {
        this.values = values;
    }

    @Override
    public boolean validateString(String strRep) {
        return Arrays.asList(values).stream().map(Translated::getFinnishTranslation).anyMatch(strRep::equals);
    }

    @Override
    public T stringToField(String strRep) {
        return Arrays.asList(values).stream().filter(t -> t.getFinnishTranslation().equals(strRep)).findAny().get();
    }

    @Override
    public String fieldToString(T obj) {
        return obj.getFinnishTranslation();
    }
}