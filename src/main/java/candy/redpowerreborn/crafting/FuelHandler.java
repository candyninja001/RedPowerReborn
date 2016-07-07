package candy.redpowerreborn.crafting;

import candy.redpowerreborn.block.RedPowerBlocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;

public class FuelHandler implements IFuelHandler {

	@Override
	public int getBurnTime(ItemStack fuel) {
		if(fuel.getItem() == Item.getItemFromBlock(RedPowerBlocks.rubber_log))
			return 300;
		return 0;
	}
}
