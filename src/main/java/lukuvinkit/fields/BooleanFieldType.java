package lukuvinkit.fields;

public class BooleanFieldType implements FieldType {
    public static final FieldType BOOLEAN_TYPE = new BooleanFieldType();

    private BooleanFieldType() {}

    @Override
    public boolean validateString(String strRep) {
        return strRep.matches("True|False");
    }

    @Override
    public Object stringToField(String strRep) {
        switch (strRep) {
            case "True":
                return Boolean.TRUE;
            case "False":
                return Boolean.FALSE;
            default:
                assert false;
                return null;
        }
    }

    @Override
    public boolean validateObject(Object obj) {
        return obj instanceof Boolean;
    }

    @Override
    public String fieldToString(Object obj) {
        assert validateObject(obj);
        return ((Boolean) obj) ? "True" : "False";
    }
}