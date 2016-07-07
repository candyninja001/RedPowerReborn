package candy.redpowerreborn.item;

import com.google.common.collect.Multimap;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.util.EnumHelper;

public class ItemAthame extends ItemSword {
	
	public ItemAthame(){
		super(EnumHelper.addToolMaterial("ATHAME", 2, 500, 8.0F, -3.0F, 0));
	}
	
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
		//TODO perform check to reduce the bonus damage effect if the attackers AttackCooldown is not fully gone
		if(target instanceof EntityEnderman){
			target.attackEntityFrom(DamageSource.causeMobDamage(attacker), 20.0f);
		}
        return true;
    }
}
