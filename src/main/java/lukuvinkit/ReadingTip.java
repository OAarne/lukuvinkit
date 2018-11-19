package lukuvinkit;

import java.util.HashMap;
import java.util.Map;

public class ReadingTip {

    private Map<ReadingTipField, String> fields;

    public ReadingTip() {
        this.fields = new HashMap<>();
    }

    public String getFieldValue(ReadingTipField field) {
        return fields.getOrDefault(field, "");
    }

    public void setFieldValue(ReadingTipField field, String value) {
        fields.put(field, value);
    }
}
