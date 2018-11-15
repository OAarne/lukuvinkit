package lukuvinkit;

import java.util.HashMap;

public class Storage {

    private HashMap<Integer, ReadingTip> readingTips;
    int lastReadingTipId;

    public Storage() {
        this.readingTips = new HashMap<>();
        this.lastReadingTipId = -1;
    }

    public void addEntry(ReadingTip readingTip) {
        //The first reading tip added will have an id of 0. This ticks up by one
        //for each new tip added so that each id is unique.
        readingTips.put(lastReadingTipId + 1, readingTip);
        lastReadingTipId++;
    }

    public void removeReadingTipById(int id) {
        readingTips.remove(id);
    }

    public ReadingTip getReadingTipById(int id) {
        return readingTips.get(id);
    }
}
