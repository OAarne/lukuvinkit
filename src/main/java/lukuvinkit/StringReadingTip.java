package lukuvinkit;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class StringReadingTip implements ReadingTipInterface {

    private Map<ReadingTipField<?>, String> fields;

    public StringReadingTip() {
        this.fields = new HashMap<>();
    }

    @Override
    public Set<ReadingTipField<?>> getPresentFields() {
        return fields.keySet();
    }

    @Override
    public<T> String getFieldValueString(ReadingTipField<T> field) {
        return fields.getOrDefault(field, field.getType().fieldToString(field.getDefaultValue()));
    }

    @Override
    public void setFieldValueString(ReadingTipField<?> field, String value) {
        fields.put(field, value);
    }

    @Override
    public boolean validateString(ReadingTipField<?> field, String value) {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StringReadingTip)) return false;
        StringReadingTip that = (StringReadingTip) o;
        return Objects.equals(fields, that.fields);
    }

    @Override
    public int hashCode() {
        return fields.hashCode();
    }
}
