
import java.util.ArrayList;
import java.util.List;

public class TeamFormation {

    public static int countTeams(List<Integer> skills, int minPlayers, int minLevel, int maxLevel) {
        if (skills.size() < minPlayers) return 0;

        if (minLevel > maxLevel) return 0;

        List<Integer> possibleTeamMembers = new ArrayList<>();
        for (int skill : skills) if (skill >= minLevel && skill <= maxLevel) possibleTeamMembers.add(skill);
        int[] possibleTeamArr = new int[possibleTeamMembers.size()];
        for(int i = 0;i < possibleTeamArr.length;i++)
            possibleTeamArr[i] = possibleTeamMembers.get(i);
        return teamsCount(possibleTeamArr, minPlayers);
    }

    static int teamsCount(int[] values, int minSize) {
        int n = values.length;
        int possible = 0;
        for (int i = 0; i < (1 << n); i++) {
            List<Integer> possibleTeam = new ArrayList<>();
            for (int j = 0; j < n; j++) if ((i & (1 << j)) > 0) possibleTeam.add(values[j]);
            if (possibleTeam.size() >= minSize) possible += 1;
        }
        return possible;
    }
}