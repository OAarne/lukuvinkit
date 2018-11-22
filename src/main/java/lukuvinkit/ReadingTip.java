package lukuvinkit;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.json.JSONObject;

public class ReadingTip {

    private Map<ReadingTipField, Object> fields;

    public ReadingTip() {
        this.fields = new HashMap<>();
    }

    public String getFieldValueString(ReadingTipField field) {
        return Optional.ofNullable(fields.get(field)).map(field.getType()::fieldToString).orElse("");
    }

    public void setFieldValueString(ReadingTipField field, String value) {
        fields.put(field, field.getType().stringToField(value));
    }

    public Optional<Object> getFieldValue(ReadingTipField field) {
        return Optional.ofNullable(fields.get(field));
    }

    public void setFieldValue(ReadingTipField field, Object value) {
        assert field.getType().validateObject(value);
        fields.put(field, value);
    }

    public JSONObject toJSONObject() {
        JSONObject obj = new JSONObject();
        for (Map.Entry<ReadingTipField, Object> entry : fields.entrySet()) {
            ReadingTipField key = entry.getKey();
            Object value = entry.getValue();
            obj.put(key.getName(), key.getType().fieldToString(value));
        }
        return obj;
    }

    public static ReadingTip fromJSONObject(JSONObject obj) {
        ReadingTip tip = new ReadingTip();
        for (ReadingTipField field : ReadingTipField.values()) {
            if (obj.has(field.getName())) {
                tip.setFieldValue(field, field.getType().stringToField(obj.getString(field.getName())));
            }
        }
        return tip;
    }
}
