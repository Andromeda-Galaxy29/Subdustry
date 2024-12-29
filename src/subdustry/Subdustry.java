package subdustry;

import arc.util.*;
import mindustry.mod.*;
import subdustry.content.*;

public class Subdustry extends Mod{

    public Subdustry(){
        Log.info("Subdustry loaded");
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
