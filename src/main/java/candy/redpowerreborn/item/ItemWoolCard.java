package candy.redpowerreborn.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;

public class ItemWoolCard extends Item {
	public ItemWoolCard()
    {
        this.maxStackSize = 1;
    }

	@Override
	public ItemStack getContainerItem(ItemStack itemstack) {
		return itemstack;
	}
	
	@Override
	public boolean hasContainerItem(ItemStack itemstack) {
		return true;
	}
}
