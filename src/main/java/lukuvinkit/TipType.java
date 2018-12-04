package lukuvinkit;

import java.util.HashMap;
import java.util.Map;

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

    public static final Map<String, TipType> TIP_TYPES = new HashMap<>();
    static {
        for (TipType type : values()) {
            TIP_TYPES.put(type.getName(), type);
        }
    }
}