package lukuvinkit;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.json.JSONObject;

public class ReadingTip {

    private Map<ReadingTipField<? extends Object>, Object> fields;

    public ReadingTip() {
        this.fields = new HashMap<>();
    }

    public Set<ReadingTipField<?>> getPresentFields() {
        return fields.keySet();
    }

    public<T> String getFieldValueString(ReadingTipField<T> field) {
        return field.getType().fieldToString(getFieldValue(field));
    }

    public void setFieldValueString(ReadingTipField<?> field, String value) {
        fields.put(field, field.getType().stringToField(value));
    }

    @SuppressWarnings("unchecked")
    public<T> T getFieldValue(ReadingTipField<T> field) {
        return (T) fields.getOrDefault(field, field.getDefaultValue());
    }

    public<T> void setFieldValue(ReadingTipField<T> field, T value) {
        fields.put(field, value);
    }

    @SuppressWarnings("unchecked")
    public JSONObject toJSONObject() {
        JSONObject obj = new JSONObject();
        for (Map.Entry<ReadingTipField<? extends Object>, Object> entry : fields.entrySet()) {
            @SuppressWarnings("rawtypes")
            ReadingTipField key = entry.getKey();
            Object value = entry.getValue();
            obj.put(key.getName(), key.getType().fieldToString(value));
        }
        return obj;
    }

    @SuppressWarnings("unchecked")
    public static ReadingTip fromJSONObject(JSONObject obj) {
        ReadingTip tip = new ReadingTip();
        for (@SuppressWarnings("rawtypes") ReadingTipField field : ReadingTipField.VALUES) {
            if (obj.has(field.getName())) {
                tip.setFieldValue(field, field.getType().stringToField(obj.getString(field.getName())));
            }
        }
        return tip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReadingTip)) return false;
        ReadingTip that = (ReadingTip) o;
        return Objects.equals(fields, that.fields);
    }

    @Override
    public int hashCode() {
        return fields.hashCode();
    }
}
