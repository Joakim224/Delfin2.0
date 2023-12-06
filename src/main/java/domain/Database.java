package domain;

import data.SwimmingClubMember;
import data.Filehandler;
import ui.Color;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class Database {
    ArrayList<SwimmingClubMember> members = new ArrayList<>();
    Filehandler filehandler = new Filehandler();
    Color color = new Color();

    public void addMember(String name, int age, boolean subscriptionActive, String ageGroup, String exerciseType, String activeDiscipline, boolean displaySubscriptionFeesAndPaymentStatus) {
        members.add(new SwimmingClubMember(name, age, subscriptionActive, ageGroup, exerciseType, activeDiscipline, displaySubscriptionFeesAndPaymentStatus));
    }

    public double checkSubscription() {
        double yearlyIncome = 0;
        for (SwimmingClubMember member : members) {
            if (member.getAge() < 18) {
                yearlyIncome += 1000;
            } if (member.getAge() >= 18 && member.getAge() < 60) {
                yearlyIncome += 1600;
            } if (member.getAge() >= 60) {
                yearlyIncome += 1600*0.75;
            }
        }
        return yearlyIncome;
    }

    public double calculateSubscriptionFee(SwimmingClubMember member) {
        double fee;

        if (member.getAge() < 18) {
            fee = 1000;
        } else if (member.getAge() >= 18 && member.getAge() < 60) {
            fee = 1600;
        } else {
            fee = 1600 * 0.75;
        }

        return fee;
    }

    public void printMembers() {
        if (!members.isEmpty()) {
            for (SwimmingClubMember member : members) {
                System.out.println(member);
            }
        } else {
            System.out.println(color.ANSI_RED + "There are no members in the system yet. \nPress 1 to add a member." + color.ANSI_RESET);
        }
    }

    public ArrayList<SwimmingClubMember> getMembers() {
        return members;
    }

    public ArrayList<SwimmingClubMember> findMemberName(String memberName) {
        ArrayList<SwimmingClubMember> foundMembers = new ArrayList<>();
        for (SwimmingClubMember member : members) {
            if (member.getName().equalsIgnoreCase(memberName)) {
                foundMembers.add(member);
            }
        }
        return foundMembers;
    }

    public ArrayList<SwimmingClubMember> searchMember(String memberName) {
        return findMemberName(memberName);
    }

    public void addSwimmingResult(String name, String swimmingResultDateTime, String event, int placement) {

        ArrayList<SwimmingClubMember> foundMembers = searchMember(name);

        if (!foundMembers.isEmpty()) {
            SwimmingClubMember selectedMember = foundMembers.get(0);

            selectedMember.setSwimmingResultDateTime(swimmingResultDateTime);
            selectedMember.setEvent(event);
            selectedMember.setPlacement(placement);
        }
    }

    public void splitAndPrintCompetitiveSwimmers(ArrayList<SwimmingClubMember> members) {
        ArrayList<SwimmingClubMember> competitiveSwimmersUnder18 = new ArrayList<>();
        ArrayList<SwimmingClubMember> competitiveSwimmers18AndAbove = new ArrayList<>();

        for (SwimmingClubMember member : members) {
            if (member.getExerciseType().equalsIgnoreCase("competitive")) {
                if (member.getAge() >= 18) {
                    competitiveSwimmers18AndAbove.add(member);
                } else {
                    competitiveSwimmersUnder18.add(member);
                }
            }
        }

        System.out.println("Competitive swimmers under 18: ");
        for (SwimmingClubMember under18 : competitiveSwimmersUnder18) {
            System.out.println("Name: " + under18.getName() +
                    ", Age: " + under18.getAge() +
                    ", Exercise type: " + under18.getExerciseType() +
                    ", Active discipline: " + under18.getActiveDiscipline());
        }

        System.out.println("\nCompetitive swimmers 18 and above: ");
        for (SwimmingClubMember plus18 : competitiveSwimmers18AndAbove) {
            System.out.println("Name: " + plus18.getName() +
                    ", Age: " + plus18.getAge() +
                    ", Exercise type: " + plus18.getExerciseType() +
                    ", Active discipline: " + plus18.getActiveDiscipline());
        }
    }

    public void loadData() {
        try {
            filehandler.loadMemberData(members);
            System.out.println(color.ANSI_GREEN + "CSV file successfully loaded." + color.ANSI_RESET);
        } catch (FileNotFoundException e) {
            System.out.println(color.ANSI_RED + "File not found." + color.ANSI_RESET);
        }
    }

    public void saveData() {
        try {
            filehandler.saveMemberData(members);
            System.out.println(color.ANSI_GREEN + "CSV file successfully saved in system." + color.ANSI_RESET);
        } catch (FileNotFoundException e) {
            System.out.println(color.ANSI_GREEN + "File not found." + color.ANSI_RESET);
        }
    }

    public void displaySubscriptionFeesAndPaymentStatus() {
        System.out.println("Subscription Fees and Payment Status:");

        double totalSubscriptionFees = 0.0;

        for (SwimmingClubMember member : members) {
            double memberFee = calculateSubscriptionFee(member);
            totalSubscriptionFees += memberFee;
            String paymentStatus = member.getSubscriptionActive() ? "Paid" : "Not Paid";
            System.out.println(member.getName() + ": " + memberFee + " DKK - Payment Status: " + paymentStatus);
        }

        System.out.println("Total: " + totalSubscriptionFees + " DKK");
    }
}