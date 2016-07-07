package candy.redpowerreborn.color;

import candy.redpowerreborn.item.ItemCanvasBag;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;

public class ColorHelper {	
	public static String getOreDictNameFromEnumDyeColor(EnumDyeColor color){
		switch(color){
			case BLACK:
				return "dyeBlack";
			case BLUE:
				return "dyeBlue";
			case BROWN:
				return "dyeBrown";
			case CYAN:
				return "dyeCyan";
			case GRAY:
				return "dyeGray";
			case GREEN:
				return "dyeGreen";
			case LIGHT_BLUE:
				return "dyeLightBlue";
			case LIME:
				return "dyeLime";
			case MAGENTA:
				return "dyeMagenta";
			case ORANGE:
				return "dyeOrange";
			case PINK:
				return "dyePink";
			case PURPLE:
				return "dyePurple";
			case RED:
				return "dyeRed";
			case SILVER:
				return "dyeLightGray";
			case WHITE:
				return "dyeWhite";
			case YELLOW:
				return "dyeYellow";
		}
		return "";
	}
	
	public static EnumDyeColor getColorFromOreDictName(String s){
		if(s.startsWith("dyeWhite")){
			return EnumDyeColor.WHITE;
		}else if(s.startsWith("dyeOrange")){
			return EnumDyeColor.ORANGE;
		}else if(s.startsWith("dyeMagenta")){
			return EnumDyeColor.MAGENTA;
		}else if(s.startsWith("dyeLightBlue")){
			return EnumDyeColor.LIGHT_BLUE;
		}else if(s.startsWith("dyeYellow")){
			return EnumDyeColor.YELLOW;
		}else if(s.startsWith("dyeLime")){
			return EnumDyeColor.LIME;
		}else if(s.startsWith("dyePink")){
			return EnumDyeColor.PINK;
		}else if(s.startsWith("dyeGray")){
			return EnumDyeColor.GRAY;
		}else if(s.startsWith("dyeLightGray")){
			return EnumDyeColor.SILVER;
		}else if(s.startsWith("dyeCyan")){
			return EnumDyeColor.CYAN;
		}else if(s.startsWith("dyePurple")){
			return EnumDyeColor.PURPLE;
		}else if(s.startsWith("dyeBlue")){
			return EnumDyeColor.BLUE;
		}else if(s.startsWith("dyeBrown")){
			return EnumDyeColor.BROWN;
		}else if(s.startsWith("dyeGreen")){
			return EnumDyeColor.GREEN;
		}else if(s.startsWith("dyeRed")){
			return EnumDyeColor.RED;
		}else if(s.startsWith("dyeBlack")){
			return EnumDyeColor.BLACK;
		}else{
			System.out.println("could not locate color from " + s + ". Returning white as a default");//TODO remove when confident this will never occur
			return EnumDyeColor.WHITE;
		}
	}
}
