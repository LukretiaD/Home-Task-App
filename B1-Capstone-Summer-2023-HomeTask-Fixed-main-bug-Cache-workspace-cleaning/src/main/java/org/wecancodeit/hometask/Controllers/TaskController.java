package org.wecancodeit.hometask.Controllers;
import java.util.Optional;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.wecancodeit.hometask.Models.*;
import org.wecancodeit.hometask.Repositories.TaskRepository;
import org.wecancodeit.hometask.Services.*;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class TaskController {

    @Resource
    private final HouseholdService householdService;
    @Resource
    private final TaskService taskService;
    @Resource 
    private final TaskRepository taskRepo;
    @Resource
    private final HouseholdMemberService memberService;
    @Resource 
    private final RewardService rewardService;
    @Resource
    private final UserService userService;

    public TaskController(HouseholdService householdService, TaskService taskService,
            HouseholdMemberService memberService, RewardService rewardService, UserService userService, TaskRepository taskRepo) {
        this.householdService = householdService;
        this.taskService = taskService;
        this.memberService = memberService;
        this.userService = userService;
        this.rewardService = rewardService;
        this.taskRepo = taskRepo;
    }

    @GetMapping("/task-details/{id}")
    public String TaskDetailsByID(@PathVariable Long id, Model model, HttpServletRequest request) throws Exception{
        Iterable<Task> tasks = taskService.getAllTasks();
        Iterable<HouseholdMember> members =  memberService.retrieveAllMembers();
        model.addAttribute("tasks", tasks);
        model.addAttribute("members", members);

        long userId = userService.getUserId(request);
        User user = userService.getUserById(userId);
        String name = user.getHousehold().getName();
        model.addAttribute("household", householdService.retrieveHouseholdByName(name));
        
        Optional<Task> optTask = taskRepo.findById(id);
        if (optTask.isPresent()) {
            Task task = optTask.get();
            model.addAttribute("task", task);
        }
        model.addAttribute("member", memberService.retrieveAllMembers());
        return "task-details";
    }
    
    
    @PostMapping("/assign-member")
    public String assignMemberToTask(@RequestParam long taskId,@RequestParam long memberId) throws Exception {
        Task task = taskService.retrieveTaskById(taskId);
        HouseholdMember member = memberService.retrieveMemberById(memberId);
        
        task.setMember(member);
        taskService.save(task);
        return "redirect:/dashboard";
    }

    @PostMapping("/mark-completed")
    public String markTaskAsCompleted(@RequestParam("id") Long id) throws Exception{
        Task task = taskService.retrieveTaskById(id);
        if (task != null) {
            task.setCompleted(true);
            task.setPending(false);
            taskService.save(task);
            double addToMember = task.getReward().getCashAmount();
            HouseholdMember member = task.getMember();
            double amount = member.getAmountRemaining();
            member.setAmountRemaining(amount + addToMember);
            memberService.save(member);
        } 
        return "redirect:/dashboard";
    }

}
