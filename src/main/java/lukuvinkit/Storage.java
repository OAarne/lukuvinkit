package lukuvinkit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    }
        
    public void removeReadingTipById(int id) {
        readingTips.remove(id);
    }
    
    public ReadingTip getEntryById(int id) {
        return readingTips.get(id);
    }
}
