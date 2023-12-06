package data;

import java.time.LocalDateTime;

public class SwimmingClubMember {
    private String name;
    private int age;
    private boolean subscriptionActive;
    private String ageGroup;
    private String exerciseType;
    private String swimmingResultDateTime;
    private String activeDiscipline;
    private String event;
    private int placement;
    private double subscriptionFee;


    public SwimmingClubMember(String name, int age, boolean subscriptionActive, String ageGroup, String exerciseType, String activeDiscipline, String swimmingResultDateTime, String event, int placement ) {
        this.name = name;
        this.age = age;
        this.subscriptionActive = subscriptionActive;
        this.ageGroup = ageGroup;
        this.exerciseType = exerciseType;
        this.activeDiscipline = activeDiscipline;
        this.swimmingResultDateTime = null;
        this.event = "";
        this.placement = 0;
    }

    public SwimmingClubMember(String name, int age, boolean subscriptionActive, String ageGroup, String exerciseType, String activeDiscipline) {
        this.name = name;
        this.age = age;
        this.subscriptionActive = subscriptionActive;
        this.ageGroup = ageGroup;
        this.exerciseType = exerciseType;
        this.activeDiscipline = activeDiscipline;
    }

    public double getSubscriptionFee() {
        return subscriptionFee;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getAgeGroup() {
        return ageGroup;
    }

    public String getExerciseType() {
        return exerciseType;
    }

    public String getActiveDiscipline() {
        return activeDiscipline;
    }

    public String getSwimmingResultDateTime() {
        return swimmingResultDateTime;
    }

    public void setSwimmingResultDateTime(String swimmingResultDateTime) {
        this.swimmingResultDateTime = swimmingResultDateTime;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public int getPlacement() {
        return placement;
    }

    public void setPlacement(int placement) {
        this.placement = placement;
    }

    public Boolean getSubscriptionActive() {
        return subscriptionActive;
    }

    public String toString() {
        return "Member: " + name +
                ", age: " + age +
                ", active subscription: " + subscriptionActive +
                ", age group: " + ageGroup +
                ", exercise type: " + exerciseType +
                ", active discipline: " + activeDiscipline;
        }
}