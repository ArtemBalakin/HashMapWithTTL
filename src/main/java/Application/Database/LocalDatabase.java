package Application.Database;

import Application.Entityes.Element;
import Application.HashMap.HashMapWithTTL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * @author Balakin Artem( 17.09.2021)
 * Данный класс- реализация "локальной базы данных"
 * hashMapWithTTL-коллекция из которой удаляются значения по времени
 * pathsToUsersFiles- хранит пути до пользовательских файлов
 * */
@Component
public class LocalDatabase {
    private final HashMapWithTTL hashMapWithTTL;
    private final HashMap<String, String> pathsToUsersFiles = new HashMap<>();

    @Autowired
    public LocalDatabase(HashMapWithTTL hashMapWithTTL) {
        this.hashMapWithTTL = hashMapWithTTL;
        Runnable task = () -> {
            while (true) {
                hashMapWithTTL.deleteElementsByTTL();
                Thread.yield();
            }
        };
        ExecutorService pool = Executors.newFixedThreadPool(1);
        pool.execute(task);

    }

    public HashMap<String, String> getPathsToUsersFiles() {
        return pathsToUsersFiles;
    }

    /**
     * Добавляет данные из коллекции уже в существующий файл
     */
    public HashMapWithTTL getHashMapWithTTL() {
        return hashMapWithTTL;
    }

    /**
     * @param key- ключ
     * @return значение по ключу
     */
    public Element getObj(String key) {
        Element o = hashMapWithTTL.getObj(key);
        if (o != null)
            return o;
        return null;
    }

    /**
     * Записывает объект в коллекцию
     *
     * @param key    - ключ
     * @param value- значение
     * @param ttl-   время жизни
     */
    public void putObj(String key, String value, long ttl) {
        hashMapWithTTL.putElementWithTTL(key, value, ttl);
    }

    /**
     * Записывает объект в коллекцию с дефолтным ttl=600мс
     *
     * @param key    - ключ
     * @param value- значение
     */
    public void putObj(String key, Object value) {
        hashMapWithTTL.putElementWithTTL(key, value);
    }

    /**
     * Записывает Element в коллекцию
     *
     * @param key      - ключ
     * @param element- значение
     */
    public void putObj(String key, Element element) {
        hashMapWithTTL.putElementWithTTL(key, element);
    }

    public void addPath(String key, String path) {
        pathsToUsersFiles.put(key, path);
    }

    /**
     * Удаляет элемент по ключу
     * @param key-ключ элемента
     * @return Возвращает либо удаленный объект, либо сообщение о том что объекта с таким ключом нет
     */
    public Object deleteElement(String key) {
        Object o = hashMapWithTTL.deleteElement(key);
        if (o != null) return o;
        return "Объекта по такому ключу нет";
    }
}

