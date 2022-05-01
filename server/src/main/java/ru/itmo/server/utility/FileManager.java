package ru.itmo.server.utility;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import ru.itmo.common.model.HumanBeing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class FileManager {
    private final Gson gson = new Gson();
    private final String envVariable;

    public FileManager(String envVariable) {
        this.envVariable = envVariable;
    }

    /**
     * Записывает коллекцию в файл
     * @param collection Необходимая коллекция
     */
    public void writeCollection(Collection<?> collection) {
        if (System.getenv().get(envVariable) != null) {
            try (FileWriter collectionFileWriter = new FileWriter(new File(System.getenv().get(envVariable)))) {
                collectionFileWriter.write(gson.toJson(collection));
                System.out.println("Коллекция успешно сохранена в файл.");
            } catch (IOException exception) {
                System.err.println("Загрузочный файл является не может быть открыт.");
            }
        } else System.err.println("Системная переменная с загрузочным файлом не найдена!");
    }

    /**
     * Загружает коллекцию из файла
     * @return Коллекция, загруженная из файла, либо новая пустая коллекция
     */
    public ArrayDeque<HumanBeing> readCollection(){
        if (System.getenv().get(envVariable) != null) {
            try (Scanner scanner = new Scanner(new File(System.getenv().get(envVariable)))) {
                ArrayDeque<HumanBeing> collection;
                Type collectionType = new TypeToken<ArrayDeque<HumanBeing>>() {
                }.getType();
                collection = gson.fromJson(scanner.nextLine().trim(), collectionType);
                System.out.println("Коллекция успешно загружена.");
                return collection;
            } catch (FileNotFoundException exception) {
                System.err.println("Загрузочный файл не найден, коллекция будет создана автоматически.");
            } catch (NoSuchElementException exception) {
                System.err.println("Загрузочный файл пуст!");
            } catch (JsonParseException | NullPointerException exception) {
                System.err.println("В загрузочном файле не обнаружена корректная коллекция.");
            } catch (IllegalStateException exception) {
                System.err.println("Непредвиденная ошибка!");
                System.exit(0);
            }
        } else System.err.println("Системная переменная с загрузочным файлом не найдена!");
        return new ArrayDeque<>();
    }
}
