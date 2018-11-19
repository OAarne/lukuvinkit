package lukuvinkit;

public enum ReadingTipField {
    TITLE("Otsikko"),
    DESCRIPTION("Kuvaus"),
    ISBN("ISBN");

    private String name;

    private ReadingTipField(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}