package domain;

import data.SwimmingClubMember;
import data.Filehandler;
import ui.Color;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Database {
    ArrayList<SwimmingClubMember> members = new ArrayList<>();
    ArrayList<SwimmingClubMember> topFiveTimeCrawl = new ArrayList<>();
    ArrayList<SwimmingClubMember> topFiveTimeBackCrawl = new ArrayList<>();
    ArrayList<SwimmingClubMember> topFiveTimeButterfly = new ArrayList<>();
    ArrayList<SwimmingClubMember> topFiveTimeBreaststroke = new ArrayList<>();

    Filehandler filehandler = new Filehandler();
    Color color = new Color();

    public void addMember(String name, int age, boolean subscriptionActive, String ageGroup, String exerciseType, String activeDiscipline, boolean displaySubscriptionFeesAndPaymentStatus) {
        if (exerciseType.equalsIgnoreCase("competitive")) {
            SwimmingClubMember member = new SwimmingClubMember(name, age, subscriptionActive, ageGroup, exerciseType, activeDiscipline, false);
            members.add(member);
        } else {
            SwimmingClubMember member = new SwimmingClubMember(name, age, subscriptionActive, ageGroup, exerciseType, "", false);
            members.add(member);
        }
    }

    public double checkSubscription() {
        double yearlyIncome = 0;
        for (SwimmingClubMember member : members) {
            if (member.getAge() < 18) {
                yearlyIncome += 1000;
            }
            if (member.getAge() >= 18 && member.getAge() < 60) {
                yearlyIncome += 1600;
            }
            if (member.getAge() >= 60) {
                yearlyIncome += 1600 * 0.75;
            }
        }
        return yearlyIncome;
    }

    public double calculateSubscriptionFee(SwimmingClubMember member) {
        double fee;

        if (member.getSubscriptionActive()) {
            if (member.getAge() < 18) {
                fee = 1000;
            } else if (member.getAge() >= 18 && member.getAge() < 60) {
                fee = 1600;
            } else {
                fee = 1600 * 0.75;
            }
        } else {
            fee = 500;
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

    public void addSwimmingResult(String name, String swimmingDate, double swimmingResult, String event, int placement) {

        ArrayList<SwimmingClubMember> foundMembers = searchMember(name);

        if (!foundMembers.isEmpty()) {
            SwimmingClubMember selectedMember = foundMembers.get(0);
            selectedMember.setSwimmingDate(swimmingDate);
            selectedMember.setSwimmingResult(swimmingResult);
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

    public void updatePaymentStatus(String memberName, boolean newPaymentStatus) {
        ArrayList<SwimmingClubMember> foundMembersToUpdate = findMemberName(memberName);

        if (!foundMembersToUpdate.isEmpty()) {
            SwimmingClubMember memberToUpdate = foundMembersToUpdate.get(0);
            memberToUpdate.setPaymentStatus(newPaymentStatus);
            System.out.println("Payment status updated for " + memberToUpdate.getName() + ": " + (newPaymentStatus ? "Paid" : "Not Paid"));
        } else {
            System.out.println("Member not found. Please make sure the name is correct.");
        }
    }

    public void displaySubscriptionFeesAndPaymentStatus() {
        System.out.println("Subscription Fees and Payment Status:");

        double totalSubscriptionFees = 0.0;

        for (SwimmingClubMember member : members) {
            double memberFee = calculateSubscriptionFee(member);
            totalSubscriptionFees += memberFee;
            String paymentStatus = member.getPaymentStatus() ? "Paid" : "Not Paid";
            System.out.println(member.getName() + ": " + memberFee + " DKK - Payment Status: " + paymentStatus);
        }

        System.out.println("Total: " + totalSubscriptionFees + " DKK");
    }


    public ArrayList<SwimmingClubMember> sortByTimeCrawl() {
        ArrayList<SwimmingClubMember> competitiveMembersCrawl = new ArrayList<>();
        for (SwimmingClubMember member : members) {
            if (member.getExerciseType().equals("competitive")) {
                if (member.getActiveDiscipline().equals("crawl")) {
                    competitiveMembersCrawl.add(member);
                }
            }
        }
        Collections.sort(competitiveMembersCrawl, Comparator.comparingDouble(SwimmingClubMember::getSwimmingResult));

        topFiveTimeCrawl.clear();

        int numMembers = Math.min(5, competitiveMembersCrawl.size());
        for (int i = 0; i < numMembers; i++) {
            topFiveTimeCrawl.add(competitiveMembersCrawl.get(i));
        }
        return topFiveTimeCrawl;

    }

    public void printTop5Crawl() {
        sortByTimeCrawl();
            if (!topFiveTimeCrawl.isEmpty()) {
                for (SwimmingClubMember member : topFiveTimeCrawl) {
                    System.out.println(member + " tid: " + color.ANSI_GREEN +member.getSwimmingResult() + color.ANSI_RESET);
                }
            } else {
                System.out.println(color.ANSI_RED + "There are not enough members in the system yet. \nPress 1 to add a member." + color.ANSI_RESET);
            }
        }

    public ArrayList<SwimmingClubMember> sortByTimeBackCrawl() {
        ArrayList<SwimmingClubMember> competitiveMembersBackCrawl = new ArrayList<>();
        for (SwimmingClubMember member : members) {
            if (member.getExerciseType().equals("competitive") && member.getActiveDiscipline().equals("back crawl")) {
                competitiveMembersBackCrawl.add(member);
            }
        }

        Collections.sort(competitiveMembersBackCrawl, Comparator.comparingDouble(SwimmingClubMember::getSwimmingResult));
        topFiveTimeBackCrawl.clear();

        int numMembers = Math.min(5, competitiveMembersBackCrawl.size());
        for (int i = 0; i < numMembers; i++) {
            topFiveTimeBackCrawl.add(competitiveMembersBackCrawl.get(i));
        }
        return topFiveTimeBackCrawl;
    }

    public void printTop5BackCrawl() {
        sortByTimeBackCrawl();
        if (!topFiveTimeBackCrawl.isEmpty()) {
            for (SwimmingClubMember member : topFiveTimeBackCrawl) {
                System.out.println(member + " tid: " + color.ANSI_GREEN +member.getSwimmingResult() + color.ANSI_RESET);
            }
        } else {
            System.out.println(color.ANSI_RED + "There are not enough members in the system yet. \nPress 1 to add a member." + color.ANSI_RESET);
        }
    }


    public ArrayList<SwimmingClubMember> sortByTimeButterfly() {
        ArrayList<SwimmingClubMember> competitiveMembersButterfly = new ArrayList<>();
        for (SwimmingClubMember member : members) {
            if (member.getExerciseType().equals("competitive") && member.getActiveDiscipline().equals("butterfly")) {
                competitiveMembersButterfly.add(member);
            }
        }

        Collections.sort(competitiveMembersButterfly, Comparator.comparingDouble(SwimmingClubMember::getSwimmingResult));

        topFiveTimeButterfly.clear();

        int numMembers = Math.min(5, competitiveMembersButterfly.size());
        for (int i = 0; i < numMembers; i++) {
            topFiveTimeButterfly.add(competitiveMembersButterfly.get(i));
        }
        return topFiveTimeButterfly;
    }
    public void printTop5Buttefly() {
        sortByTimeButterfly();
        if (!topFiveTimeButterfly.isEmpty()) {
            for (SwimmingClubMember member : topFiveTimeButterfly) {
                System.out.println(member + " tid: " + color.ANSI_GREEN + member.getSwimmingResult() + color.ANSI_RESET);
            }
        } else {
            System.out.println(color.ANSI_RED + "There are not enough members in the system yet. \nPress 1 to add a member." + color.ANSI_RESET);
        }

    }
    public ArrayList<SwimmingClubMember> sortByTimeBreaststroke() {
        ArrayList<SwimmingClubMember> competitiveMembersBreaststroke = new ArrayList<>();
        for (SwimmingClubMember member : members) {
            if (member.getExerciseType().equals("competitive") && member.getActiveDiscipline().equals("breaststroke")) {
                competitiveMembersBreaststroke.add(member);
            }
        }

        Collections.sort(competitiveMembersBreaststroke, Comparator.comparingDouble(SwimmingClubMember::getSwimmingResult));


        topFiveTimeBreaststroke.clear();

        int numMembers = Math.min(5, competitiveMembersBreaststroke.size());
        for (int i = 0; i < numMembers; i++) {
            topFiveTimeBreaststroke.add(competitiveMembersBreaststroke.get(i));
        }
        return topFiveTimeBreaststroke;
    }
    public void printTop5Breaststroke() {
        sortByTimeBreaststroke();
        if (!topFiveTimeBreaststroke.isEmpty()) {
            for (SwimmingClubMember member : topFiveTimeBreaststroke) {
                System.out.println(member + " tid: " + color.ANSI_GREEN +member.getSwimmingResult() + color.ANSI_RESET);
            }
        } else {
            System.out.println(color.ANSI_RED + "There are not enough members in the system yet. \nPress 1 to add a member." + color.ANSI_RESET);
        }



    }





}
