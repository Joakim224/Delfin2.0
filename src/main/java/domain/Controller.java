package domain;

import data.Filehandler;
import data.SwimmingClubMember;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Controller {
    Database database = new Database();
    Filehandler filehandler = new Filehandler();
    private ArrayList<SwimmingClubMember> members = new ArrayList<>();

    public void addMember(String name, int age, boolean subscriptionActive, String ageGroup, String excerciseType) {
        database.addMember(name, age, subscriptionActive, ageGroup, excerciseType);
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

    public void addSwimmingResult(String name, LocalDateTime swimmingResultDateTime, String event, int placement) {
        database.addSwimmingResult(name, swimmingResultDateTime, event, placement);
    }

    public void competitiveSwimmersSplit() {
        database.splitAndPrintCompetitiveSwimmers(database.getMembers());
    }
}