package candy.redpowerreborn.item.crafting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;

import candy.redpowerreborn.RedPower;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class AlloyFurnaceRecipes {
	private static final AlloyFurnaceRecipes instance = new AlloyFurnaceRecipes();
    private List<ItemStack[]> recipeList = new ArrayList<ItemStack[]>();
    private List<ItemStack> resultList = new ArrayList<ItemStack>();
    private List<Float> experienceList = new ArrayList<Float>();

    /**
     * Returns an instance of FurnaceRecipes.
     */
    public static AlloyFurnaceRecipes instance()
    {
        return instance;
    }

    // note: Item.getItemFromBlock(input)
    
    private AlloyFurnaceRecipes()
    {
    	ItemStack[] input = {new ItemStack(Items.redstone, 4), new ItemStack(Items.iron_ingot, 1)};
        this.addSmeltingRecipe(input , new ItemStack(Items.diamond), 0.7F); //TODO should be red alloy
        //ItemStack[] input = {new ItemStack(Items.redstone, 4), new ItemStack(RedPower.itemCopperIngot, 1)};
        //this.addSmeltingRecipe(input , new ItemStack(RedPower.itemRedAlloyIngot), 0.7F); //TODO should be red alloy
    }

    /**
     * Adds an alloy smelting recipe using an ItemStack[] as the input for the recipe.
     */
    public void addSmeltingRecipe(ItemStack[] input, ItemStack output, float experience)
    {
    	//TODO update backup check
        //if (getSmeltingResult(input) != null) {
        //	net.minecraftforge.fml.common.FMLLog.info("Ignored smelting recipe with conflicting input: " + input. + " = " + stack);
        //	return;
        //}
    	recipeList.add(input);
    	resultList.add(output);
    	experienceList.add(experience);
    }

    /**
     * Returns the index of the alloy smelting recipe for use elsewhere.
     */
    public int getSmeltingIndex(ItemStack[] stock)
    {
    	//boolean hasValidIngredient;
    	int q = 0;
    	boolean isMissingIngredient;
    	ItemStack[] recipe;
    	for (int i = 0; i < recipeList.size(); ++i)
        {
        //for (ItemStack[] recipe : recipeList)
    		recipe = recipeList.get(i);
        	isMissingIngredient = false;
        	
        	for (ItemStack ingredient : recipe){
        		//hasValidIngredient = false;
        		q = 0;
        		for (ItemStack item : stock){
        			if (item != null && ingredient.getItem() == item.getItem())
        				q += item.stackSize;
        		}
        		if(q < ingredient.stackSize)
        			isMissingIngredient = true;
        	}
        	if(!isMissingIngredient)
        		return i;
        }

        return -1;
    }
    
    public ItemStack getSmeltingResult(int i){
    	if (i == -1)
    		return null;
    	return resultList.get(i);
    }
    
    public ItemStack[] getSmeltingRequirements(int i){
    	if (i == -1)
    		return null;
    	return recipeList.get(i);
    }

    public float getSmeltingExperience(int i)
    {
        if(i == -1)
        	return 0.0F;
        return experienceList.get(i);
    }
}
