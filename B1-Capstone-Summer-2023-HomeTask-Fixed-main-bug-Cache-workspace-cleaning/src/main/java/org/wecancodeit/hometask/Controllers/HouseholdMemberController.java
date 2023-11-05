package org.wecancodeit.hometask.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.wecancodeit.hometask.Models.*;
import org.wecancodeit.hometask.Services.*;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class HouseholdMemberController {

    @Resource
    private HouseholdService householdService;
    @Resource
    private final HouseholdMemberService memberService;
    @Resource
    private final UserService userService;

    public HouseholdMemberController(HouseholdMemberService memberService, UserService userService,
            HouseholdService householdService) {
        this.memberService = memberService;
        this.userService = userService;
        this.householdService = householdService;
    }

    @GetMapping("/members/")
    public String collectAllMembers(Model model) {
        model.addAttribute("members", memberService.retrieveAllMembers());
        return "memberView";// add household member view
    }

    @RequestMapping("/member/{id}")
    public String collectOneMember(@PathVariable Long id, Model model) throws Exception {
        model.addAttribute("member", memberService.retrieveMemberById(id));
        return "member";// add household member view
    }

    @PostMapping("/addFamilyMember")
    public String newFamilyMember(@ModelAttribute HouseholdMember newMember, HttpServletRequest request, Model model)
            throws Exception {
        long userId = userService.getUserId(request);
        User user = userService.getUserById(userId);
        String name = user.getHousehold().getName();
        Household household = householdService.retrieveHouseholdByName(name);
        newMember.addHousehold(household);
        HouseholdMember familyAdded = memberService.save(newMember);
        model.addAttribute("household", household);
        model.addAttribute("name", familyAdded.getName());// add a new member to household

        return "redirect:/dashboard";
    }

    @PostMapping("/memberNotified")
    public String notifyMember(@ModelAttribute HouseholdMember assignedMember, HttpServletRequest request, Model model)
            throws Exception {
        long userID = userService.getUserId(request);
        User userToNotify = userService.getUserById(userID);
        String person = userToNotify.getHousehold().getName();
        Household house = householdService.retrieveHouseholdByName(person);
        model.addAttribute("house", house);
        return "redirect:/dashboard";
    }

    @GetMapping("/cashAmount")
    public String displayCash(Model model) {
        model.addAttribute("cashAmount");
        return "member";
    }// Cash total for member

    @GetMapping("/amountPaid")
    public String cashEarn(Model model) {
        model.addAttribute("amountPaid");
        return "member";
    } // Amount Paid

    @RequestMapping("/remainingAmount")
    public String remainingCash(Model model) {
        model.addAttribute("remainingAmount");
        return "member";
    } // Amount remaining

    @PostMapping("/pay/{id}")
    public String payAMember(@PathVariable long id, @RequestParam double paymentAmount, Model model,
            HttpServletRequest request) throws Exception {

        HouseholdMember member = memberService.retrieveMemberById(id);
        long userId = userService.getUserId(request);
        User user = userService.getUserById(userId);
        String name = user.getHousehold().getName();
        Household household = householdService.retrieveHouseholdByName(name);
        double amountOwed = member.getAmountRemaining();
        double bank = household.getHouseholdBank();
        double balance = member.getAmountPaid();
        if (household.getHouseholdBank() >= paymentAmount && paymentAmount <= amountOwed) {

            household.setHouseholdBank(bank - paymentAmount);
            member.setAmountRemaining(amountOwed - paymentAmount);
            member.setAmountPaid(balance + paymentAmount);
            memberService.save(member);
            return "redirect:/dashboard";
        } else {
            return "redirect:/dashboard";
        }
    }

    @PostMapping("/clear-balance/{memberId}")
    public String clearBalance(@PathVariable Long memberId) throws Exception {
        HouseholdMember member = memberService.retrieveMemberById(memberId);
        member.setAmountPaid(0);
        memberService.save(member);
        return "redirect:/dashboard";
    }

}
