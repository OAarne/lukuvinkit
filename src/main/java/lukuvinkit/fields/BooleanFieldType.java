package lukuvinkit.fields;

public class BooleanFieldType implements FieldType<Boolean> {
    public static final FieldType<Boolean> BOOLEAN_TYPE = new BooleanFieldType("Tosi", "Epätosi");

    private String trueString, falseString;

    public BooleanFieldType(String trueString, String falseString) {
        this.trueString = trueString;
        this.falseString = falseString;
    }

    @Override
    public boolean validateString(String strRep) {
        return strRep.equals(trueString) || strRep.equals(falseString);
    }

    @Override
    public Boolean stringToField(String strRep) {
        if (strRep.equals(trueString)) {
            return Boolean.TRUE;
        } else if (strRep.equals(falseString)) {
            return Boolean.FALSE;
        } else {
            assert false;
            throw new IllegalArgumentException();
        }
    }

    @Override
    public String fieldToString(Boolean obj) {
        return obj ? trueString : falseString;
    }
}