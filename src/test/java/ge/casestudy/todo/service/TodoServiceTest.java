package ge.casestudy.todo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class TodoServiceTest {

    List<String> tasks = Arrays.asList(
            "(A) 2024-06-14 Call Mom @phone",
            "2024-06-14 Buy groceries +Shopping @store",
            "Review project proposal +Work",
            "Email team about meeting +Work @email",
            "x 2024-06-14 2024-06-13 Submit report +Work @email",
            "2024-06-14 Plan birthday party +Personal @home",
            "Plan weekend trip +Leisure",
            "x 2024-06-14 Clean the garage +Chores @home",
            "Call Jane about the project +Work @phone",
            "2024-06-14 Prepare presentation +Work",
            "2024-06-14 Call service center @phone",
            "Finish reading book +Leisure @home"
    );



    @Autowired
    private TodoService todoService;


    @Test
    public void testLoadById() {

        String result = "(A) 2024-06-14 Call Mom @phone";
        todoService.loadFile();

        assertEquals(result, todoService.loadById(0L));
    }

    @Test
    public void testLoadAll(){

        todoService.loadFile();

        assertEquals(tasks.get(0), todoService.loadAll().get(0));
    }


    @Test
    public void testPriorityMap(){
        String result = "(A) 2024-06-14 Call Mom @phone";
        todoService.loadFile();

        Map<String,List<String>> mp = todoService.getSortedPriorityMapByKeys();

        assertEquals(mp.get("A").get(0),result);
    }

    @Test
    public void testProjectMap(){
        String result = "(C) 2024-06-14 Plan birthday party +Personal @home";
        String filter = "Personal";
        todoService.loadFile();

        List<String> projects = todoService.filterByProject(filter);

        assertEquals(projects.get(0),result);
    }

}