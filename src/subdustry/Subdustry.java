package subdustry;

import arc.*;
import arc.math.*;
import arc.util.*;
import mindustry.game.*;
import mindustry.mod.*;
import subdustry.content.*;
import subdustry.game.*;
import subdustry.gen.*;
import subdustry.ui.*;

import static mindustry.Vars.*;

public class Subdustry extends Mod{

    public Subdustry(){
        Log.info("Subdustry loaded");

        Events.on(EventType.ClientLoadEvent.class, (event) -> {
            EditorUIModifier.modify();
        });

        // Temporary boid spawning code
//        Events.on(EventType.UnitControlEvent.class, (event) -> {
//            for(int i = 0; i < 300; i++){
//                Boid b = Boid.create();
//                b.vel.setToRandomDirection();
//                b.x = event.unit.x + Mathf.random(-60, 60);
//                b.y = event.unit.y + Mathf.random(-60, 60);
//                b.add();
//            }
//        });
    }

    @Override
    public void init() {
        super.init();
        SIcons.load();
    }

    @Override
    public void loadContent(){
        EntityRegistry.register();

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
