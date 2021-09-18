package Application.HashMap;

import Application.Entityes.Element;
import Application.Entityes.TTL;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Balakin Artem( 06.09.2021)
 * Реализация HashMap с временем жизни каждого элемента
 * hashMap(String,Element)- основа в которой хранятся объекты
 */
@Component
public class HashMapWithTTL {
    private final ConcurrentHashMap<String, Element> hashMap = new ConcurrentHashMap<>();

    /**
     * Метод для своевремменого удаления элементов
     */
    public void deleteElementsByTTL() {
        hashMap.forEach((key, value) -> {
            if (!value.getTtl().isTTLAlive()) {
                System.out.println("Delete");
                hashMap.remove(key);
            }
        });
    }

    public Element getObj(String key) {
        return hashMap.get(key);
    }

    public ConcurrentHashMap<String, Element> getHashMap() {
        return hashMap;
    }

    /**
     * Метод для добавления нового значения в коллекцию
     *
     * @param key-ключ     нового элемента
     * @param obj-значение которое будет хранится по ключу
     * @param ttl-         время жизни объекта
     */
    public void putElementWithTTL(String key, Object obj, long ttl) {
        TTL ttl2 = new TTL(ttl);
        Element element = new Element(obj, ttl2);
        hashMap.put(key, element);
    }

    /**
     * Добавление нового объекта в коллекцию, если время жизни не указано( применяется дефолтное значение 600мс)
     *
     * @param key-ключ нового элемента
     * @param obj-значение которое будет хранится по ключу
     */
    public void putElementWithTTL(String key, Object obj) {
        putElementWithTTL(key, obj, 600);
    }

    public void putElementWithTTL(String key, Element element) {
        hashMap.put(key, element);
    }

    public Object deleteElement(String key) {
        Object o = hashMap.get(key);
        hashMap.remove(key);
        return o;
    }
}
