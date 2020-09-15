package Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

public class DragonCollection {
    private Hashtable<Long, Dragon> dragons;
    private final Date creationDate;

    public DragonCollection(){
        creationDate = new Date();
        dragons = new Hashtable<Long, Dragon>();
    }

    public Hashtable<Long, Dragon> getCollection() {
        return dragons;
    }

    public void setCollection(Hashtable<Long, Dragon> collection) {
        this.dragons = collection;
    }

    public Date getCreationDate() {
        return creationDate;
    }
}
