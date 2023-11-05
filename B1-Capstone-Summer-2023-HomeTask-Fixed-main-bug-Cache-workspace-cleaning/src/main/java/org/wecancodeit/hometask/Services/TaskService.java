package org.wecancodeit.hometask.Services;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.wecancodeit.hometask.Models.Household;
import org.wecancodeit.hometask.Models.Task;
import org.wecancodeit.hometask.Repositories.TaskRepository;

@Service
public class TaskService {
    
    private final TaskRepository taskRepo;

    public TaskService(TaskRepository taskRepo) {
        this.taskRepo = taskRepo;
    }

    public Task save(Task task){
        return taskRepo.save(task);
    }
    public Task retrieveTaskById(Long id)throws Exception{
        try{
            return taskRepo.findById(id).get();
        }catch(Exception e){
            throw new Exception("Task not Found");
        }
    }
    public void delete(Long id)throws Exception{
        if(!taskRepo.existsById(id)){
            throw new Exception("Task not Found");
        }
    }

    public Iterable<Task> getAllTasks() {
        return taskRepo.findAll();
    }

    public List<Task> getCompletedTasksFromHousehold(Household household) {
        List<Task> completedTasks = new ArrayList<>();
        for (Task task : taskRepo.findAll()) {
            if(task.isCompleted() && task.getHousehold().equals(household)) {
                completedTasks.add(task);
            }
        }
        return completedTasks;
    }
    
    

    
}
