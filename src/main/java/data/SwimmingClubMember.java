package data;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class SwimmingClubMember {
    private String name;
    private int age;
    private boolean subscriptionActive;
    private String ageGroup;
    private String exerciseType;
    private LocalDateTime swimmingResultDateTime;
    private String event;
    private int placement;

    public SwimmingClubMember(String name, int age, boolean subscriptionActive, String ageGroup, String exerciseType) {
        this.name = name;
        this.age = age;
        this.subscriptionActive = subscriptionActive;
        this.ageGroup = ageGroup;
        this.exerciseType = exerciseType;
        this.swimmingResultDateTime = null;
        this.event = "";
        this.placement = 0;
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

    public LocalDateTime getSwimmingResultDateTime() {
        return swimmingResultDateTime;
    }

    public void setSwimmingResultDateTime(LocalDateTime swimmingResultDateTime) {
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
                ", exercise type: " + exerciseType;
        }
}