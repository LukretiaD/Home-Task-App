package org.wecancodeit.hometask.Controllers;


import java.io.IOException;

import org.springframework.http.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.wecancodeit.hometask.Models.Household;
import org.wecancodeit.hometask.Models.User;
import org.wecancodeit.hometask.Repositories.HouseholdRepository;
import org.wecancodeit.hometask.Services.*;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class HouseholdController {
    @Resource
    private HouseholdRepository householdRepository;

    @Resource
    private HouseholdService householdService;

    @Resource
    private TaskService taskService;

    @Resource
    private UserService userService;

    @Resource
    private HouseholdMemberService memberService;

    @Resource
    private RewardService rewardService;

    public HouseholdController(HouseholdService householdService, HouseholdMemberService memberService, TaskService taskService, UserService userService) {
        this.householdService = householdService;
        this.taskService = taskService;
        this.userService = userService;
        this.memberService = memberService;
    }

/*
 * Creates household and connects it to the User.
 */
    @PostMapping("/create-household")
    public String createHousehold(@RequestParam String name, HttpServletRequest request) throws Exception {
        Household household = householdService.retrieveHouseholdByName(name);
        long userId = userService.getUserId(request);
        if(userId==0){
            throw new Exception("Not logged in");
        }
        User user = userService.getUserById(userId);
        if (household == null) {
            Household newHousehold = new Household(name, user,0,null);
            householdService.save(newHousehold);
        }
        return "redirect:/dashboard";
    }

    @PostMapping("/upload-profile-picture")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,HttpServletRequest request) throws Exception {
        long userId = userService.getUserId(request);
        User user = userService.getUserById(userId);
        String name = user.getHousehold().getName();
        Household household = householdService.retrieveHouseholdByName(name);
        try {
            
            if (household != null && !file.isEmpty()) {
                byte[] imageData = file.getBytes();
                household.setProfilePicture(imageData);
                householdService.save(household);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/dashboard";
    }

    @GetMapping("/displayProfilePicture/{householdId}")
public ResponseEntity<byte[]> getProfilePicture(@PathVariable Long householdId, Model model) throws Exception {
    
     
    Household household = householdService.retrieveHouseholdById(householdId);
    model.addAttribute("household", household);
        if (household != null && household.getProfilePicture() != null) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(household.getProfilePicture(), headers, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
}

}



