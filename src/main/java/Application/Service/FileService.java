package Application.Service;

import Application.Entityes.Element;
import Application.Entityes.TTL;
import Application.IO.CsvIO;
import Application.Utils.MIME;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Balakin Artem( 06.09.2021)
 * Сервис для работы с файлами
 */
@Service
public class FileService {
    private final String mainserverDirectory = "serverFiles";
    private final String mainUsersDirectory = "usersFiles";
    private final DatabaseService databaseService;
    private final CsvIO csvIO = new CsvIO(';');


    @Autowired
    public FileService(DatabaseService databaseService) throws IOException {
        new File(mainserverDirectory).mkdirs();
        new File(mainUsersDirectory).mkdirs();
        csvIO.saveDataToExistFile(new ArrayList(), mainUsersDirectory +"/paths.csv");
        databaseService.readAllPaths(csvIO.uploadData(mainUsersDirectory + "/paths.csv"));
        this.databaseService = databaseService;
    }

    /**
     * Загружает файл на сервер
     * @param file- файл который отправил пользователь
     * @return уникальный ключ для доступа к файлу
     * @throws Exception при ошибке записи
     */
    public String uploadFile(MultipartFile file) throws Exception {
        ArrayList<String[]> list = new ArrayList<>();
        String fileType = file.getContentType();
        String path = mainUsersDirectory + "/" + fileType + "/";
        new File(path).mkdirs();
        path = mainUsersDirectory + "/" + fileType + "/" + file.getOriginalFilename();
        System.out.println(path);
        FileCopyUtils.copy(file.getBytes(), new File(path));
        String key = path.hashCode() + "";
        System.out.println("key:" + key);
        databaseService.getPathsToUsersFile().put(key, path);
        list.add(new String[]{key, path});
        csvIO.saveDataToExistFile(list, mainUsersDirectory + "/paths.csv");
        return key;
    }

    /**
     * Отдает файл пользователю на загрузку
     * @param key- уникальный ключ файла(был получен при загрузке на сервер)
     * @return файл по ключу
     * @throws Exception - если файл не найден или нет доступа
     */
    public ResponseEntity<ByteArrayResource> downloadFile(String key) throws Exception {
        String pathToFile = databaseService.getPathsToUsersFile().get(key);

        if (pathToFile != null && new File(pathToFile).exists()) {
            Path path = Paths.get(pathToFile);
            byte[] data = Files.readAllBytes(path);
            ByteArrayResource resource = new ByteArrayResource(data);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + path.getFileName().toString())
                    .contentType(MediaType.valueOf(MIME.getMediaType(pathToFile).toString()))
                    .contentLength(data.length)
                    .body(resource);

        }
        return ResponseEntity.ok()
                .header(HttpHeaders.WARNING, "File don't exist")
                .contentLength("File don't exist".length())
                .body(new ByteArrayResource("File don't exist".getBytes()));
    }

    /**
     * Сохраняет колекцию в csv
     * @param fileName- название файла для сохранения
     * @return сохраненный файл
     * @throws IOException
     */
    public ResponseEntity<ByteArrayResource> saveDataToFile(String fileName) throws IOException {
        new File(mainserverDirectory).mkdir();
        ArrayList<String[]> list = new ArrayList<>();
        databaseService.getAllMap().getHashMap().forEach(((s, element) -> {
            list.add(new String[]{s, element.getObject().toString(), element.getTtl().getTtl() + ""});
        }));
        csvIO.saveDataToExistFile(list, mainserverDirectory + "/" + fileName);
        Path path = Paths.get(mainserverDirectory + "/" + fileName);
        byte[] data = Files.readAllBytes(path);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + path.getFileName().toString())
                .contentLength(data.length)
                .body(resource);

    }


    /**
     * Записывает данные из файла в коллекцию
     */
    public void uploadData(String fileName) throws IOException {
        ArrayList<String[]> list = csvIO.uploadData(mainserverDirectory + "/" + fileName);
        for (String[] strings : list) {
            System.out.println(Arrays.toString(strings));
            String key = strings[0];

            Element element = new Element(strings[1], new TTL(Long.parseLong(strings[2])));
            databaseService.putElement(key, element);
        }
        System.out.println(databaseService.getAllMap().getHashMap().size());
    }

}



