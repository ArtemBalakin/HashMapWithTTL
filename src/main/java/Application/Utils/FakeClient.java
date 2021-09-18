package Application.Utils;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

/**
 * @author Balakin Artem( 06.09.2021)
 * Класс для взаимодействия с сервером. Отсылает HTTP запросы.
 */
public class FakeClient {
    private final RestTemplate restTemplate = new RestTemplate();
    private String mainURL;

    public FakeClient(String mainURL) throws IOException {
        if (checkConnection(mainURL)) {
            this.mainURL = mainURL;
        } else {
            System.out.println("Server inaccessible, check URL");
            System.exit(1);
        }
    }

    public FakeClient(String mainURL, int portNumber) throws IOException {
        if (checkConnection(mainURL, portNumber)){
            this.mainURL = mainURL + ":" + portNumber;}
        else{ System.out.println("Server inaccessible, check URL");
        System.exit(1);}
    }

    private boolean checkConnection(String mainURL, int portNumber) {
        URL url;
        try {
            url = new URL(mainURL + ":" + portNumber);
            URLConnection conn = url.openConnection();
            conn.connect();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    private boolean checkConnection(String mainURL) {
        URL url;
        try {
            url = new URL(mainURL);
            URLConnection conn = url.openConnection();
            conn.connect();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * Отправляет объект на сервер с заданным TTL
     *
     * @param key-   ключ объекта
     * @param value- значение объекта
     * @param ttl    - время жизни объекта
     */
    public void postRequest(String key, Object value, long ttl) {
        String postRequestWithTTL = "/putElementWithTTL?key=%s&value=%s&ttl=%s";
        String str = String.format(mainURL + postRequestWithTTL, key, value, ttl);
        restTemplate.postForObject(str, "str", String.class);
    }

    /**
     * Отправляет объект на сервер с дефолтным TTL
     *
     * @param key-   ключ объекта
     * @param value- значение объекта
     */
    public void postRequest(String key, Object value) {
        postRequest(key, value, 600);
    }

    /**
     * Запрашивает у сервера объкт по ключу
     *
     * @param key- ключ объекта
     */
    public Object getRequest(String key) {
        String getRequest = "/getElement?key=%s";
        String str = String.format(mainURL + getRequest, key);
        return restTemplate.getForObject(str, String.class);
    }

    /**
     * Удаляет объект из сервера по ключу
     *
     * @param key- ключ объекта
     */
    public Object deleteRequest(String key) {
        String deleteRequest = "/deleteElement?key=%s";
        String str = String.format(mainURL + deleteRequest, key);
        restTemplate.delete(str);
        return "delete";
    }

    /**
     * Отправляет запрос на сохранение коллекции на сервере
     */
    public Object saveRequest() {
        String saveRequest = String.format("/saveElementsToFile?fileName=%s", "data.csv");
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
        restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);
        return restTemplate.postForObject(mainURL + saveRequest, "data.csv", String.class);
    }

    /**
     * Отправляет запрос за загрузку коллекции из файла на сервере
     */
    public Object uploadRequest() {
        String uploadRequest = String.format("/uploadCollection?fileName=%s", "data.csv");
        return restTemplate.getForObject(mainURL + uploadRequest, String.class);
    }
}

