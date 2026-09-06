import java.util.List;
import java.util.ArrayList;

public class EntityList<T> {
    
    private List<T> items;

    public EntityList() {
        items = new ArrayList<>();
    }

    public void add(T item) {
        items.add(item);
    }

    public void remove(T item) {
        items.remove(item);
    }

    public List<T> getItems() {
        return items;
    }

    public int size() {
        return items.size();
    }
}
