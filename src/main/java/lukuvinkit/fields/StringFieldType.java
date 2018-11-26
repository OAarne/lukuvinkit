package lukuvinkit.fields;

public class StringFieldType implements FieldType<String> {
    public static final FieldType<String> STRING_TYPE = new StringFieldType();

    private StringFieldType() {}

    @Override
    public boolean validateString(String strRep) {
        return true;
    }

    @Override
    public String stringToField(String strRep) {
        return strRep;
    }

    @Override
    public String fieldToString(String obj) {
        return obj;
    }
}