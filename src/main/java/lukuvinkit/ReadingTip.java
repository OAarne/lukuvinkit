package lukuvinkit;

import java.util.HashMap;
import java.util.Map;

public class ReadingTip {

    private Map<ReadingTipField, Object> fields;

    public ReadingTip() {
        this.fields = new HashMap<>();
    }

    public String getFieldValueString(ReadingTipField field) {
        return field.getType().fieldToString(fields.getOrDefault(field, ""));
    }

    public void setFieldValueString(ReadingTipField field, String value) {
        fields.put(field, field.getType().stringToField(value));
    }

    public Object getFieldValue(ReadingTipField field) {
        return fields.getOrDefault(field, "");
    }

    public void setFieldValue(ReadingTipField field, Object value) {
        assert field.getType().validateObject(value);
        fields.put(field, value);
    }
}
