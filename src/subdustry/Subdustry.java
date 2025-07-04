package subdustry;

import arc.*;
import arc.util.*;
import mindustry.game.*;
import mindustry.mod.*;
import subdustry.content.*;
import subdustry.game.STeams;
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
        SIcons.load();
    }

    @Override
    public void loadContent(){
        SSounds.load();

        SItems.load();
        SLiquids.load();
        SUnitTypes.load();
        SAttributes.load();
        SBlocks.load();

        STeams.load();
        SPlanets.load();
        Planet4546bSectors.load();
        Planet4546bTechTree.load();
    }

}
