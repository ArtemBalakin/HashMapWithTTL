package Application.Entityes;


/**
 * @author Balakin Artem( 06.09.2021)
 * Данный класс является сущностью, которая будет хранится в коллекции
 * ttl- время жизни объкта
 * obj- значение хранящееся в объекте
 */
public class Element {
    private final TTL ttl;
    private final Object object;

    public Element(Object object, TTL ttl) {
        this.ttl = ttl;
        this.object = object;
    }

    public TTL getTtl() {
        return ttl;
    }

    public Object getObject() {
        return object;
    }

    @Override
    public String toString() {
        return "value=" + object + ", ttl=" + ttl;
    }
}
