package subdustry.content;

import subdustry.content.blocks.SAlterraBlocks;
import subdustry.content.blocks.SEnvironmentBlocks;
import subdustry.content.blocks.SPrecursorBlocks;

public class SBlocks {
    public static void load(){
        SEnvironmentBlocks.load();
        SAlterraBlocks.load();
        SPrecursorBlocks.load();
    }
}
