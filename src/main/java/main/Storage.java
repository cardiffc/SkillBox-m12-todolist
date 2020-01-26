package main;
import main.model.Task;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Storage {
    private static Map<Integer, Task> taskList = new ConcurrentHashMap<>();
    private static volatile int currentId = 1;

    public static List<Task> getAllTasks () {
        List<Task> allTasks = new ArrayList<>();
        allTasks.addAll(taskList.values());
        return allTasks;
    }

    public static int addTask (Task task) {
        int id = currentId++;
        task.setId(id);
        taskList.put(id, task);
        return id;
    }

    public static Task getTask (int id) {
        if (taskList.containsKey(id)) {
            return taskList.get(id);
        }
        return null;
    }

    public static boolean delTask (int id) {
        if (taskList.containsKey(id)) {
            taskList.remove(id);
            return true;
        }
        return false;
    }

    public static boolean modifyDescription(int id, String description) {
        if (taskList.containsKey(id)) {
            taskList.get(id).setDescription(description);
            return true;
        }
        return false;
    }

    public static boolean modifyName (int id, String name) {
        if (taskList.containsKey(id)) {
            taskList.get(id).setName(name);
            return true;
        }
        return false;

    }

    public static boolean clearList () {
        taskList.clear();
        currentId = 1;
        return taskList.size() == 0;
    }

}
