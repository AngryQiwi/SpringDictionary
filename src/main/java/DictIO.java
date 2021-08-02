import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class DictIO {
    private Dictionary dictionary;
    private final Scanner scanner = new Scanner(System.in);
    private final ApplicationContext context = new AnnotationConfigApplicationContext(DictionaryConfig.class);

    void greetings() throws IOException {
        while (true) {
            System.out.println("Добро пожаловать в интергалактический словарь.");
            System.out.println("В данный момент доступно 2 языка, для выбора пункта введите его номер:");
            System.out.println("1. Интергаланглийский");
            System.out.println("2. Числоматанский");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1": {
                    dictionary = (Dictionary) context.getBean("dictEnglish");
                    dictionary.readAllFromFile();
                    chooseAction();
                    continue;
                }
                case "2": {
                    dictionary = (Dictionary) context.getBean("dictNumeric");
                    dictionary.readAllFromFile();
                    chooseAction();
                    continue;
                }
                default: {
                    System.out.println("Такой словарь не существует");
                }
            }
        }
    }

    void chooseAction() throws IOException {
        while (true) {
            System.out.println("Выберите действие:");
            System.out.println("1. Просмотр данных;");
            System.out.println("2. Найти значения по ключу;");
            System.out.println("3. Добавить значение;");
            System.out.println("4. Изменить значение;");
            System.out.println("5. Удалить значение.");
            System.out.println("6. Сменить словарь.");
            System.out.println("7. Выход.");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1": {
                    show();
                    continue;
                }
                case "2": {
                    search();
                    continue;
                }
                case "3": {
                    add();
                    continue;
                }
                case "4": {
                    update();
                    continue;
                }
                case "5": {
                    delete();
                    continue;
                }
                case "6": {
                    if (changeDict()) return;
                    continue;
                }
                case "7": {
                    exit();
                    continue;
                }
                default: {
                    System.out.println("Введено неверное значение, введите значение в соответствии с выбранным пунктом");
                }
            }
        }
    }

    void show() {
        System.out.println("===================================");
        System.out.println("===================================");
        for (DictEntry entry : dictionary.getEntries()) {
            System.out.println(entry.toString());
        }
        System.out.println("===================================");
        System.out.println("===================================");
    }

    void search() {
        System.out.println("Введите слово:");
        String key = scanner.nextLine();
        ArrayList<DictEntry> foundEntries = dictionary.readByKey(key);
        if (foundEntries == null) return;
        System.out.println("===================================");
        System.out.println("===================================");
        for (DictEntry entry : foundEntries) {
            System.out.println(entry.toString());
        }
        System.out.println("===================================");
        System.out.println("===================================");
    }

    void add() {
        DictEntry entry = new DictEntry();
        System.out.println("Введите слово:");
        entry.setKey(scanner.nextLine());
        System.out.println("Введите значение:");
        entry.setValue(scanner.nextLine());
        dictionary.add(entry);
    }

    void update() {
        System.out.println("Введите слово:");
        String key = scanner.nextLine();
        ArrayList<DictEntry> candidatesForUpdate = dictionary.readByKey(key);
        if (candidatesForUpdate == null) return;
        System.out.println("Выберите значение, которое хотите изменить:");
        for (DictEntry candidate : candidatesForUpdate) {
            System.out.print((candidatesForUpdate.indexOf(candidate) + 1) + ". ");
            System.out.println(candidate.toString());
        }
        int numberOfUpdated;
        numberOfUpdated = scanner.nextInt();
        scanner.nextLine();
        while (numberOfUpdated < 1 || numberOfUpdated > candidatesForUpdate.size()) {
            System.out.println("Выбран неверный номер элемента, введите корректный номер:");
            numberOfUpdated = scanner.nextInt();
            scanner.nextLine();
        }
        System.out.println("Введите новое значение:");
        String newValue = scanner.nextLine();
        dictionary.update(newValue, candidatesForUpdate, numberOfUpdated);
    }

    void delete() {
        System.out.println("Введите слово:");
        String key = scanner.nextLine();
        ArrayList<DictEntry> candidatesForDelete = dictionary.readByKey(key);
        if (candidatesForDelete == null) return;
        System.out.println("Выберите значение, которое хотите удалить:");
        for (DictEntry candidate : candidatesForDelete) {
            System.out.print((candidatesForDelete.indexOf(candidate) + 1) + ". ");
            System.out.println(candidate.toString());
        }
        int numberOfDeleted = scanner.nextInt();
        scanner.nextLine();
        while (numberOfDeleted < 1 || numberOfDeleted > candidatesForDelete.size()) {
            System.out.println("Выбран неверный номер элемента, введите корректный номер:");
            numberOfDeleted = scanner.nextInt();
            scanner.nextLine();
        }
        dictionary.deleteEntry(numberOfDeleted, candidatesForDelete);
    }

    void exit() throws IOException {
        System.out.println("Сохранить изменения?");
        System.out.println("1. Да");
        System.out.println("2. Нет");
        System.out.println("3. Отмена");
        String choice = scanner.nextLine();
        while (true) {
            switch (choice) {
                case "1": {
                    dictionary.refreshFile();
                    System.exit(0);
                }
                case "2": {
                    System.exit(0);
                }
                case "3": {
                    return;
                }
                default: {
                    System.out.println("Введено неверное значение, введите значение в соответствии с выбранным пунктом");
                }
            }
        }
    }

    boolean changeDict() throws IOException {
        System.out.println("Сохранить изменения?");
        System.out.println("1. Да");
        System.out.println("2. Нет");
        System.out.println("3. Отмена");
        String choice = scanner.nextLine();
        while (true) {
            switch (choice) {
                case "1": {
                    dictionary.refreshFile();
                    return true;
                }
                case "2": {
                    return true;
                }
                case "3": {
                    return false;
                }
                default: {
                    System.out.println("Введено неверное значение, введите значение в соответствии с выбранным пунктом");
                }
            }
        }
    }
}
