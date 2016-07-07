package candy.redpowerreborn.network;

import candy.redpowerreborn.RedPower;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkSetup {
    public static void init(FMLInitializationEvent event) {
		RedPower.network.registerMessage(MessageAssemblerUpdate.class, MessageAssemblerUpdate.class, 0, Side.SERVER);// Last value is the side that is to receives the message
		RedPower.network.registerMessage(MessageProjectTablePlan.class, MessageProjectTablePlan.class, 1, Side.SERVER);
	}
}