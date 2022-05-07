package ru.itmo.server.utility;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import ru.itmo.common.model.HumanBeing;
import ru.itmo.server.ServerLauncher;

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
                ServerLauncher.log.info("Коллекция успешно сохранена в файл.");
            } catch (IOException exception) {
                ServerLauncher.log.error("Загрузочный файл является не может быть открыт.");
            }
        } else ServerLauncher.log.error("Системная переменная с загрузочным файлом не найдена!");
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
                ServerLauncher.log.info("Коллекция успешно загружена.");
                return collection;
            } catch (FileNotFoundException exception) {
                ServerLauncher.log.error("Загрузочный файл не найден, коллекция будет создана автоматически.");
            } catch (NoSuchElementException exception) {
                ServerLauncher.log.error("Загрузочный файл пуст!");
            } catch (JsonParseException | NullPointerException exception) {
                ServerLauncher.log.error("В загрузочном файле не обнаружена корректная коллекция.");
            } catch (IllegalStateException exception) {
                ServerLauncher.log.error("Непредвиденная ошибка!");
                System.exit(0);
            }
        } else ServerLauncher.log.error("Системная переменная с загрузочным файлом не найдена!");
        return new ArrayDeque<>();
    }
}
