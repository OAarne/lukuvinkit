package lukuvinkit;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONObject;
import org.json.JSONStringer;

public class Storage {

    private LinkedHashMap<Integer, ReadingTip> readingTips;
    int nextReadingTipId;

    public Storage() {
        this.readingTips = new LinkedHashMap<>();
        this.nextReadingTipId = 0;
    }

    public int addReadingTip(ReadingTip readingTip) {
        //The first reading tip added will have an id of 0. This ticks up by one
        //for each new tip added so that each id is unique.
        readingTips.put(nextReadingTipId, readingTip);
        return nextReadingTipId++;
    }

    public void removeReadingTipById(int id) {
        readingTips.remove(id);
    }

    public Optional<ReadingTip> getReadingTipById(int id) {
        return Optional.ofNullable(readingTips.get(id));
    }

    public List<Map.Entry<Integer, ReadingTip>> getReadingTips() {
        return new ArrayList<>(readingTips.entrySet());
    }

    public String toJSON() {
        JSONStringer stringer = new JSONStringer();
        stringer.object();
        for (Map.Entry<Integer, ReadingTip> entry : readingTips.entrySet()) {
            stringer.key(entry.getKey().toString());
            stringer.value(entry.getValue().toJSONObject());
        }
        stringer.endObject();
        return stringer.toString();
    }

    public static Storage fromJSON(String json) {
        Storage storage = new Storage();
        JSONObject obj = new JSONObject(json);
        int maxId = 0;
        for (String key : obj.keySet()) {
            Integer id = Integer.parseInt(key);
            if (id > maxId) {
                maxId = id;
            }
            storage.readingTips.put(id, ReadingTip.fromJSONObject(obj.getJSONObject(key)));
        }
        storage.nextReadingTipId = maxId + 1;
        return storage;
    }
}
