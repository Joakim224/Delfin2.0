package domain;

import data.Filehandler;
import data.SwimmingClubMember;

import java.io.FileNotFoundException;
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

    public void loadData() throws FileNotFoundException {
        filehandler.loadMemberData(database.getMembers());
    }

    public void saveData() {
        try {
            filehandler.saveMemberData(database.getMembers());
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    public ArrayList<SwimmingClubMember> searchMember(String memberName) {
        return database.findMemberName(memberName);
    }

    public void addSwimmingResult(String name, LocalDateTime swimmingResultDateTime, String event, int placement) {

        ArrayList<SwimmingClubMember> foundMembers = searchMember(name);

        if (!foundMembers.isEmpty()) {
            SwimmingClubMember selectedMember = foundMembers.get(0);

            selectedMember.setSwimmingResultDateTime(swimmingResultDateTime);
            selectedMember.setEvent(event);
            selectedMember.setPlacement(placement);
        }
    }
}