import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TeamFormationTests {

    @Test
    public void testForInputs1() {
        List<Integer> skills = Arrays.asList(4, 8, 5, 6);
        int minPlayers = 2;
        int minLevel = 5;
        int maxLevel = 7;
        assertEquals(
                TeamFormation.countTeams(skills, minPlayers, minLevel, maxLevel),
                1);
    }

    @Test
    public void testForInputs2() {
        List<Integer> skills = Arrays.asList(1, 2, 3, 5);
        int minPlayers = 3;
        int minLevel = 1;
        int maxLevel = 5;
        assertEquals(
                TeamFormation.countTeams(skills, minPlayers, minLevel, maxLevel),
                5);
    }
}
