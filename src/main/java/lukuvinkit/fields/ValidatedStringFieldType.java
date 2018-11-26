package lukuvinkit.fields;

public class ValidatedStringFieldType implements FieldType<String> {
    private final String regex;

    public ValidatedStringFieldType(String regex) {
        this.regex = regex;
    }

    @Override
    public boolean validateString(String strRep) {
        return strRep.matches(regex);
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