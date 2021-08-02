import java.util.Comparator;

public class DictComparator implements Comparator<DictEntry> {
    @Override
    public int compare(DictEntry o1, DictEntry o2) {
        String a1 = o1.getKey();
        String a2 = o2.getKey();
        return a1.compareTo(a2);
    }
}
