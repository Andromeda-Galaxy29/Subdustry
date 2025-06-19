package subdustry;

import arc.*;
import arc.util.*;
import mindustry.*;
import mindustry.game.*;
import mindustry.mod.*;
import subdustry.content.*;
import subdustry.game.SubTeams;
import subdustry.world.blocks.environment.ShapedProp;

public class Subdustry extends Mod{

    public Subdustry(){
        Log.info("Subdustry loaded");
    }

    @Override
    public void init() {
        super.init();
        SubIcons.load();

        //TODO: Make a button for this in the map editor
        Events.on(EventType.WorldLoadEvent.class, (event) -> {
            Vars.world.tiles.eachTile(tile -> {
                if(tile.block() instanceof ShapedProp.ShapedPropPlacer placer){
                    placer.place(tile);
                }
            });
        });
    }

    @Override
    public void loadContent(){
        SubSounds.load();

        SubItems.load();
        SubLiquids.load();
        SubUnitTypes.load();
        SubAttributes.load();
        SubBlocks.load();

        SubTeams.load();
        SubPlanets.load();
        Planet4546bSectors.load();
        Planet4546bTechTree.load();
    }

}
