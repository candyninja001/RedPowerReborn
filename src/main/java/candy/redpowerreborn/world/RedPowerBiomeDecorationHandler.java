package candy.redpowerreborn.world;

import candy.redpowerreborn.block.BlockIndigoFlower;
import candy.redpowerreborn.block.RedPowerBlocks;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RedPowerBiomeDecorationHandler {
	// This code was copied from botania's BiomeDecorationHandler and updated/revised for indigo flowers and maybe rubber trees later
	
	//seed: 6789652934464220863
	// it spawned flowers at 0 80 0 at one point
	
	// the default values for botania are
	// flowerQuantity = 2;
	// flowerDensity = 2;
	// flowerPatchSize = 6;
	// flowerPatchChance = 16;
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onWorldDecoration(DecorateBiomeEvent.Decorate event) {
		if((event.getResult() == Result.ALLOW || event.getResult() == Result.DEFAULT) && event.getType() == EventType.FLOWERS) {
			int dist = Math.min(8, Math.max(1, 6));//flowerPatchSize
			for(int i = 0; i < 2; i++) {//flowerQuantity
				if(event.getRand().nextInt(16) == 0) {//flowerPatchChance
					int x = event.getWorld().getChunkFromBlockCoords(event.getPos()).xPosition + event.getRand().nextInt(16) + 8;
					int z = event.getWorld().getChunkFromBlockCoords(event.getPos()).zPosition + event.getRand().nextInt(16) + 8;
					int y = event.getWorld().getTopSolidOrLiquidBlock(new BlockPos(x, event.getWorld().getActualHeight(), z)).getY();

					int color = event.getRand().nextInt(16);
					BlockPos pos = new BlockPos(x, y, z);

					for(int j = 0; j < 2 * 6; j++) {//flowerDensity and flowerPatchChance
						int x1 = x + event.getRand().nextInt(dist * 2) - dist;
						int y1 = y + event.getRand().nextInt(4) - event.getRand().nextInt(4);
						int z1 = z + event.getRand().nextInt(dist * 2) - dist;
						BlockPos pos1 = new BlockPos(x1, y1, z1);

						if(event.getWorld().isAirBlock(pos1) && (!event.getWorld().provider.getHasNoSky() || y1 < 127) && ((BlockIndigoFlower)RedPowerBlocks.INDIGO_FLOWER).canBlockStay(event.getWorld(), pos1, event.getWorld().getBlockState(pos1))){//.canBlockStay(event.getWorld(), x1, y1, z1)) {
							event.getWorld().setBlockState(pos1, RedPowerBlocks.INDIGO_FLOWER.getDefaultState(), 2);//2 might not be correct
						}
					}
				}
			}
		}
	}
}
