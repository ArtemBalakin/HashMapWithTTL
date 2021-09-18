package Application.Service;

import Application.Database.LocalDatabase;
import Application.Entityes.Element;
import Application.HashMap.HashMapWithTTL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Balakin Artem( 06.09.2021)
 * Сервис для работы с hashMapWithTTL
 * pool- пул для очищения коллекции
 * task -задание по запуску очищения коллекции
 * csvIO- для записи/чтения данных в CSV
 */
@Service
public class DatabaseService {
    private final LocalDatabase localDatabase;

    @Autowired
    public DatabaseService(LocalDatabase localDatabase) {
        this.localDatabase = localDatabase;
    }

    public Element getElement(String key) {
        return localDatabase.getObj(key);
    }

    /**
     * @return  вся  коллекция
     */
    public HashMapWithTTL getAllMap() {
        return localDatabase.getHashMapWithTTL();
    }

    public void putElement(String key, Object value) {
        localDatabase.putObj(key, value);
    }

    public void putElement(String key, Element element) {
        localDatabase.putObj(key, element);
    }

    public void putElement(String key, String value, long ttl) {
        localDatabase.putObj(key, value, ttl);
    }

    public Object deleteElement(String key) {
        return localDatabase.deleteElement(key);
    }

    /**
     *@return набор путей до файлов
     */
    public HashMap<String, String> getPathsToUsersFile() {
        return localDatabase.getPathsToUsersFiles();
    }

    /**
     * Заполняет мапу с путями к пользовательским файлам
     * @param list- откуда идет заполнение
     */
    public void readAllPaths(ArrayList<String[]> list) {
        list.forEach(strings -> {
            localDatabase.addPath(strings[0], strings[1]);
        });
    }
}
