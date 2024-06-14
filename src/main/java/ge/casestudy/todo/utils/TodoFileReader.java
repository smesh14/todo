package ge.casestudy.todo.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Repository
public class TodoFileReader {


    private File file;

    @Value("${file.destination}")
    private String fileDestination;

    private void createFileIfNotExists() {
        try {
            file.createNewFile();
        } catch (IOException e) {
            log.info("Error creating the todo.txt file: " + e.getMessage());
        }
    }

    public List<String> readTodos() {
        this.file = new File(fileDestination);
        createFileIfNotExists();
        List<String> todos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                todos.add(line);
            }
        } catch (IOException e) {
            log.info("Error reading the todo.txt file: " + e.getMessage());
        }
        return todos;
    }


}