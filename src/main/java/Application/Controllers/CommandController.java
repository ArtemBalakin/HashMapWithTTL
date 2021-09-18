package Application.Controllers;

import Application.Entityes.Element;
import Application.Service.DatabaseService;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


/**
 * @author Balakin artem
 * Контроллер для обработки команд и отправки ответа в виде данных
 * myService- серфис для работы с коллекцией
 */
@RestController
@Validated
public class CommandController {

    private final DatabaseService databaseService;

    @Autowired
    public CommandController(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    /**
     * Возвращает элемент по ключу
     *
     * @param key-ключ элемента
     * @return объект коллекции
     */
    @GetMapping("/getElement")
    public ResponseEntity<String> getElement(@RequestParam("key") @NotNull @Length(min = 1) String key) {
        Element element = databaseService.getElement(key);
        if (element != null)
            return new ResponseEntity<>(element.toString(), HttpStatus.OK);
        return new ResponseEntity<>("Element don't find", HttpStatus.OK);
    }

    /**
     * Записывает эллемент в коллекцию без указакия ttl
     *
     * @param key-   ключ элемента
     * @param value- значение
     * @return Ответ в виде строки при успешном выполнении операции
     */
    @PostMapping("/putElement")
    public ResponseEntity<String> putElement(@RequestParam("key") @NotNull String key,
                                             @RequestParam("value") @NotNull String value) {
        databaseService.putElement(key, value);
        return new ResponseEntity<>("Add element", HttpStatus.OK);
    }

    /**
     * Записывает эллемент в коллекцию с указанием ttl
     *
     * @param key-   ключ элемента
     * @param value- значение
     * @param ttl-   время жизни объекта
     * @return Ответ в виде строки при успешном выполнении операции
     */
    @PostMapping("/putElementWithTTL")
    public ResponseEntity<Object> putElement(@RequestParam("key") @NotNull @Length(min = 1) String key, @RequestParam("value") String value,
                                             @RequestParam("ttl")  @Min(1) long ttl) {
        databaseService.putElement(key, value, ttl);
        return new ResponseEntity<>("Add element", HttpStatus.OK);
    }

    /**
     * Удаляет эллемент из коллекции по ключу
     *
     * @return возвращает удаленный элемент
     */
    @DeleteMapping("/deleteElement")
    public ResponseEntity<Object> deleteElement(@RequestParam("key") @NotNull @Length(min = 1) String key) {
        return new ResponseEntity<>(databaseService.deleteElement(key), HttpStatus.OK);
    }
}

