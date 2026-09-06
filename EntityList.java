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

    public T get(int index) {
        return items.get(index);
    }

    public int size() {
        return items.size();
    }
}
