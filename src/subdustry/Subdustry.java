package subdustry;

import arc.*;
import arc.util.*;
import mindustry.game.*;
import mindustry.mod.*;
import subdustry.content.*;
import subdustry.game.SubTeams;
import subdustry.ui.EditorUIModifier;

public class Subdustry extends Mod{

    public Subdustry(){
        Log.info("Subdustry loaded");

        Events.on(EventType.ClientLoadEvent.class, (event) -> {
            EditorUIModifier.modify();
        });
    }

    @Override
    public void init() {
        super.init();
        SubIcons.load();
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
