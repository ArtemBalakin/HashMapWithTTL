import Application.Database.LocalDatabase;
import Application.Entityes.Element;
import Application.Entityes.TTL;
import Application.HashMap.HashMapWithTTL;
import Application.Service.DatabaseService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DatabaseServiceTest {
    private final HashMapWithTTL hashMapWithTTL = new HashMapWithTTL();
    private final LocalDatabase database = new LocalDatabase(hashMapWithTTL);
    private final DatabaseService databaseService = new DatabaseService(database);

    @Before
    public void addElements() {
        databaseService.putElement("Key", new Element("Str", new TTL(1000)));
        databaseService.putElement("Key2", new Element("Str2", new TTL(1000)));
        databaseService.putElement("Key3", new Element("Str3", new TTL(1000)));
        databaseService.putElement("Key4", new Element("Str4", new TTL(1000)));
    }

    @Test
    public void addElementsTest() {
        Assert.assertEquals(4, databaseService.getAllMap().getHashMap().size());
    }

    @Test
    public void getElementTest() {
        Assert.assertEquals("Str", databaseService.getElement("Key").getObject());
        Assert.assertEquals("Str2", databaseService.getElement("Key2").getObject());
        Assert.assertEquals("Str3", databaseService.getElement("Key3").getObject());
        Assert.assertEquals("Str4", databaseService.getElement("Key4").getObject());
    }

    @Test
    public void deleteElementTest() {
        databaseService.deleteElement("Key");
        Assert.assertNull(databaseService.getElement("Key"));
        databaseService.deleteElement("Key2");
        Assert.assertNull(databaseService.getElement("Key2"));
        databaseService.deleteElement("Key3");
        Assert.assertNull(databaseService.getElement("Key3"));
    }

    @Test
    public void putElementWithoutTTL() {
        databaseService.putElement("WithoutTTL", "Object");
        Element element = databaseService.getElement("WithoutTTL");
        Assert.assertEquals(600, element.getTtl().getTtl());
    }

    @Test
    public void timeTest() {
        sleepForSecond();
        databaseService.putElement("Key", new Element("Str", new TTL(1)));
        Assert.assertEquals("Str", databaseService.getElement("Key").getObject());
        sleepForSecond();
        Assert.assertNull(databaseService.getElement("Key"));
       }
    private static void sleepForSecond(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

