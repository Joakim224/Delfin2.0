package domain;

import data.SwimmingClubMember;
import data.Filehandler;
import java.util.ArrayList;

public class Database {
    ArrayList<SwimmingClubMember> members = new ArrayList<>();
    Filehandler filehandler = new Filehandler();

    public void addMember(String name, int age, boolean subscriptionActive, String ageGroup, String excerciseType) {
        members.add(new SwimmingClubMember(name, age, subscriptionActive, ageGroup, excerciseType));
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

    public void printMembers() {
        for (SwimmingClubMember member : members) {
            System.out.println(member);
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
}