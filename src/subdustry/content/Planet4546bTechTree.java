package subdustry.content;

import static mindustry.content.TechTree.*;
import static subdustry.content.SubBlocks.*;
import static subdustry.content.SubItems.*;
import static subdustry.content.Planet4546bSectors.*;

public class Planet4546bTechTree {
    public static void load(){
        SubPlanets.planet4546B.techTree = nodeRoot("@planet.subdustry-4546b.name", coreShallows, false, () ->{
            node(submergedDuct, ()->{
                node(submergedDuctRouter, ()->{
                    node(submergedDuctBridge, ()->{

                    });
                    node(submergedFlowDuct, ()->{

                    });
                });
            });

            node(harvester, ()->{

            });

            node(solarPanel, ()->{
                node(tidalGenerator, ()->{

                });
                node(copperWireNode, ()->{
                    node(battery, ()->{

                    });
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
