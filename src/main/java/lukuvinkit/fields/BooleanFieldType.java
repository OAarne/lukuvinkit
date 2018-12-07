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
        return trueString.contains(strRep) || falseString.contains(trueString);
    }

    @Override
    public Boolean stringToField(String strRep) {
        if (trueString.contains(strRep) && !strRep.equals(falseString)) {
            return Boolean.TRUE;
        } else if (falseString.contains(strRep)) {
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