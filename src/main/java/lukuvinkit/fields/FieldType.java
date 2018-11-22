package lukuvinkit.fields;

public interface FieldType {
    public boolean validateString(String strRep);
    public Object stringToField(String strRep);
    public boolean validateObject(Object obj);
    public String fieldToString(Object obj);
}