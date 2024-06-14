package ge.casestudy.todo.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class TodoHelper {

    private static final String PRIORITY_REGEX = "^[A-Z]+$";

    public void fetchFileFromURL(String url, String destinationPath) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        try {
            HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

            if (response.statusCode() == 200) {
                Path path = Path.of(destinationPath);
                Files.copy(response.body(), path, StandardCopyOption.REPLACE_EXISTING);
                log.info("File downloaded successfully to " + path);
            } else {
                log.info("Failed to get the file. HTTP status code: " + response.statusCode());
            }
        } catch (InterruptedException | IOException e) {
            log.info("Error during file download: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }


    public  String  getPriority(String todoPrefix) {
        if(todoPrefix.matches(PRIORITY_REGEX)){
            return todoPrefix;
        }
        return "z";
    }

    public  List<String> extractProjects(String source) {
        Pattern pattern = Pattern.compile("(?<=\\s\\+)\\S+");
        Matcher matcher = pattern.matcher(source);

        List<String> substrings = new ArrayList<>();

        while (matcher.find()) {
            substrings.add(matcher.group());
        }

        return substrings;
    }

}