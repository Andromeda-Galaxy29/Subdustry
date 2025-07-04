package subdustry.content;

import arc.struct.*;

import static mindustry.content.TechTree.*;
import static mindustry.game.Objectives.*;
import static subdustry.content.blocks.SAlterraBlocks.*;
import static subdustry.content.SItems.*;
import static subdustry.content.Planet4546bSectors.*;

public class Planet4546bTechTree {
    public static void load(){
        SPlanets.planet4546B.techTree = nodeRoot("@planet.subdustry-4546b.name", coreShallows, false, () ->{
            node(submergedDuct, ()->{
                node(submergedDuctRouter, ()->{
                    node(submergedDuctBridge, ()->{

                    });
                    node(submergedFlowDuct, ()->{

                    });
                });
            });

            node(harvester, Seq.with(new Research(solarPanel)), ()->{
                node(metalGrinder, ()->{
                    node(titaniumCrucible, ()->{

                    });
                });
            });

            node(solarPanel, ()->{
                node(copperWireNode, ()->{
                    node(battery, ()->{

                    });
                });
                node(scanner, ()->{

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
                nodeProduce(metalSalvage, ()->{

                });
                nodeProduce(copperOre, ()->{

                });
                nodeProduce(quartz, ()->{
                    nodeProduce(glass, ()->{

                    });
                });
                nodeProduce(acidMushroom, ()->{
                    nodeProduce(creepvineSeedCluster, ()->{
                        nodeProduce(siliconeRubber, ()->{

                        });
                    });
                });
            });

        });
    }
}
