package lukuvinkit.fields;

public interface FieldType<T> {
    public boolean validateString(String strRep);

    public T stringToField(String strRep);
    
    public String fieldToString(T obj);
}