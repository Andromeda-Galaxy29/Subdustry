package subdustry.content;

import arc.Core;
import arc.graphics.Color;
import mindustry.game.Team;

public class SubTeams {
    public static Team precursor, nature;

    public static void load(){
        precursor = newTeam(41, "precursor", Color.valueOf("81e550"),
                Color.valueOf("81e550"),
                Color.valueOf("41a326"),
                Color.valueOf("117412")
        );

        nature = newTeam(42, "nature", Color.valueOf("ffb92b"));
    }

    public static Team newTeam(int id, String name, Color color){
        return newTeam(id, name, color, color, color.cpy().mul(0.75f), color.cpy().mul(0.5f));
    }

    public static Team newTeam(int id, String name, Color color, Color pal1, Color pal2, Color pal3){
        Team team = Team.get(id);
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
