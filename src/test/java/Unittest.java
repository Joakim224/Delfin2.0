import data.SwimmingClubMember;
import domain.Database;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class Unittest {
    @Test
    public void testAddMember() {
        Database database = new Database();
        database.addMember("John", 25, true, "Senior", "Regular");

        assertEquals(1, database.getMembers().size());
    }

    @Test
    public void testCheckSubscription() {
        Database database = new Database();


        database.addMember("John", 17, true, "Junior", "Regular");
        database.addMember("Jane", 25, true, "Senior", "Regular");
        database.addMember("Bob", 65, true, "Senior", "Competitive");
        double yearlyIncome = database.checkSubscription();
        assertEquals(1000 + 1600 + (1600 * 0.75), yearlyIncome);
    }

    @Test
    public void testFindMemberName() {
        Database database = new Database();
        database.addMember("Jhon", 17, true, "Junior", "regular");
        database.addMember("Jhon", 25, true, "Senior", "regular");


        // søg af superhelt navn
        ArrayList<SwimmingClubMember> findMemberName = database.findMemberName("Jhon");

        assertEquals(2, findMemberName.size()); //


        // check om search resultat har den superhero man forventer
        assertTrue(findMemberName.stream().anyMatch(member -> member.getName().equals("Jhon")));
        assertTrue(findMemberName.stream().anyMatch(member -> member.getName().equals("Jhon")));
    }



}
