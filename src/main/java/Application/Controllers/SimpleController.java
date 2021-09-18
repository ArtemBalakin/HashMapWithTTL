package Application.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Balakin Artem( 06.09.2021)
 * @version Java11(SpringBoot)
 * Данный класс является контроллером для отправки нужных файлов к представлению
 */
@Controller
public class SimpleController {

    /**
     * При переходе по данному URL пользователь попадает на главную страницу сайта
     *
     * @return Шаблон mainPage
     */
    @GetMapping("/")
    public String getMainPage() {
        return "mainPage";
    }

    /**
     * При переходе по данному URL пользователь попадает на страницу со всеми командами
     *
     * @return Шаблон commands
     */
    @GetMapping("/commands")
    public String getPageWithCommands() {
        return "commands";
    }

    @GetMapping("/documentation")
    public String getPageWithDoc() {
        return "JavaDoc/index";
    }
}

