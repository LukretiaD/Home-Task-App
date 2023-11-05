package org.wecancodeit.hometask.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.wecancodeit.hometask.Models.*;
import org.wecancodeit.hometask.Services.*;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class RewardController  {

    @Resource
    private final RewardService rewardService;
    @Resource
    private final TaskService taskService;
    @Resource
    private final HouseholdService householdService;
    @Resource
    private final UserService userService;

    public RewardController(RewardService rewardService, UserService userService, TaskService taskService, HouseholdService householdService) {
        this.rewardService = rewardService;
        this.taskService = taskService;
        this.householdService = householdService;
        this.userService = userService;
    }




    @PostMapping("/rewards")
    public String createReward(@ModelAttribute Reward reward, Model model, HttpServletRequest request) throws Exception {

        
        long userId = userService.getUserId(request);
        User user = userService.getUserById(userId);
        String name = user.getHousehold().getName();
        Household household = householdService.retrieveHouseholdByName(name);
        reward.setHousehold(household);
        reward.setOther(false);
        rewardService.save(reward);
        return "redirect:/dashboard";
    }


    @GetMapping({"/reward/{RewardID}" })
    public Reward retrieveRewardById(@PathVariable Long RewardID) throws Exception {
        return rewardService.retrieveRewardById(RewardID);
    }

    /*
     * Save reward from user input
     */
    @PostMapping("/saveReward")
    public String saveReward(@ModelAttribute("reward") Reward Reward) {
        return "redirect:/reward";
    }
}
