package subdustry.game;

import arc.graphics.Color;
import mindustry.game.Team;

public class STeams {
    public static Team precursor;

    public static void load(){
        precursor = newTeam(41, "[#81e550]\uEB33[]", "precursor", Color.valueOf("81e550"),
                Color.valueOf("81e550"),
                Color.valueOf("41a326"),
                Color.valueOf("117412")
        );
    }

    public static Team newTeam(int id, String icon, String name, Color color, Color pal1, Color pal2, Color pal3){
        Team team = Team.get(id);
        team.emoji = icon;
        team.name = name;
        team.color.set(color);
        team.palette[0] = pal1;
        team.palette[1] = pal2;
        team.palette[2] = pal3;

        for(int i = 0; i < 3; i++){
            team.palettei[i] = team.palette[i].rgba();
        }

        team.hasPalette = true;

        return team;
    }
}
