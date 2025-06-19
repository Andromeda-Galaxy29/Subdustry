package subdustry.content;

import subdustry.content.blocks.SubAlterraBlocks;
import subdustry.content.blocks.SubEnvironmentBlocks;
import subdustry.content.blocks.SubPrecursorBlocks;

public class SubBlocks {
    public static void load(){
        SubEnvironmentBlocks.load();
        SubAlterraBlocks.load();
        SubPrecursorBlocks.load();
    }
}
