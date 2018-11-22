package lukuvinkit.fields;

public class StringFieldType implements FieldType {
    public static final FieldType STRING_TYPE = new StringFieldType();

    private StringFieldType() {}

    @Override
    public boolean validateString(String strRep) {
        return true;
    }

    @Override
    public Object stringToField(String strRep) {
        return strRep;
    }

    @Override
    public boolean validateObject(Object obj) {
        return obj instanceof String;
    }

    @Override
    public String fieldToString(Object obj) {
        assert validateObject(obj);
        return (String) obj;
    }
}