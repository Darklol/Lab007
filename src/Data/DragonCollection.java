package Data;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DragonCollection {
    private ConcurrentHashMap<Long, Dragon> dragons;
    private final Date creationDate;

    public DragonCollection(){
        creationDate = new Date();
        dragons = new ConcurrentHashMap<>();
    }

    public ConcurrentHashMap<Long, Dragon> getCollection() {
        return dragons;
    }

    public void setCollection(ConcurrentHashMap<Long, Dragon> collection) {
        this.dragons = collection;
    }

    public Date getCreationDate() {
        return creationDate;
    }
}
