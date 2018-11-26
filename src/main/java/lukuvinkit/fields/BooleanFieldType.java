package lukuvinkit.fields;

public class BooleanFieldType implements FieldType<Boolean> {
    public static final FieldType<Boolean> BOOLEAN_TYPE = new BooleanFieldType();

    private BooleanFieldType() {}

    @Override
    public boolean validateString(String strRep) {
        return strRep.matches("True|False");
    }

    @Override
    public Boolean stringToField(String strRep) {
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
    public String fieldToString(Boolean obj) {
        return ((Boolean) obj) ? "True" : "False";
    }
}