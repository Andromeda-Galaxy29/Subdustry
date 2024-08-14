package subdustry;

import arc.util.*;
import mindustry.mod.*;
import subdustry.content.*;

public class Subdustry extends Mod{

    public Subdustry(){
        Log.info("Subdustry loaded");
    }

    @Override
    public void loadContent(){
        SubSounds.load();
        SubItems.load();
        SubUnitTypes.load();
        SubAttributes.load();
        SubBlocks.load();
    }

}
