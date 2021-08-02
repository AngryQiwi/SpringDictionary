import java.io.IOException;
import java.util.ArrayList;

public interface Dictionary {
    void readAllFromFile() throws IOException;
    ArrayList<DictEntry> readByKey(String key);
    void deleteEntry(int numberOfDeleted, ArrayList<DictEntry> candidatesForDelete);
    void add(DictEntry entry);
    void refreshFile() throws IOException;
    void update(String newValue, ArrayList<DictEntry> candidatesForUpdate, int numberOfUpdated);
    ArrayList<DictEntry> getEntries();
}
