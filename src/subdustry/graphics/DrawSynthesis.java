package subdustry.graphics;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.graphics.g2d.TextureRegion;
import mindustry.gen.Building;
import mindustry.graphics.Layer;
import mindustry.world.Block;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.draw.DrawRegion;

public class DrawSynthesis extends DrawRegion {
	public int lightningAmount = 2, segmentAmount = 3;

	public float lightningMinX = -6.5f, lightningMaxX = 4.5f,
				lightningMaxYOffset = 6f;

	public DrawSynthesis(String suffix){
		super(suffix);
	}

	@Override
	public void draw(Building build) {
		if(build instanceof GenericCrafter.GenericCrafterBuild cbuild){
			float progressScl = cbuild.progress;

			TextureRegion slice = new TextureRegion(region);

			slice.setWidth(region.width*progressScl);

			Draw.rect(slice,  build.x-region.width/8f * (1-progressScl), build.y);

			float lightningSegmentLength = (1-progressScl) / segmentAmount * (lightningMaxX - lightningMinX);

			float[][] lightningPositions = new float[lightningAmount][segmentAmount+1];


			if(cbuild.efficiency > 0){
				float z = Draw.z();
				Draw.z(Layer.effect);

				Draw.color(Color.valueOf("81e550"));
				for(int i=0; i < lightningAmount;i++){
					for(int j =0; j < segmentAmount; j++){
						lightningPositions[i][j+1] = (float)Math.random() * lightningMaxYOffset - lightningMaxYOffset / 2;

						if(j == segmentAmount-1) lightningPositions[i][j+1] = 0f;

						Lines.line(build.x+lightningMinX+(lightningMaxX-lightningMinX) * progressScl+lightningSegmentLength * j, build.y+lightningPositions[i][j], build.x+lightningMinX+(lightningMaxX-lightningMinX) * progressScl+lightningSegmentLength * (j+1), build.y+lightningPositions[i][j+1]);
					}
				}
				Draw.color(Color.white);
				Draw.z(z);
			}
		}
	}

	@Override
	public TextureRegion[] icons(Block block){
		return new TextureRegion[]{};
	}
}
