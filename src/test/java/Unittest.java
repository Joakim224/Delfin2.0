import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import data.SwimmingClubMember;
import domain.Database;
import domain.Controller;
import org.junit.Test;
import java.util.ArrayList;

public class Unittest {

    @Test
    public void testAddMember() {
        Database database = new Database();
        database.addMember("John", 25, true, "Senior", "Regular", "Crawl", true);
        assertEquals(1, database.getMembers().size());
    }

    @Test
    public void testCheckSubscription() {
        Database database = new Database();

        database.addMember("John", 17, true, "Junior", "Regular", "Back crawl", true);
        database.addMember("Jane", 25, true, "Senior", "Regular", "Butterfly", true);
        database.addMember("Bob", 65, true, "Senior", "Competitive", "Breaststroke", true);
        double yearlyIncome = database.checkSubscription();
        assertEquals(1000 + 1600 + (1600 * 0.75), yearlyIncome);
    }

    @Test
    public void testFindMemberName() {
        Database database = new Database();
        database.addMember("Jhon", 17, true, "Junior", "regular", "Butterfly", true);
        database.addMember("Jhon", 25, true, "Senior", "regular", "Breaststroke", true);

        // søg af superhelt navn
        ArrayList<SwimmingClubMember> findMemberName = database.findMemberName("Jhon");

        assertEquals(2, findMemberName.size()); //

        // check om search resultat har den superhero man forventer
        assertTrue(findMemberName.stream().anyMatch(member -> member.getName().equals("Jhon")));
        assertTrue(findMemberName.stream().anyMatch(member -> member.getName().equals("Jhon")));
    }

    @Test
    public void testPrintMembers() {
        // Laver en ny controller
        Controller c = new Controller();

        // Tilføjer 2 test members
        c.addMember("Test1", 15, true, "Junior", "Competitive", "Crawl", false);
        c.addMember("Test2", 30, true, "Senior", "Competitive", "Butterfly", false);

        // Kalder printMembers metoden og samler det printede information op i printStream
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        c.printMembers();

        // Definere forventet output fra test members
        String expectedOutput =
                "Member: Test1, age: 15, active subscription: true, age group: Junior, exercise type: Competitive, active discipline: Crawl, paid: false" +
                System.lineSeparator() +
                "Member: Test2, age: 30, active subscription: true, age group: Senior, exercise type: Competitive, active discipline: Butterfly, paid: false";

        // Asserter at forventede og printede output er det samme
        assertEquals(expectedOutput, outContent.toString().trim());
    }
}