package Application.Entityes;

/**
 * @author Balakin Artem( 06.09.2021)
 * Данный класс- вспомогательная сущность для реализации удаления элементов из коллекции
 * ttl- время через которое объект должен удалится( в миллисекундах)
 * dieTime- системное время удаления объекта(System.currentTimeMillis()+ttl)( в миллисекундах)
 */
public class TTL {

    private final long ttl;
    private final long dieTime;


    public TTL(long ttl) {
        this.ttl = ttl;
        dieTime = System.currentTimeMillis() + ttl;
    }

    public long getTtl() {
        return ttl;
    }

    /**
     * Проверяет не настало ли время для удаления объкта
     *
     * @return true/false- либо жив, либо уже нет
     */
    public boolean isTTLAlive() {
        return System.currentTimeMillis() < dieTime;
    }

    @Override
    public String toString() {
        return String.valueOf(ttl);
    }
}
