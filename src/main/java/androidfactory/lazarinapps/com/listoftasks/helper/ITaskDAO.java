package androidfactory.lazarinapps.com.listoftasks.helper;

import java.util.List;

import androidfactory.lazarinapps.com.listoftasks.model.Task;

public interface ITaskDAO {

    boolean save(Task task);
    boolean update(Task task);
    boolean delete(Task task);
    List<Task> listTasks();
}
