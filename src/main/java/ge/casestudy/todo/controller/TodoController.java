package ge.casestudy.todo.controller;


import ge.casestudy.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/todo")
public class TodoController {

    private final TodoService todoService;


    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }


    @PostMapping("/loadFile")
    public void create() {
        todoService.loadFile();
    }


    @GetMapping("/loadAll")
    public ResponseEntity<List<String>> loadAll() {
        return ResponseEntity.of(Optional.ofNullable(todoService.loadAll()));
    }


    @GetMapping("/load/{id}")
    public ResponseEntity<String> loadById(@PathVariable Long id) {
        return ResponseEntity.ok(todoService.loadById(id));
    }


    @GetMapping("/loadSortedByPriority")
    public ResponseEntity<Map<String, List<String>>> loadSortedByPriority() {
        return ResponseEntity.ok(todoService.getSortedPriorityMapByKeys());
    }

    @GetMapping("/filterByProject/{projectName}")
    public ResponseEntity<List<String>> filterByProjectName(@PathVariable String projectName) {
        return ResponseEntity.ok(todoService.filterByProject(projectName));
    }

}