package Application.IO;

import Application.Entityes.Element;
import Application.HashMap.HashMapWithTTL;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * @author Balakin Artem( 06.09.2021)
 * Класс для работы с CSV файлами. Объединяет Reader и Writer
 */
public class CsvIO {
    private final CsvFileWriter writer;
    private final CsvFileReader reader;

    public CsvIO(char separator) {
        writer = new CsvFileWriter(separator);
        reader = new CsvFileReader(separator);
    }

    /**
     * Записывает данные из коллекции в существующий CSV файл
     *
     * @param arrayList- HashMap откуда берутся данные
     */
    public void saveDataToExistFile(ArrayList arrayList, String fileName) throws IOException {
        writer.AppendDataToCsvFile(arrayList, fileName);
    }


    /**
     * Записывает данные из файла в коллекцию
     */
    public ArrayList<String[]> uploadData(String fileName) throws IOException {
        return reader.readCsvFile(fileName);
    }
}
