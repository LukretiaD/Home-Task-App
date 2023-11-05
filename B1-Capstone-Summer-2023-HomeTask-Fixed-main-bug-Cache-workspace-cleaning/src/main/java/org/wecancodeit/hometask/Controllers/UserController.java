package org.wecancodeit.hometask.Controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Base64;

import javax.print.attribute.standard.Media;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties.Io;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.wecancodeit.hometask.Models.Household;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.wecancodeit.hometask.Models.LoginDto;
import org.wecancodeit.hometask.Models.User;
import org.wecancodeit.hometask.Services.HouseholdService;
import org.wecancodeit.hometask.Services.UserService;

import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private HouseholdService householdService;

    public UserController(UserService userService, HouseholdService householdService) {
        this.userService = userService;
        this.householdService = householdService;
    }

/*
 * Main page after starting application
 */

    @GetMapping({"","/","/login"})
    public String loginPage(Model model) {
        model.addAttribute("loginDto", new LoginDto());
        return "login";
    }

/*
 * Login control and cookie creation
 */

    @PostMapping({"/loginUser"})
    public String LoginUser(@ModelAttribute("loginDto") LoginDto login, HttpServletResponse response){
    User user = userService.getUserByUsername(login.getUsername());
    if(user==null){
        return "redirect:/";
    }
    //confirm Password
    Cookie jwtTokenCookie = new Cookie("user-id", "" + user.getId());
    Cookie nameCookie = new Cookie("username", ""+user.getUsername());
    response.addCookie(jwtTokenCookie);
    response.addCookie(nameCookie);
    return "redirect:/logon";
    }

    @GetMapping("/logon")
    public String homePage(Model model, HttpServletRequest request) throws Exception {
    long userId = userService.getUserId(request);
    if(userId==0){
        throw new Exception("Not logged in");
    }
    User user = userService.getUserById(userId);
    model.addAttribute("username", user.getUsername());
    return "home";
    }

    /*
     * Delete cookie to log out
     */
    @GetMapping({"/logout"})
    public String logout(HttpServletResponse response) {
    Cookie jwtTokenCookie = new Cookie("user-id", "null");
    Cookie nameCookie = new Cookie("username", "null");
    nameCookie.setMaxAge(0);
    jwtTokenCookie.setMaxAge(0);
    response.addCookie(nameCookie);
    response.addCookie(jwtTokenCookie);
    return "login";
    }

    /*
     * Creates new model for user
     */
    @GetMapping("/register")
    public String registrationForm(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    /*
     * Completes registration with input from form
     */
    @PostMapping("/register")
    public String processRegistration(@ModelAttribute("user") User user) {
        userService.registerUser(user);
        return "redirect:/login";
    }
    
        @GetMapping("/settings")
    public String userAccountSettings(Model model, HttpServletRequest request) throws Exception{
        long userId = userService.getUserId(request);
        if(userId==0){
        throw new Exception("Not logged in");
    }
    User user = userService.getUserById(userId);
    model.addAttribute("username", user.getUsername());

        return "settings";
    }

     @PostMapping("/change-password")
    public String processChangePassword(@ModelAttribute("user") User user, HttpServletRequest request) {
        long userId = userService.getUserId(request);
        User existingUser = userService.getUserById(userId);
        if (!existingUser.getPassword().equals(user.getPassword())){
            return "error";
        }
       
        userService.registerUser(existingUser);
        return "redirect:/dashboard";
    }
    @PostMapping("/change-household-name")
    public String processChangeHouseholdName(@ModelAttribute("household") Household household, @RequestParam String newHouseholdName, HttpServletRequest request) {
        long userId = userService.getUserId(request);
        User existingUser = userService.getUserById(userId);
        existingUser.getHousehold().setName(newHouseholdName);
        householdService.save(household);
        userService.registerUser(existingUser);

        return "redirect:/dashboard";
    }  


    

    
}
