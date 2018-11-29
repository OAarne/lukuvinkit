package lukuvinkit;

public enum TipType implements Translated {
    BOOK("kirja"),
    ARTICLE("artikkeli"),
    OTHER("muu");

    private String name;

    private TipType(String finnishName) {
        this.name = finnishName;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getFinnishTranslation() {
        return getName();
    }
}