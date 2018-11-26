package lukuvinkit;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.json.JSONObject;

public class ReadingTip {

    private Map<ReadingTipField<? extends Object>, Object> fields;

    public ReadingTip() {
        this.fields = new HashMap<>();
    }

    @SuppressWarnings("all")
    public String getFieldValueString(ReadingTipField field) {
        return Optional.ofNullable(fields.get(field)).map(field.getType()::fieldToString).orElse("");
    }

    public void setFieldValueString(ReadingTipField<?> field, String value) {
        fields.put(field, field.getType().stringToField(value));
    }

    @SuppressWarnings("unchecked")
    public<T> Optional<T> getFieldValue(ReadingTipField<T> field) {
        return Optional.ofNullable((T) fields.get(field));
    }

    public<T> void setFieldValue(ReadingTipField<T> field, T value) {
        fields.put(field, value);
    }

    @SuppressWarnings("all")
    public JSONObject toJSONObject() {
        JSONObject obj = new JSONObject();
        for (Map.Entry<ReadingTipField<? extends Object>, Object> entry : fields.entrySet()) {
            ReadingTipField key = entry.getKey();
            Object value = entry.getValue();
            obj.put(key.getName(), key.getType().fieldToString(value));
        }
        return obj;
    }

    @SuppressWarnings("all")
    public static ReadingTip fromJSONObject(JSONObject obj) {
        ReadingTip tip = new ReadingTip();
        for (ReadingTipField field : ReadingTipField.VALUES) {
            if (obj.has(field.getName())) {
                tip.setFieldValue(field, field.getType().stringToField(obj.getString(field.getName())));
            }
        }
        return tip;
    }
}
