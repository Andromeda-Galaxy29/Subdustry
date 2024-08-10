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
        SubItems.load();
        SubUnitTypes.load();
        SubBlocks.load();
    }

}
