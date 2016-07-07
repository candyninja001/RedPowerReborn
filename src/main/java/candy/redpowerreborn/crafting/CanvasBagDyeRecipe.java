package candy.redpowerreborn.crafting;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import candy.redpowerreborn.item.ItemCanvasBag;
import candy.redpowerreborn.item.RedPowerItems;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.oredict.OreDictionary;

public class CanvasBagDyeRecipe implements IRecipe
{
    protected ItemStack output = null;
    protected ArrayList<Object> input = new ArrayList<Object>();

    public CanvasBagDyeRecipe(Block result, Object... recipe){ this(new ItemStack(result), recipe); }
    public CanvasBagDyeRecipe(Item  result, Object... recipe){ this(new ItemStack(result), recipe); }

    public CanvasBagDyeRecipe(ItemStack result, Object... recipe)
    {
        output = result.copy();
        for (Object in : recipe)
        {
            if (in instanceof ItemStack)
            {
                input.add(((ItemStack)in).copy());
            }
            else if (in instanceof Item)
            {
                input.add(new ItemStack((Item)in));
            }
            else if (in instanceof Block)
            {
                input.add(new ItemStack((Block)in));
            }
            else if (in instanceof String)
            {
                input.add(OreDictionary.getOres((String)in));
            }
            else
            {
                String ret = "Invalid shapeless ore recipe: ";
                for (Object tmp :  recipe)
                {
                    ret += tmp + ", ";
                }
                ret += output;
                throw new RuntimeException(ret);
            }
        }
    }

    CanvasBagDyeRecipe(ShapelessRecipes recipe, Map<ItemStack, String> replacements)
    {
        output = recipe.getRecipeOutput();

        for(ItemStack ingredient : recipe.recipeItems)
        {
            Object finalObj = ingredient;
            for(Entry<ItemStack, String> replace : replacements.entrySet())
            {
                if(OreDictionary.itemMatches(replace.getKey(), ingredient, false))
                {
                    finalObj = OreDictionary.getOres(replace.getValue());
                    break;
                }
            }
            input.add(finalObj);
        }
    }

    /**
     * Returns the size of the recipe area
     */
    @Override
    public int getRecipeSize(){ return input.size(); }

    @Override
    public ItemStack getRecipeOutput(){ return output; }

    /**
     * Returns an Item that is the result of this recipe
     */
    @Override
    public ItemStack getCraftingResult(InventoryCrafting var1){
    	//return output.copy(); 
    	int color = 0;
    	c: for (int i = 0; i < var1.getSizeInventory(); i++){
    		if(var1.getStackInSlot(i) != null){
    			int[] ids = OreDictionary.getOreIDs(var1.getStackInSlot(i));
    			for (int id : ids) {
    				if (OreDictionary.getOreName(id).startsWith("dye") && OreDictionary.getOreName(id) != "dye"){
    					String s = OreDictionary.getOreName(id);
    					if(s.substring(3) == "White"){
    						color = 0;
    						break c;
    					}else if(s.substring(3) == "Orange"){
    						color = 1;
    						break c;
    					}else if(s.substring(3) == "Magenta"){
    						color = 2;
    						break c;
    					}else if(s.substring(3) == "LightBlue"){
    						color = 3;
    						break c;
    					}else if(s.substring(3) == "Yellow"){
    						color = 4;
    						break c;
    					}else if(s.substring(3) == "Lime"){
    						color = 5;
    						break c;
    					}else if(s.substring(3) == "Pink"){
    						color = 6;
    						break c;
    					}else if(s.substring(3) == "Gray"){
    						color = 7;
    						break c;
    					}else if(s.substring(3) == "LightGray"){
    						color = 8;
    						break c;
    					}else if(s.substring(3) == "Cyan"){
    						color = 9;
    						break c;
    					}else if(s.substring(3) == "Purple"){
    						color = 10;
    						break c;
    					}else if(s.substring(3) == "Blue"){
    						color = 11;
    						break c;
    					}else if(s.substring(3) == "Brown"){
    						color = 12;
    						break c;
    					}else if(s.substring(3) == "Green"){
    						color = 13;
    						break c;
    					}else if(s.substring(3) == "Red"){
    						color = 14;
    						break c;
    					}else if(s.substring(3) == "Black"){
    						color = 15;
    						break c;
    					}else{
    						System.out.println("could not locate color from " + s);//TODO remove when confident this will never occur
    					}
    				}
    			}
    		}
    	}
    	
    	ItemStack bag;
    	for (int i = 0; i < var1.getSizeInventory(); i++){
    		if(var1.getStackInSlot(i) != null && var1.getStackInSlot(i).getItem() instanceof ItemCanvasBag){
    			bag = var1.getStackInSlot(i).copy();
    			if (!bag.hasTagCompound()){
    				NBTTagCompound tag = new NBTTagCompound();
    				tag.setInteger("Color", color);
    				bag.setTagCompound(tag);
    				return bag;
    			}else{
    				bag.getTagCompound().setInteger("Color", color);
    				return bag;
    			}
    		}
    	}
    	return null;
    }

    /**
     * Used to check if a recipe matches current crafting inventory
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean matches(InventoryCrafting var1, World world)
    {
        ArrayList<Object> required = new ArrayList<Object>(input);

        for (int x = 0; x < var1.getSizeInventory(); x++)
        {
            ItemStack slot = var1.getStackInSlot(x);

            if (slot != null)
            {
                boolean inRecipe = false;
                Iterator<Object> req = required.iterator();

                while (req.hasNext())
                {
                    boolean match = false;

                    Object next = req.next();

                    if (next instanceof ItemStack)
                    {
                        match = OreDictionary.itemMatches((ItemStack)next, slot, false);
                    }
                    else if (next instanceof List)
                    {
                        Iterator<ItemStack> itr = ((List<ItemStack>)next).iterator();
                        while (itr.hasNext() && !match)
                        {
                            match = OreDictionary.itemMatches(itr.next(), slot, false);
                        }
                    }

                    if (match)
                    {
                        inRecipe = true;
                        required.remove(next);
                        break;
                    }
                }

                if (!inRecipe)
                {
                    return false;
                }
            }
        }

        return required.isEmpty();
    }

    /**
     * Returns the input for this recipe, any mod accessing this value should never
     * manipulate the values in this array as it will effect the recipe itself.
     * @return The recipes input vales.
     */
    public ArrayList<Object> getInput()
    {
        return this.input;
    }

    @Override
    public ItemStack[] getRemainingItems(InventoryCrafting inv) //getRecipeLeftovers
    {
        return ForgeHooks.defaultRecipeGetRemainingItems(inv);
    }
}