package main;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import response.Task;
import java.util.List;

@RestController
public class TaskController {
    private static final Logger MARKLOGGER = LogManager.getLogger(TaskController.class);
    private static final Marker VARS = MarkerManager.getMarker("VARS");

    @GetMapping("/tasks/")
    public List<Task> listTasks() {
        return Storage.getAllTasks();

    }

    @PostMapping("/tasks/")
    public ResponseEntity addTask (Task task) {
        return new ResponseEntity(Storage.addTask(task), HttpStatus.CREATED);
    }


    @GetMapping("/tasks/{id}")
    public ResponseEntity getTask (@PathVariable int id) {
        Task result = Storage.getTask(id);
        if (result == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity delTask (@PathVariable int id) {
        if (!Storage.delTask(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity(true, HttpStatus.OK);

    }
    @DeleteMapping("/tasks/all")
    public ResponseEntity clearList () {
        if (Storage.clearList()) {
            return new ResponseEntity(Storage.getAllTasks(), HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity modifyTask (@PathVariable int id,String name,String description)
    {
        if (!Storage.modifyName(id, name) || !Storage.modifyDescription(id,description)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity(true, HttpStatus.OK);
    }

}
