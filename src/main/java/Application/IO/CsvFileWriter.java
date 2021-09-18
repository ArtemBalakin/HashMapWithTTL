package Application.IO;

import Application.HashMap.HashMapWithTTL;
import au.com.bytecode.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Balakin Artem( 06.09.2021)
 * Класс для записи данных в csv файл
 * fileName-Имя файла в который произойдет запись( если файл не существует, он будет создан)
 * separator- разделитель для записей
 */
public class CsvFileWriter {


    private char separator;

    public CsvFileWriter( char separator) {
        this.separator = separator;
    }



    public char getSeparator() {
        return separator;
    }

    public void setSeparator(char separator) {
        this.separator = separator;
    }

    /**
     * Добавляет данные из коллекции в CSV файл
     *
     * @param arrayList- HashMap откуда берутся данные
     */
    public void AppendDataToCsvFile(ArrayList<String[]> arrayList, String fileName) throws IOException {
        CSVWriter writer = new CSVWriter(new FileWriter(fileName, true), this.separator);
        writeData(arrayList, writer);
    }
    /**
     * Запись в csv
     */
    private void writeData(ArrayList<String[]> strings, CSVWriter writer) throws IOException {
        strings.forEach(writer::writeNext);
        writer.close();
    }
}

