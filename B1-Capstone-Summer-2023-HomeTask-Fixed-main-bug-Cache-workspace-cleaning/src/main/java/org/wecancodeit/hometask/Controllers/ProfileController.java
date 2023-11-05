package org.wecancodeit.hometask.Controllers;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.wecancodeit.hometask.Models.Household;
import org.wecancodeit.hometask.Models.User;
import org.wecancodeit.hometask.Services.HouseholdService;
import org.wecancodeit.hometask.Services.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {
    

    @Autowired
    private UserService userService;

    @Autowired
    private HouseholdService householdService;

/*
     * Routes User to Profile page
     */
    @GetMapping("/profile")
    public String displayProfile(Model model, HttpServletRequest request) throws Exception{
        long userId = userService.getUserId(request);
        User user = userService.getUserById(userId);
        Household household = user.getHousehold();
        
        User trueUser= household.getUser();
        
        model.addAttribute("user", trueUser);
        model.addAttribute("household", household);
        return "profile";
    }






}
