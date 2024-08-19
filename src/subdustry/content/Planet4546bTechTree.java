package subdustry.content;

import static mindustry.content.TechTree.*;
import static subdustry.content.SubBlocks.*;
import static subdustry.content.SubItems.*;
import static subdustry.content.Planet4546bSectors.*;
import static subdustry.content.SubUnitTypes.*;
import static mindustry.content.Liquids.*;
import static mindustry.game.Objectives.*;

public class Planet4546bTechTree {
    public static void load(){
        SubPlanets.planet4546B.techTree = nodeRoot("@planet.subdustry-4546b.name", coreShallows, false, () ->{
            node(submarineDuct, ()->{
                node(submarineDuctRouter, ()->{
                    node(submarineDuctBridge, ()->{

                    });
                    node(submarineFlowDuct, ()->{

                    });
                });
            });

            node(harvester, ()->{

            });

            node(solarPanel, ()->{
                node(copperWireNode, ()->{

                });
                node(metalGrinder, ()->{
                    node(titaniumCrucible, ()->{

                    });
                });
            });

            node(dissolve, ()->{
                node(titaniumOreWall, ()->{
                    node(largeTitaniumOreWall, ()->{

                    });
                });
            });

            node(safeShallows, ()->{

            });

            nodeProduce(titanium, ()->{
                nodeProduce(copperOre, ()->{

                });
                nodeProduce(quartz, ()->{

                });
                nodeProduce(acidMushroom, ()->{

                });
            });

        });
    }
}
