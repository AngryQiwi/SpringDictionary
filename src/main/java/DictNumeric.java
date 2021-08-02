import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DictNumeric implements Dictionary {
    private final String FILE_NAME = "src/main/resources/numdict.txt";
    private final String REGULAR = "\\d{5}";
    private ArrayList<DictEntry> entries = new ArrayList<>();
    @Override
    public void readAllFromFile() throws IOException {
        entries = new ArrayList<>();
        FileReader reader = new FileReader(FILE_NAME);
        StringBuilder entryStr = new StringBuilder();
        int c;
        while ((c = reader.read()) != -1) {
            if (c == ';') {
                String[] keyValue = entryStr.toString().split("-");
                entries.add(new DictEntry(keyValue[0], keyValue[1]));
                entryStr.delete(0, entryStr.length());
            } else entryStr.append((char) c);
        }
        reader.close();
        entries.sort(new DictComparator());
    }

    public ArrayList<DictEntry> getEntries() {
        return entries;
    }

    @Override
    public ArrayList<DictEntry> readByKey(String key) {
        ArrayList<DictEntry> foundEntries = new ArrayList<>();
        for (DictEntry entry : entries) {
            if (entry.getKey().equals(key)) foundEntries.add(entry);
        }
        if(foundEntries.isEmpty()){
            System.out.println("Значений не найдено");
            return null;
        }
        return foundEntries;
    }

    @Override
    public void deleteEntry(int numberOfDeleted, ArrayList<DictEntry> candidatesForDelete) {
        DictEntry updatedValue = candidatesForDelete.get(numberOfDeleted-1);
        entries.remove(updatedValue);
        System.out.println("Значение удалено");
    }

    @Override
    public void add(DictEntry entry) {
        Pattern pattern = Pattern.compile(REGULAR);
        Matcher matcher = pattern.matcher(entry.getKey());
        if (matcher.matches()) {
            for(DictEntry entry1: entries){
                if (entry.getKey().equals(entry1.getKey())&&entry.getValue().equals(entry1.getValue())) {
                    System.out.println("Ввод не выполнен, значение существует");
                    return;
                }
            }
            entries.add(entry);
            System.out.println("Ввод выполнен.");
            entries.sort(new DictComparator());
            return;
        }
        System.out.println("Ввод не выполнен, некорректное слово");
    }

    @Override
    public void refreshFile() throws IOException {
        new FileWriter(FILE_NAME).close();
        FileWriter writer = new FileWriter(FILE_NAME, true);
        for (DictEntry entry : entries) {
            writer.append(entry.toString());
        }
        writer.close();
    }

    @Override
    public void update(String newValue, ArrayList<DictEntry> candidatesForUpdate, int numberOfUpdated) {
        DictEntry updatedValue = candidatesForUpdate.get(numberOfUpdated-1);
        entries.remove(updatedValue);
        System.out.println("Старое значение удалено");
        updatedValue.setValue(newValue);
        entries.add(updatedValue);
        System.out.println("Нвове значение введено");
    }

}
