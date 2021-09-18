package Application.IO;

import au.com.bytecode.opencsv.CSVReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


/**
 * @author Balakin Artem( 06.09.2021)
 * Класс для чтения данных из csv файла
 * fileName-Имя файла из которого произойдет чтение( если файл не существует, он будет создан)
 * separator- разделитель в файле
 */
public class CsvFileReader {

    private final char separator;

    public CsvFileReader(char separator) {
        this.separator = separator;

    }

    /**
     * Записывает данные из файла в ArrayList для дальнейшей работы
     *
     */
    public ArrayList<String[]> readCsvFile(String fileName) throws IOException {
        if (new File(fileName).exists()) {
            ArrayList<String[]> list = new ArrayList<>();
            CSVReader reader = new CSVReader(new FileReader(fileName), this.separator);
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                list.add(nextLine);
            }
            return list;
        }
        throw new FileNotFoundException("File don't find");
    }
}