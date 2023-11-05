
package org.wecancodeit.hometask;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.wecancodeit.hometask.Models.Household;
import org.wecancodeit.hometask.Models.HouseholdMember;
import org.wecancodeit.hometask.Models.Reward;
import org.wecancodeit.hometask.Models.Task;
import org.wecancodeit.hometask.Models.User;
import org.wecancodeit.hometask.Services.HouseholdMemberService;
import org.wecancodeit.hometask.Services.HouseholdService;
import org.wecancodeit.hometask.Services.RewardService;
import org.wecancodeit.hometask.Services.TaskService;
import org.wecancodeit.hometask.Services.UserService;

@Component
public class Populator implements CommandLineRunner {

        private final HouseholdService householdService;
        private final TaskService taskService;
        private final HouseholdMemberService householdMemberService;
        private final UserService userService;
        private final RewardService rewardService;

        public Populator(HouseholdService householdService, TaskService taskService, UserService userService,
                        HouseholdMemberService householdMemberService, RewardService rewardService) {
                this.householdService = householdService;
                this.taskService = taskService;
                this.householdMemberService = householdMemberService;
                this.userService = userService;
                this.rewardService = rewardService;
        }

        @Override
        public void run(String... args) throws Exception {

                byte[] defaultProfile = "src\\main\\resources\\static\\profile-pictures\\profile.png".getBytes();
                // Creating default user and admin accounts
                User admin = new User("admin", "password", "admin"
                                );
                User user = new User("user", "user", "user"
                                );
                userService.registerUser(admin);
                userService.registerUser(user);

                // Creating Household Family Details /Add another household user
                Household family_1 = new Household("Family #1", admin, 100,defaultProfile);
                householdService.save(family_1);
                Household family_2 = new Household("Family #2", user, 100,defaultProfile);
                householdService.save(family_2);

                // Creating Family Member Details
                HouseholdMember HHmember_1 = new HouseholdMember("Marcus G", family_1, 25, 2, 27, "/images/Marcus.jpg");
                householdMemberService.save(HHmember_1);
                HouseholdMember HHmember_2 = new HouseholdMember("Tim A", family_1, 15, 1, 16,"/images/Tim.jpg");
                householdMemberService.save(HHmember_2);
                HouseholdMember HHmember_3 = new HouseholdMember("Rukshan K", family_1, 18, 3, 21,"/images/Rukshan.png");
                householdMemberService.save(HHmember_3);
                HouseholdMember HHmember_4 = new HouseholdMember("Esperanza R", family_1, 21, 2, 23,"/images/Esperanza.jpg");
                householdMemberService.save(HHmember_4);
                HouseholdMember HHmember_5 = new HouseholdMember("Lukretia D", family_1, 15, 2, 17,"/images/Lukretia.jpg");
                householdMemberService.save(HHmember_5);
                HouseholdMember HHmember_6 = new HouseholdMember("Martial O", family_1, 20, 1, 21,"/images/Martial.jpg");
                householdMemberService.save(HHmember_6);
                // HouseholdMember HHmember_7 = new HouseholdMember("John M", family_1, 16, 2, 18);
                // householdMemberService.save(HHmember_7);
                // HouseholdMember HHmember_8 = new HouseholdMember("Taylour H", family_1, 14, 3, 17);
                // householdMemberService.save(HHmember_8);
                // HouseholdMember HHmember_9 = new HouseholdMember("Sarah J", family_1, 12, 1, 13);
                // householdMemberService.save(HHmember_9);
                // HouseholdMember HHmember_10 = new HouseholdMember("Bob S", family_1, 22, 2, 24);
                // householdMemberService.save(HHmember_10);

                // Creating Rewards
                Reward fiveDollars = new Reward("Five dollars", 5, false, null, family_1);
                Reward threeDollars = new Reward("Three dollars", 3, false, null, family_1);
                Reward oneDollar = new Reward("One dollars", 1, false, null, family_1);
                Reward fiftyCents = new Reward("Fifty Cents", .50, false, null, family_1);
                Reward iceCream = new Reward("Ice cream", 0, true, "ice cream", family_1);
                Reward none = new Reward("No reward", 0, true, "none", family_1);
                rewardService.save(none);
                rewardService.save(iceCream);
                rewardService.save(threeDollars);
                rewardService.save(fiveDollars);
                rewardService.save(oneDollar);
                rewardService.save(fiftyCents);


                // Creating a main task1
                // Task task_01 = new Task("School", "main task_01", List.of(fiveDollars, none),
                // 0, HHmember_1, family_1, false,
                // true,
                // 5.0, LocalDate.now(), LocalDate.now().plusDays(0),
                // false, // identify start and end dates active //we can enactive datetime
                // using this
                // // flag
                // false, 0, // keep rewarded value against task
                // LocalDate.now());

                // Creating a main task between days
                // Task task_02 = new Task("School", "main task_02", List.of(fiveDollars,
                // iceCream), 0, HHmember_1, family_2,true,true,
                // true,
                // null, LocalDate.now(), LocalDate.now().plusDays(2), true, false, 0,
                // LocalDate.now());

                // // Creating a sub task
                // Task task_03 = new Task("School", "sub task on main task_01 ",
                // List.of(fiveDollars, none), 1, HHmember_1,
                // family_1, false, false,
                // null, LocalDate.now(), LocalDate.now().plusDays(0), true, false, 0,
                // LocalDate.now());

                Task doDishes = new Task("Do dishes", "Wash and dry the dishes", fiftyCents, 0, HHmember_1,
                                family_1,
                                true, false, null, LocalDate.now(), LocalDate.now().plusDays(0), true, false, true, 0,
                                LocalDate.now());
                Task cleanRoom = new Task("Clean room", "Clean and organize your room", threeDollars, 0,
                                HHmember_1, family_1,
                                false, false, null, LocalDate.now(), LocalDate.now().plusDays(0), true, false, true, 0,
                                LocalDate.now());
                Task mowLawn = new Task("Mow the lawn", "Only mow the front yard.", fiveDollars, 0, HHmember_1,
                                family_1,
                                true, false, null, LocalDate.now(), LocalDate.now().plusDays(0), true, true, false, 0,
                                LocalDate.now());
                Task feedDog = new Task("Walk the Dog", "Take the dog out for a walk.Take him to his favorite park.",
                                oneDollar, 0, HHmember_1, family_1,
                                false, false, null, LocalDate.now(), LocalDate.now().plusDays(0), true, true, false, 0,
                                LocalDate.now());

                Task doLaundry = new Task("Do Laundry", "Wash the towels and socks.", oneDollar, 0, HHmember_2,
                                family_1,
                                true, false, null, LocalDate.now(), LocalDate.now().plusDays(0), true, false, true, 0,
                                LocalDate.now());
                Task takeOutTrash = new Task("Take out Trash", "Take the trash out in the kitchen.", fiftyCents,
                                0, HHmember_2, family_1,
                                false, false, null, LocalDate.now(), LocalDate.now().plusDays(0), true, false, true, 0,
                                LocalDate.now());
                Task pickUpDog = new Task("Pick up the dog", "Pick up the dog from the groomers", iceCream, 0,
                                HHmember_2,
                                family_1,
                                true, false, null, LocalDate.now(), LocalDate.now().plusDays(0), true, true, false, 0,
                                LocalDate.now());
                Task rakeLeaves = new Task("Rake the leaves", "Rake the leaves in the backyard.",
                                threeDollars, 0, HHmember_2, family_1,
                                false, false, null, LocalDate.now(), LocalDate.now().plusDays(0), true, true, false, 0,
                                LocalDate.now());

                Task payBill = new Task("Pay the phone bill",
                                "Go to the phone store and pay the bill. Ask an employee about any addition discounts on the phone plan.",
                                threeDollars, 0, HHmember_3,
                                family_1,
                                true, false, null, LocalDate.now(), LocalDate.now().plusDays(0), true, true, false, 0,
                                LocalDate.now());
                Task cookMeal = new Task("Cook Dinner",
                                "Cook a delicious meal with the ingredients left in the refrigerator.",
                                oneDollar,
                                0, HHmember_3, family_1,
                                false, false, null, LocalDate.now(), LocalDate.now().plusDays(0), true, false, true, 0,
                                LocalDate.now());
                Task washCar = new Task("Wash the car", "Wash the car, clean the mats and seats.", fiveDollars,
                                0,
                                HHmember_3,
                                family_1,
                                true, false, null, LocalDate.now(), LocalDate.now().plusDays(0), true, true, false, 0,
                                LocalDate.now());
                Task pickBook = new Task("Pick up the books", "Pick up the books at the library.",
                                fiftyCents, 0, HHmember_3, family_1,
                                false, false, null, LocalDate.now(), LocalDate.now().plusDays(0), true, true, false, 0,
                                LocalDate.now());

                Task cleanBathroom = new Task("Clean Bathroom",
                                "Clean the bathroom and make sure it smells great!",
                                threeDollars, 0, HHmember_4,
                                family_1,
                                true, false, null, LocalDate.now(), LocalDate.now().plusDays(0), true, false, true, 0,
                                LocalDate.now());
                Task replaceBulb = new Task("Replace light bulbs",
                                "Replace the light bulbs in the hallway and bathroom",
                                fiftyCents,
                                0, HHmember_4, family_1,
                                false, false, null, LocalDate.now(), LocalDate.now().plusDays(0), true, false, true, 0,
                                LocalDate.now());
                Task vetAppointment = new Task("Vet appointment reminder",
                                "Don't forget to take the cat to their vet appoint at 3p. ", iceCream,
                                0,
                                HHmember_4,
                                family_1,
                                true, false, null, LocalDate.now(), LocalDate.now().plusDays(0), true, true, false, 0,
                                LocalDate.now());
                Task waterGarden = new Task("Water the garden", "Water the garden and weed it, if needed.",
                                threeDollars, 0, HHmember_4, family_1,
                                false, false, null, LocalDate.now(), LocalDate.now().plusDays(0), true, true, false, 0,
                                LocalDate.now());

                Task groceries = new Task("Get groceries", "Go anf pick up the groceries from the store",
                                oneDollar, 0, HHmember_5,
                                family_1,
                                true, false, null, LocalDate.now(), LocalDate.now().plusDays(0), true, true, false, 0,
                                LocalDate.now());
                Task fixTable = new Task("Fix the table", "Fix the table in the dinning room", oneDollar, 0,
                                HHmember_5, family_1,
                                false, false, null, LocalDate.now(), LocalDate.now().plusDays(0), true, false, true, 0,
                                LocalDate.now());
                Task sortMail = new Task("Sort Mail",
                                "Sort out all the mail that is sitting on the table in the living area.",
                                fiftyCents, 0, HHmember_5,
                                family_1,
                                true, false, null, LocalDate.now(), LocalDate.now().plusDays(0), true, true, false, 0,
                                LocalDate.now());
                Task cleanGutters = new Task("Clean the Gutters", "Clean the gutters out.",
                                fiveDollars, 0, HHmember_5, family_1,
                                false, false, null, LocalDate.now(), LocalDate.now().plusDays(0), true, true, false, 0,
                                LocalDate.now());

                Task cleanWindows = new Task("Clean Windows", "Clean the window at the front of the house.", oneDollar, 0, HHmember_6,
                                family_1,
                                true, false, null, LocalDate.now(), LocalDate.now().plusDays(0), true, false, true, 0,
                                LocalDate.now());
                Task moveBoxes = new Task("Move Boxes", "Move the 3 boxes from the living area to the basement.", threeDollars, 0,
                                HHmember_6, family_1,
                                false, false, null, LocalDate.now(), LocalDate.now().plusDays(0), true, true, false, 0,
                                LocalDate.now());
                Task unclogSink = new Task("Unclog Sink", "Unclog the sink in the bathroom.", fiveDollars, 0, HHmember_6,
                                family_1,
                                true, false, null, LocalDate.now(), LocalDate.now().plusDays(0), true, true, false, 0,
                                LocalDate.now());
                Task feedFish = new Task("Feed Fish", "Feed the fish the new fish food..",
                                fiftyCents, 0, HHmember_6, family_1,
                                false, false, null, LocalDate.now(), LocalDate.now().plusDays(0), true, true, false, 0,
                                LocalDate.now());


                // // Assign rewards to tasks
                // task_01.setRewards(List.of(fiveDollars, iceCream));
                // task_02.setRewards(List.of(fiveDollars, none));

                // taskService.save(task_02);
                // taskService.save(task_03);
                taskService.save(doDishes);
                taskService.save(cleanRoom);
                taskService.save(mowLawn);
                taskService.save(feedDog);
                taskService.save(doLaundry);
                taskService.save(takeOutTrash);
                taskService.save(pickUpDog);
                taskService.save(rakeLeaves);
                taskService.save(payBill);
                taskService.save(cookMeal);
                taskService.save(washCar);
                taskService.save(pickBook);
                taskService.save(cleanBathroom);
                taskService.save(replaceBulb);
                taskService.save(vetAppointment);
                taskService.save(waterGarden);
                taskService.save(groceries);
                taskService.save(fixTable);
                taskService.save(sortMail);
                taskService.save(cleanGutters);
                taskService.save(cleanWindows);
                taskService.save(moveBoxes);
                taskService.save(unclogSink);
                taskService.save(feedFish);
        }

}
