package lukuvinkit;

import java.util.Set;

public interface ReadingTipInterface {
    public Set<ReadingTipField<?>> getPresentFields();
    
    public<T> String getFieldValueString(ReadingTipField<T> field);

    public void setFieldValueString(ReadingTipField<?> field, String value);

    public default boolean validateString(ReadingTipField<?> field, String value) {
        return field.getType().validateString(value);
    }
}