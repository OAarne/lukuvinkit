package lukuvinkit.fields;

import java.util.function.Predicate;

public class ValidatedStringFieldType implements FieldType<String> {
    private final Predicate<String> predicate;

    public ValidatedStringFieldType(Predicate<String> predicate) {
        this.predicate = predicate;
    }

    @Override
    public boolean validateString(String strRep) {
        return predicate.test(strRep);
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