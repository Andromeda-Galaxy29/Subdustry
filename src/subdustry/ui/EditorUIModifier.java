package subdustry.ui;

import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import mindustry.Vars;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.ui.*;
import subdustry.game.SubTeams;
import subdustry.world.blocks.environment.ShapedProp;

import static mindustry.Vars.*;

public class EditorUIModifier {
    public static void modify(){
        ui.editor.shown(() -> {
            Table cont = (Table) ui.editor.getChildren().get(0);
            Table mid = (Table) cont.getChildren().get(0);

            // Adds custom teams
            Table teams = (Table) mid.getChildren().get(0);
            teams.row();
            addTeamButton(teams, SubTeams.precursor);

            // Adds shaped props button
            mid.row();
            mid.table(t -> {
                t.button("@editor.subdustry-shaped-props", Icon.powerOldSmall, Styles.flatt, EditorUIModifier::placeShapedProps).growX().margin(9f);
            }).growX().top();
        });
    }

    public static void addTeamButton(Table table, Team team){
        ImageButton button = new ImageButton(Tex.whiteui, Styles.clearNoneTogglei);
        button.margin(4f);
        button.getImageCell().grow();
        button.getStyle().imageUpColor = team.color;
        button.clicked(() -> editor.drawTeam = team);
        button.update(() -> button.setChecked(editor.drawTeam == team));
        table.add(button);
    }

    public static void placeShapedProps(){
        Vars.world.tiles.eachTile(tile -> {
            if(tile.block() instanceof ShapedProp.ShapedPropPlacer placer){
                placer.place(tile);
            }
        });
    }
}
