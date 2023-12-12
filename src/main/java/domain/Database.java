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
            if (member.getExerciseType().equals("competitive") && member.getActiveDiscipline().equals("crawl")) {
                competitiveMembersCrawl.add(member);
            }
        }


        ArrayList<SwimmingClubMember> juniorMembers = new ArrayList<>();
        ArrayList<SwimmingClubMember> seniorMembers = new ArrayList<>();

        for (SwimmingClubMember member : competitiveMembersCrawl) {
            if (member.getAge() < 18) {
                juniorMembers.add(member);
            } else {
                seniorMembers.add(member);
            }
        }


        Collections.sort(juniorMembers, Comparator.comparingDouble(SwimmingClubMember::getSwimmingResult));
        Collections.sort(seniorMembers, Comparator.comparingDouble(SwimmingClubMember::getSwimmingResult));

        topFiveTimeCrawl.clear();
        addTopMembers(topFiveTimeCrawl, juniorMembers);
        addTopMembers(topFiveTimeCrawl, seniorMembers);

        return topFiveTimeCrawl;
    }


    public ArrayList<SwimmingClubMember> sortByTimeBackCrawl() {
        ArrayList<SwimmingClubMember> competitiveMembersBackCrawl = new ArrayList<>();
        for (SwimmingClubMember member : members) {
            if (member.getExerciseType().equals("competitive")) {
                    if (member.getActiveDiscipline().equals("back crawl")) {
                        competitiveMembersBackCrawl.add(member);
                    }
            }
        }


        ArrayList<SwimmingClubMember> juniorMembers = new ArrayList<>();
        ArrayList<SwimmingClubMember> seniorMembers = new ArrayList<>();

        for (SwimmingClubMember member : competitiveMembersBackCrawl) {
            if (member.getAge() < 18) {
                juniorMembers.add(member);
            } else {
                seniorMembers.add(member);
            }
        }


        Collections.sort(juniorMembers, Comparator.comparingDouble(SwimmingClubMember::getSwimmingResult));
        Collections.sort(seniorMembers, Comparator.comparingDouble(SwimmingClubMember::getSwimmingResult));

        topFiveTimeBackCrawl.clear();
        addTopMembers(topFiveTimeBackCrawl, juniorMembers);
        addTopMembers(topFiveTimeBackCrawl, seniorMembers);

        return topFiveTimeBackCrawl;
    }



    public ArrayList<SwimmingClubMember> sortByTimeButterfly() {
        ArrayList<SwimmingClubMember> competitiveMembersButterfly = new ArrayList<>();
        for (SwimmingClubMember member : members) {
            if (member.getExerciseType().equals("competitive") && member.getActiveDiscipline().equals("butterfly")) {
                competitiveMembersButterfly.add(member);
            }
        }

        ArrayList<SwimmingClubMember> juniorMembers = new ArrayList<>();
        ArrayList<SwimmingClubMember> seniorMembers = new ArrayList<>();

        for (SwimmingClubMember member : competitiveMembersButterfly) {
            if (member.getAge() < 18) {
                juniorMembers.add(member);
            } else {
                seniorMembers.add(member);
            }
        }


        Collections.sort(juniorMembers, Comparator.comparingDouble(SwimmingClubMember::getSwimmingResult));
        Collections.sort(seniorMembers, Comparator.comparingDouble(SwimmingClubMember::getSwimmingResult));

        topFiveTimeButterfly.clear();
        addTopMembers(topFiveTimeButterfly, juniorMembers);
        addTopMembers(topFiveTimeButterfly, seniorMembers);

        return topFiveTimeButterfly;
    }

    public ArrayList<SwimmingClubMember> sortByTimeBreaststroke() {
        ArrayList<SwimmingClubMember> competitiveMembersBreaststroke = new ArrayList<>();
        for (SwimmingClubMember member : members) {
            if (member.getExerciseType().equals("competitive") && member.getActiveDiscipline().equals("breaststroke")) {
                competitiveMembersBreaststroke.add(member);
            }
        }

        ArrayList<SwimmingClubMember> juniorMembers = new ArrayList<>();
        ArrayList<SwimmingClubMember> seniorMembers = new ArrayList<>();

        for (SwimmingClubMember member : competitiveMembersBreaststroke) {
            if (member.getAge() < 18) {
                juniorMembers.add(member);
            } else {
                seniorMembers.add(member);
            }
        }


        Collections.sort(juniorMembers, Comparator.comparingDouble(SwimmingClubMember::getSwimmingResult));
        Collections.sort(seniorMembers, Comparator.comparingDouble(SwimmingClubMember::getSwimmingResult));

        topFiveTimeBreaststroke.clear();
        addTopMembers(topFiveTimeBreaststroke, juniorMembers);
        addTopMembers(topFiveTimeBreaststroke, seniorMembers);

        return topFiveTimeBreaststroke;
    }
    private void addTopMembers(ArrayList<SwimmingClubMember> topMembers, ArrayList<SwimmingClubMember> sourceMembers) {
        int numMembers = Math.min(5, sourceMembers.size());
        for (int i = 0; i < numMembers; i++) {
            topMembers.add(sourceMembers.get(i));
        }
    }

    public void printTop5ByCrawl() {
        sortByTimeCrawl();
        printTop5ByCategoryCrawl("Junior", topFiveTimeCrawl);
        printTop5ByCategoryCrawl("Senior", topFiveTimeCrawl);

    }

    public void printTop5ByBackCrawl() {
        sortByTimeBackCrawl();
        printTop5ByCategoryBackCrawl("Junior", topFiveTimeBackCrawl);
        printTop5ByCategoryBackCrawl("Senior", topFiveTimeBackCrawl);
    }

    public void printTop5byButterfly() {
        sortByTimeButterfly();
        printTop5ByCategoryButterfly("Junior", topFiveTimeButterfly);
        printTop5ByCategoryButterfly("Senior", topFiveTimeButterfly);
    }

    public void printTop5ByBreaststroke(){
        sortByTimeBreaststroke();
        printTop5ByCategoryBreaststroke("Junior", topFiveTimeBreaststroke);
        printTop5ByCategoryBreaststroke("Senior", topFiveTimeBreaststroke);
    }


    private void printTop5ByCategoryCrawl(String category, ArrayList<SwimmingClubMember> topFiveMembers) {
        System.out.println("Top 5 " + category + " Swimmers - Crawl:");


        ArrayList<SwimmingClubMember> topFiveInCategoryCrawl = new ArrayList<>();
        for (SwimmingClubMember member : topFiveMembers) {
            if (member.getAgeGroup().equalsIgnoreCase(category)) {
                topFiveInCategoryCrawl.add(member);
            }
        }

        if (!topFiveInCategoryCrawl.isEmpty()) {
            int numMembers = Math.min(5, topFiveInCategoryCrawl.size());
            for (int i = 0; i < numMembers; i++) {
                SwimmingClubMember member = topFiveInCategoryCrawl.get(i);
                System.out.println(member + " tid: " + color.ANSI_GREEN + member.getSwimmingResult() + color.ANSI_RESET);
            }
        } else {
            System.out.println(color.ANSI_RED + "There are not enough members in the " + category + " category. \nPress 1 to add a member." + color.ANSI_RESET);
        }
    }
    private void printTop5ByCategoryBackCrawl(String category, ArrayList<SwimmingClubMember> topFiveMembers) {
        System.out.println("Top 5 " + category + " Swimmers - Back Crawl:");


        ArrayList<SwimmingClubMember> topFiveInCategoryBackCrawl = new ArrayList<>();
        for (SwimmingClubMember member : topFiveMembers) {
            if (member.getAgeGroup().equalsIgnoreCase(category)) {
                topFiveInCategoryBackCrawl.add(member);
            }
        }

        if (!topFiveInCategoryBackCrawl.isEmpty()) {
            int numMembers = Math.min(5, topFiveInCategoryBackCrawl.size());
            for (int i = 0; i < numMembers; i++) {
                SwimmingClubMember member = topFiveInCategoryBackCrawl.get(i);
                System.out.println(member + " tid: " + color.ANSI_GREEN + member.getSwimmingResult() + color.ANSI_RESET);
            }
        } else {
            System.out.println(color.ANSI_RED + "There are not enough members in the " + category + " category. \nPress 1 to add a member." + color.ANSI_RESET);
        }
    }
    private void printTop5ByCategoryButterfly(String category, ArrayList<SwimmingClubMember> topFiveMembers) {
        System.out.println("Top 5 " + category + " Swimmers - Butterfly:");


        ArrayList<SwimmingClubMember> topFiveInCategoryButterfly = new ArrayList<>();
        for (SwimmingClubMember member : topFiveMembers) {
            if (member.getAgeGroup().equalsIgnoreCase(category)) {
                topFiveInCategoryButterfly.add(member);
            }
        }

        if (!topFiveInCategoryButterfly.isEmpty()) {
            int numMembers = Math.min(5, topFiveInCategoryButterfly.size());
            for (int i = 0; i < numMembers; i++) {
                SwimmingClubMember member = topFiveInCategoryButterfly.get(i);
                System.out.println(member + " tid: " + color.ANSI_GREEN + member.getSwimmingResult() + color.ANSI_RESET);
            }
        } else {
            System.out.println(color.ANSI_RED + "There are not enough members in the " + category + " category. \nPress 1 to add a member." + color.ANSI_RESET);
        }
    }
    private void printTop5ByCategoryBreaststroke(String category, ArrayList<SwimmingClubMember> topFiveMembers) {
        System.out.println("Top 5 " + category + " Swimmers - Breaststroke:");


        ArrayList<SwimmingClubMember> topFiveInCategoryBreaststroke = new ArrayList<>();
        for (SwimmingClubMember member : topFiveMembers) {
            if (member.getAgeGroup().equalsIgnoreCase(category)) {
                topFiveInCategoryBreaststroke.add(member);
            }
        }

        if (!topFiveInCategoryBreaststroke.isEmpty()) {
            int numMembers = Math.min(5, topFiveInCategoryBreaststroke.size());
            for (int i = 0; i < numMembers; i++) {
                SwimmingClubMember member = topFiveInCategoryBreaststroke.get(i);
                System.out.println(member + " tid: " + color.ANSI_GREEN + member.getSwimmingResult() + color.ANSI_RESET);
            }
        } else {
            System.out.println(color.ANSI_RED + "There are not enough members in the " + category + " category. \nPress 1 to add a member." + color.ANSI_RESET);
        }
    }

}
