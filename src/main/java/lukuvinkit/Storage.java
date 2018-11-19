package lukuvinkit;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Storage {

    private LinkedHashMap<Integer, ReadingTip> readingTips;
    int lastReadingTipId;

    public Storage() {
        this.readingTips = new LinkedHashMap<>();
        this.lastReadingTipId = 0;
    }

    public int addReadingTip(ReadingTip readingTip) {
        //The first reading tip added will have an id of 0. This ticks up by one
        //for each new tip added so that each id is unique.
        readingTips.put(lastReadingTipId, readingTip);
        return lastReadingTipId++;
    }

    public void removeReadingTipById(int id) {
        readingTips.remove(id);
    }

    public ReadingTip getReadingTipById(int id) {
        return readingTips.get(id);
    }

    public List<Map.Entry<Integer, ReadingTip>> getReadingTips() {
        return new ArrayList<>(readingTips.entrySet());
    }
}
