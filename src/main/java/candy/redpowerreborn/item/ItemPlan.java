package candy.redpowerreborn.item;

import java.util.ArrayList;
import java.util.List;

import candy.redpowerreborn.RedPower;
import candy.redpowerreborn.client.gui.GuiHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemPlan extends Item {
	public ItemPlan() {
		super();
		setMaxStackSize(64);
		this.addPropertyOverride(new ResourceLocation("set"), new IItemPropertyGetter() {
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, World worldIn, EntityLivingBase entityIn) {
				return ItemPlan.isSet(stack) ? 1.0F : 0.0F;
			}
		});
	}
	
	public static boolean isSet(ItemStack stack) {
		return stack.hasTagCompound() && getCraftingResult(stack) != null && getCraftingStacks(stack) != null;
	}
	
	public static ItemStack[] getCraftingStacks(ItemStack stack) {
		if(stack == null)
			return null;
		ItemStack[] stacks = new ItemStack[9];
		if (stack.hasTagCompound()) {
			if (stack.getTagCompound().hasKey("CraftingResult")) {
				// Gets the custom taglist we wrote to this compound, if any
				NBTTagList items = stack.getTagCompound().getTagList("CraftingSlots", Constants.NBT.TAG_COMPOUND);
				
				for (int i = 0; i < items.tagCount(); ++i) {
					NBTTagCompound item = (NBTTagCompound) (items.getCompoundTagAt(i));
					int slot = item.getInteger("Slot");
					
					ItemStack craft = ItemStack.loadItemStackFromNBT(item);
					craft.stackSize = 1;
					stacks[slot] = craft;
				}
				return stacks;
			}
		}
		return null;
	}
	
	public static ItemStack getCraftingResult(ItemStack stack) {
		if (stack != null && stack.hasTagCompound()) {
			if (stack.getTagCompound().hasKey("CraftingResult")) {
				ItemStack craft = ItemStack.loadItemStackFromNBT(stack.getTagCompound().getCompoundTag("CraftingResult"));
				return craft;
			}
		}
		return null;
	}
	
	public static void writeToNBT(ItemStack stack, ItemStack[] stacksCrafting, ItemStack result) {
		NBTTagCompound tag;
		if (stack.hasTagCompound())
			tag = stack.getTagCompound();
		else
			tag = new NBTTagCompound();
			
		if (stacksCrafting.length != 9)
			return;
			
		if (result == null)
			return;
			
		NBTTagList items = new NBTTagList();
		
		for (int i = 0; i < 9; ++i) {
			if (stacksCrafting[i] != null) {
				NBTTagCompound item = new NBTTagCompound();
				item.setInteger("Slot", i);
				stacksCrafting[i].writeToNBT(item);
				
				items.appendTag(item);
			}
		}
		
		NBTTagCompound resultitem = new NBTTagCompound();
		result.writeToNBT(resultitem);
		tag.setTag("CraftingSlots", items);
		tag.setTag("CraftingResult", resultitem);
		stack.setTagCompound(tag);
	}
	
	@Override
	public int getItemStackLimit(ItemStack stack) {
		return this.isSet(stack) ? 1 : 64;
	}
	
	@Override
	public int getItemStackLimit() {
		return 64;
	}
	
	public String getItemStackDisplayName(ItemStack stack) {
		if (this.isSet(stack))
			return I18n.translateToLocalFormatted("item.redpowerreborn_plan_set.name", new Object[] { this.getCraftingResult(stack).getDisplayName() });
			
		return super.getItemStackDisplayName(stack);
	}
	
	public EnumRarity getRarity(ItemStack stack) {
		return this.isSet(stack) ? EnumRarity.RARE : EnumRarity.COMMON; // TODO see if it is common or uncommon
	}
	
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		ItemStack[] stacks = this.getCraftingStacks(stack);
		if (stacks == null)
			return;
		List<ItemStack> amounts = new ArrayList<ItemStack>();
		boolean flag;
		for (int i = 0; i < stacks.length; i++) {
			flag = true;
			for (int k = 0; k < amounts.size(); k++) {
				if (flag && amounts.get(k).isItemEqual(stacks[i])) {
					amounts.get(k).stackSize++;
					flag = false;
				}
			}
			if (flag && stacks[i] != null) {
				amounts.add(stacks[i]);
			}
		}
		
		for (ItemStack in : amounts) {
			tooltip.add(TextFormatting.GRAY + I18n.translateToLocalFormatted("item.redpowerreborn_plan_set.tooltip", new Object[] { in.stackSize, in.getDisplayName() }));
		}
		//
		// if (stack.hasTagCompound())
		// {
		// NBTTagCompound nbttagcompound = stack.getTagCompound();
		// String s = nbttagcompound.getString("author");
		//
		// if (!StringUtils.isNullOrEmpty(s))
		// {
		// tooltip.add(TextFormatting.GRAY + I18n.translateToLocalFormatted("book.byAuthor", new Object[] {s}));
		// }
		//
		// tooltip.add(TextFormatting.GRAY + I18n.translateToLocal("book.generation." + nbttagcompound.getInteger("generation")));
		// }
	}
}
