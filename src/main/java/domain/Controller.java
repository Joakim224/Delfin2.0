package domain;

import data.Filehandler;
import data.SwimmingClubMember;
import java.util.ArrayList;

public class Controller {
    Database database = new Database();
    Filehandler filehandler = new Filehandler();
    private ArrayList<SwimmingClubMember> members = new ArrayList<>();

    public void addMember(String name, int age, boolean subscriptionActive, String ageGroup, String excerciseType, String activeDiscipline, boolean displaySubscriptionFeesAndPaymentStatus) {
        database.addMember(name, age, subscriptionActive, ageGroup, excerciseType, activeDiscipline, displaySubscriptionFeesAndPaymentStatus);
    }

    public void printMembers() {
        database.printMembers();
    }

    public double checkSubscription() {
        return database.checkSubscription();
    }

    public void loadData() {
        database.loadData();
    }

    public void saveData() {
        database.saveData();
    }

    public ArrayList<SwimmingClubMember> searchMember(String memberName) {
        return database.searchMember(memberName);
    }

    public void addSwimmingResult(String name, String dateString, double swimmingResult, String event, int placement) {
        database.addSwimmingResult(name,dateString, swimmingResult, event, placement);
    }

    public void competitiveSwimmersSplit() {
        database.splitAndPrintCompetitiveSwimmers(database.getMembers());
    }

    public void displaySubscriptionFeesAndPaymentStatus() {
        database.displaySubscriptionFeesAndPaymentStatus();
    }


    public void updatePaymentStatus(String memberName, boolean newPaymentStatus) {
        database.updatePaymentStatus(memberName, newPaymentStatus);
    }

    public void printTop5ByCrawl() {
        database.printTop5ByCrawl();
    }

    public void printTop5ByBackCrawl() {
        database.printTop5ByBackCrawl();
    }

    public void printTop5byButterfly() {
        database.printTop5byButterfly();
    }

    public void printTop5ByBreaststroke() {
        database.printTop5ByBreaststroke();
    }
}