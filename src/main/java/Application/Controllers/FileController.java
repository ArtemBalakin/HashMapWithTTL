package Application.Controllers;


import Application.Service.FileService;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.FileNotFoundException;
import java.io.IOException;
/**
 * @author Balakin Artem( 06.09.2021)
 * Класс для принятия HTTP команд связанных с файлами.
 */
@RestController
@Validated
public class FileController {

    FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("files") @NotNull MultipartFile[] file) {
        System.out.println(file.length);
        for (MultipartFile multipartFile : file) {
            try {
                String keyForFile = fileService.uploadFile(multipartFile);
                return keyForFile;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return  null;
    }

    @GetMapping("/downloadFile")
    public ResponseEntity<ByteArrayResource> downloadFile(@RequestParam("fileId") @NotNull @Length(min = 1) String fileId) throws Exception {
        return fileService.downloadFile(fileId);
    }

    /**
     * Записывает коллекцию в сущетвующий CSV
     *
     * @return
     * @throws IOException
     */
    @PostMapping("/saveElementsToFile")
    public ResponseEntity<ByteArrayResource> saveMapToExistFile(@RequestParam("fileName") @NotNull @Length(min = 1) String fileName) throws IOException {
        return fileService.saveDataToFile(fileName);
    }

    /**
     * Загружает коллекцию из csv файла
     *
     * @return
     * @throws IOException
     */
    @GetMapping("/uploadCollection")
    public ResponseEntity<String> uploadMap(@RequestParam("fileName") @NotNull @Length(min = 1) String fileName) throws IOException {
        try {
            fileService.uploadData(fileName);
        } catch (FileNotFoundException e) {
            return new ResponseEntity<>("File don't find", HttpStatus.OK);
        }
        return new ResponseEntity<>("Collection uploaded", HttpStatus.OK);
    }

}
