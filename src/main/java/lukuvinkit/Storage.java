package lukuvinkit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Storage {
    
    private HashMap<Integer, Entry> readingTips;
    int lastReadingTipId;
    
    public Storage() {
        this.readingTips = new HashMap<>();
        this.lastReadingTipId = -1;
    }
    
    public void addEntry(ReadingTip readingTip) {
        readingTips.put(lastReadingTipId + 1, readingTip);
    }
    
    public void removeEntryById(int id) {
        readingTips.remove(id);
    }
    
    public Entry getEntryById(int id) {
        return readingTips.get(id);
    }
}
