package ge.casestudy.todo.service;


import ge.casestudy.todo.utils.TodoFileReader;
import ge.casestudy.todo.utils.TodoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TodoService {


    @Value("${file.destination}")
    private String fileDestination;

    @Value("${file.url}")
    private String fileUrl;


    private final TodoFileReader todoFileReader;

    private final TodoHelper todoHelper;

    private List<String> todoList;
    private Map<String, List<String>> priorityMap;
    private Map<String, List<String>> projectMap;





    @Autowired
    public TodoService(TodoFileReader todoFileReader, TodoHelper todoParser) {
        this.todoFileReader = todoFileReader;
        this.todoHelper = todoParser;
    }

    public void loadFile (){
        todoHelper.fetchFileFromURL(fileUrl,fileDestination);
        todosToList();
        manageTodos();
    }


    public List<String> loadAll(){
        return todoList;
    }

    public String loadById(Long id){
        List<String> todos= todosToList();
        if(todos.size()>id){
            return todos.get(id.byteValue());
        }
        return null;
    }

    private List<String> todosToList(){
        todoList = todoFileReader.readTodos();
        return todoList;
    }

    public void manageTodos(){
        priorityMap = new HashMap<>();
        projectMap = new HashMap<>();
        for(String todo : todoList){
            if(todo.length() > 2) {
                String priority = todoHelper.getPriority(todo.substring(1, 2));
                priorityMap.computeIfAbsent(priority, k -> new ArrayList<>()).add(todo);
            }
            List<String> projects = todoHelper.extractProjects(todo);
            for(String project : projects){
                projectMap.computeIfAbsent(project, k -> new ArrayList<>()).add(todo);
            }
        }

    }

    public Map<String, List<String>> getSortedPriorityMapByKeys() {
        return priorityMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        HashMap::new
                ));
    }

    public List<String> filterByProject(String projectName){
        return projectMap.get(projectName);
    }

}