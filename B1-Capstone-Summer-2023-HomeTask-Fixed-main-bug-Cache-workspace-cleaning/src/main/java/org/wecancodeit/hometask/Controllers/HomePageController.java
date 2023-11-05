package org.wecancodeit.hometask.Controllers;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.wecancodeit.hometask.Models.*;
import org.wecancodeit.hometask.Services.*;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class HomePageController {

    @Resource
    private final UserService userService;

    @Resource
    private final HouseholdService householdService;

    @Resource
    private final RewardService rewardService;

    @Resource
    private final TaskService taskService;

    @Resource
    private final HouseholdMemberService memberService;

    public HomePageController(UserService userService, HouseholdMemberService memberService, TaskService taskService,
            HouseholdService householdService, RewardService rewardService) {
        this.userService = userService;
        this.householdService = householdService;
        this.rewardService = rewardService;
        this.taskService = taskService;
        this.memberService = memberService;
    }

    /*
     * Attaches the household attribute to page
     */
    @GetMapping("/dashboard")
    public String displayHousehold(Model model, HttpServletRequest request) throws Exception {
        long userId = userService.getUserId(request);
        User user = userService.getUserById(userId);
        String name = user.getHousehold().getName();
        model.addAttribute("household", householdService.retrieveHouseholdByName(name));

        return "HouseholdFamilyDashboard";
    }

    @GetMapping("/tasks")
    public String showTaskForm(Model model, HttpServletRequest request) throws Exception {
        // retrieve Sign in token and populate create task with household token as
        // attribute
        long userId = userService.getUserId(request);
        User user = userService.getUserById(userId);
        String name = user.getHousehold().getName();
        Household household = householdService.retrieveHouseholdByName(name);

        model.addAttribute("task", new Task());
        model.addAttribute("household", household);
        model.addAttribute("tasks", taskService.getAllTasks());
        model.addAttribute("rewards", rewardService.retrieveAllRewards());

        return "tasks";
    }

    @PostMapping("/tasks")
    public String createTask(@ModelAttribute Task task, Model model, HttpServletRequest request) throws Exception {
        long userId = userService.getUserId(request);
        User user = userService.getUserById(userId);
        String name = user.getHousehold().getName();
        Household household = householdService.retrieveHouseholdByName(name);
        task.setHousehold(household);
        task.setCreatedDate(LocalDate.now());
        task.setPending(true);
        taskService.save(task);
        return "redirect:/dashboard";
    }


    @GetMapping({ "/completed-tasks" })
    public String getCompletedTasks(@ModelAttribute Task task, HttpServletRequest request, Model model) throws Exception {
        long userId = userService.getUserId(request);
        User user = userService.getUserById(userId);
        String name = user.getHousehold().getName();
        Household household = householdService.retrieveHouseholdByName(name);
        List<Task> completedTasks = taskService.getCompletedTasksFromHousehold(household);
        List<Reward> rewardsForCompletedTasks = new ArrayList<>();
        for (Task completedTask : completedTasks) {
            Reward reward = completedTask.getReward();
            if (reward != null) {
                rewardsForCompletedTasks.add(reward);
            }
        }
        model.addAttribute("household", household);
        model.addAttribute("completed-tasks", completedTasks);
        model.addAttribute("rewardsForCompletedTasks", rewardsForCompletedTasks);
        return "completed-tasks";
    }

    @ModelAttribute("householdBank")
    public Household createHouseholdBank(HttpServletRequest request) throws Exception{
         long userId = userService.getUserId(request);
        User user = userService.getUserById(userId);
        String name = user.getHousehold().getName();
        return householdService.retrieveHouseholdByName(name);
    }

    @GetMapping({"/allowance"})
    public String allowancePage(Model model, HttpServletRequest request) throws Exception {
        long userId = userService.getUserId(request);
        User user = userService.getUserById(userId);
        String name = user.getHousehold().getName();
        model.addAttribute("household", householdService.retrieveHouseholdByName(name));
        model.addAttribute("allowance", model);
        return "allowance";
    }

     @PostMapping("/increment")
    public String incrementDouble(@ModelAttribute("householdBank") Household householdBank,
    @RequestParam("incrementAmount") double incrementAmount) throws Exception {
        
        
        double bank = householdBank.getHouseholdBank();
        householdBank.setHouseholdBank(bank + incrementAmount);
        
        householdService.save(householdBank);
        return "redirect:/dashboard";
    }

    @GetMapping("/rewards")
    public String showRewardForm(Model model, HttpServletRequest request) throws Exception {
        long userId = userService.getUserId(request);
        User user = userService.getUserById(userId);
        String name = user.getHousehold().getName();
        Household household = householdService.retrieveHouseholdByName(name);
        
        model.addAttribute("reward", new Reward());
        model.addAttribute("rewards", rewardService.retrieveAllRewards());
        model.addAttribute("tasks", taskService.getAllTasks());
        model.addAttribute("household", household);
        return "rewards";
    }
    

    
}
