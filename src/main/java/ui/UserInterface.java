package ui;

import data.SwimmingClubMember;
import domain.Controller;
import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface {
    private int age;
    Controller controller = new Controller();
    Scanner scanner = new Scanner(System.in);
    Color color = new Color();

    public void welcomeMessage() {
        System.out.println();
        System.out.println(
                "Welcome to the swimclub-database (Delfin)\n" + "\u2500".repeat(50) + "\n" +
                        "Interact with the menu by inputting the corresponding number\n" +
                        "(1) Add member\n" +
                        "(2) Print all members\n" +
                        "(3) Print revenue\n" +
                        "(4) Swimming Result\n" +
                        "(5) Order competitive swimmers by age and discipline\n" +
                        "(6) Update Subsribtion status\n" +
                        "(7)\n" +
                        "(8)\n" +
                        "(9) EXIT\n" + "\u2500".repeat(50)
                );
    }

    public void startProgram() {
        boolean run = true;
        String input;
        controller.loadData();

        do {

            welcomeMessage();
            System.out.print("Input: ");
            input = scanner.nextLine().trim().toLowerCase();
            String[] commands = input.split("\\s+");
            String command = commands[0];

            switch (command) {
                case "1", "one" -> {
                    System.out.println("Input the members name");
                    System.out.print("Name: ");
                    String name = scanner.nextLine();

                    boolean correctInputForAge = true;
                    do {
                        System.out.println("Input the members age");
                        System.out.print("Age: ");
                        if (scanner.hasNextInt()) {
                            age = scanner.nextInt();
                            scanner.nextLine();
                            correctInputForAge = true;
                        } else {
                            System.out.println(color.ANSI_RED + "You need to input a number" + color.ANSI_RESET);
                            System.out.println(color.ANSI_RED + "Try again: " + color.ANSI_RESET);
                            scanner.nextLine();
                            correctInputForAge = false;
                        }
                    } while (!correctInputForAge);

                    String ageGroup;
                    if (age < 18) {
                        ageGroup = "Junior";
                    } else {
                        ageGroup = "Senior";
                    }

                    String subscriptionString;
                    boolean subscriptionStatus = false;
                    boolean validSubscriptionStatus = true;
                    do {
                        validSubscriptionStatus = true;
                        System.out.println("Input subscription status (ACTIVE/PASSIVE)");
                        System.out.print("Status: ");

                        subscriptionString = scanner.nextLine().trim().toLowerCase();
                        if (subscriptionString.equals("active")) {
                            subscriptionStatus = true;
                        } else if (subscriptionString.equals("passive")) {
                            subscriptionStatus = false;
                        } else {
                            System.out.println(color.ANSI_RED + "Invalid input" + color.ANSI_RESET);
                            validSubscriptionStatus = false;
                        }
                    } while (!validSubscriptionStatus);

                    String exerciseType;
                    boolean validType = true;
                    String activeDiscipline = "";
                    do {
                        validType = true;
                        System.out.println("Input your exercise type ('Regular' or 'Competitive')");
                        exerciseType = scanner.nextLine().trim().toLowerCase();
                        if (exerciseType.equals("regular") || exerciseType.equals("competitive")) {

                        } else {
                            System.out.println(color.ANSI_RED + "Invalid input" + color.ANSI_RESET);
                            validType = false;
                        }
                    } while (!validType);

                        if (exerciseType.equals("competitive")) {
                            boolean validDiscipline = true;
                            do {
                                validDiscipline = true;
                                System.out.println("Input the discipline you're most active in: ('Crawl', 'Back crawl', 'Butterfly' or 'Breaststroke)");
                                activeDiscipline = scanner.nextLine().trim().toLowerCase();
                                if (activeDiscipline.equals("crawl") || activeDiscipline.equals("back crawl") || activeDiscipline.equals("butterfly") || activeDiscipline.equals("breaststroke")) {

                                } else {
                                    System.out.println(color.ANSI_RED + "Invalid input" + color.ANSI_RESET);
                                    validDiscipline = false;
                                }
                            } while (!validDiscipline);
                        }

                    boolean displaySubscriptionFeesAndPaymentStatus = true;
                    controller.addMember(name, age, subscriptionStatus, ageGroup, exerciseType, activeDiscipline, displaySubscriptionFeesAndPaymentStatus);

                    System.out.println(color.ANSI_GREEN + "Swimming member added" + color.ANSI_RESET);
                    System.out.println("\u2500".repeat(50) + " ");
                }

                case "2", "two" -> {
                    System.out.println("Printing:");
                    controller.printMembers();
                }

                case "3", "three" -> {
                    controller.displaySubscriptionFeesAndPaymentStatus();
                }

                case "4", "four" -> {
                    System.out.println("Adding Swimming Result:");
                    System.out.print("Input the member's name: ");
                    String memberName = scanner.nextLine();

                    ArrayList<SwimmingClubMember> foundMembers = controller.searchMember(memberName);

                    if (foundMembers.isEmpty()) {
                        System.out.println(color.ANSI_RED + "Member not found. Please make sure the name is correct." + color.ANSI_RESET);
                    } else {

                        SwimmingClubMember selectedMember = foundMembers.get(0);

                        System.out.print("Input the swimming result date (yyyy-MM-dd): ");
                        String dateString = scanner.nextLine();

                        System.out.print("Input the swimming result time (HH:mm): ");
                        String timeString = scanner.nextLine();

                        double result = convertToDouble(timeString);

                        double swimmingResult = result;

                        System.out.print("Input the event (if competitive, otherwise leave empty): ");
                        String event = scanner.nextLine();

                        int placement = 0;
                        if (!event.isEmpty()) {
                            System.out.print("Input the placement: ");
                            placement = scanner.nextInt();
                            scanner.nextLine();
                        }

                        controller.addSwimmingResult(selectedMember.getName(),dateString, swimmingResult, event, placement);
                        System.out.println(color.ANSI_GREEN + "Swimming result added for " + selectedMember.getName() + color.ANSI_RESET);
                        System.out.println("\u2500".repeat(50) + " ");
                    }
                }

                case "5", "five" -> {
                    controller.competitiveSwimmersSplit();
                }

                case "6", "six" -> {
                    System.out.println("Update Subscription Status:");
                    System.out.print("Input the member's name: ");
                    String memberNameToUpdate = scanner.nextLine();

                    System.out.print("Input the new subscription status ('active' or 'passive'): ");
                    String newSubscriptionStatusString = scanner.nextLine().trim().toLowerCase();

                    boolean isValidStatus = newSubscriptionStatusString.equals("active") || newSubscriptionStatusString.equals("passive");

                    if (isValidStatus) {
                        boolean newSubscriptionStatus = newSubscriptionStatusString.equals("active");
                        controller.updateSubscriptionStatus(memberNameToUpdate, newSubscriptionStatus);
                    } else {
                        System.out.println(color.ANSI_RED + "Invalid input. Subscription status remains unchanged." + color.ANSI_RESET);
                    }

                    System.out.println("\u2500".repeat(50) + " ");

                }
                case "7", "seven" -> {
                    System.out.println("Select the discipline you want to see top 5 times from:");
                    System.out.println(
                            "\u2500".repeat(50) + "\n" +
                                    "Interact with the menu by inputting the corresponding number\n" +
                                    "(1) Crawl\n" +
                                    "(2) Backcrawl\n" +
                                    "(3) Butterfly\n" +
                                    "(4) Breaststroke\n" +
                                    "\u2500".repeat(50));

                    // Use a different variable for the nested switch
                    String disciplineCommand = scanner.nextLine().trim().toLowerCase();

                    switch (disciplineCommand) {
                        case "1", "one" -> {
                            controller.printTop5Crawl();
                        }
                        case "2", "two" -> {
                            controller.printTop5BackCrawl();
                        }
                        case "3", "three" -> {
                            controller.printTop5Butterfly();
                        }
                        case "4", "four" -> {
                            controller.printTop5Breaststroke();
                        }
                        default -> {
                            System.out.println(color.ANSI_RED + "Wrong input, try again." + color.ANSI_RESET);
                        }
                    }


                }

                case "9", "nine" -> {
                    controller.saveData();
                    System.exit(0);
                }

            }
        } while (run);
    }
    private static double convertToDouble(String timeString) {
        String[] parts = timeString.split(":");
        int minutes = Integer.parseInt(parts[0]);
        int seconds = Integer.parseInt(parts[1]);

        double result = minutes + (seconds / 60.0);
        return result;
    }
}