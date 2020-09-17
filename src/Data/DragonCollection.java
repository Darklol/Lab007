package Data;

import java.util.*;

public class DragonCollection {
    private HashMap<Long, Dragon> dragons;
    private final Date creationDate;

    public DragonCollection(){
        creationDate = new Date();
        dragons = new HashMap<Long, Dragon>();
    }

    public HashMap<Long, Dragon> getCollection() {
        return dragons;
    }

    public void setCollection(HashMap<Long, Dragon> collection) {
        this.dragons = collection;
    }

    public Date getCreationDate() {
        return creationDate;
    }
}
