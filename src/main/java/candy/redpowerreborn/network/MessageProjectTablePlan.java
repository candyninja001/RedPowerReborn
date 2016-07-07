package candy.redpowerreborn.network;

import candy.redpowerreborn.item.ItemPlan;
import candy.redpowerreborn.tileentity.TileEntityProjectTable;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.server.FMLServerHandler;

public class MessageProjectTablePlan implements IMessage, IMessageHandler<MessageProjectTablePlan, IMessage> {
	
	public BlockPos pos;
	public int dimension;
	
	public MessageProjectTablePlan() {}
	
	public MessageProjectTablePlan(BlockPos pos, int dimension) {
		this.pos = pos;
		this.dimension = dimension;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		pos = new BlockPos(ByteBufUtils.readVarInt(buf, 5), ByteBufUtils.readVarInt(buf, 5), ByteBufUtils.readVarInt(buf, 5));
		dimension = ByteBufUtils.readVarInt(buf, 5);
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeVarInt(buf, pos.getX(), 5);
		ByteBufUtils.writeVarInt(buf, pos.getY(), 5);
		ByteBufUtils.writeVarInt(buf, pos.getZ(), 5);
		ByteBufUtils.writeVarInt(buf, dimension, 5);
	}

	@Override
	public IMessage onMessage(MessageProjectTablePlan message, MessageContext ctx) {
		// code for client FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.pos);
		// code for server ctx.getServerHandler().playerEntity.worldObj.getTileEntity(message.pos);
		//DimensionManager.getWorld(message.dimension)
		TileEntity tileEntity = ctx.getServerHandler().playerEntity.worldObj.getTileEntity(message.pos);

        if (tileEntity instanceof TileEntityProjectTable)
        {
        	ItemPlan.writeToNBT(((TileEntityProjectTable) tileEntity).getStackInSlot(27), ((TileEntityProjectTable) tileEntity).getCraftingStacks(), ((TileEntityProjectTable) tileEntity).getResult());
            //((TileEntityProjectTable) tileEntity).recordPlan(((TileEntityProjectTable) tileEntity).getStackInSlot(27));
            tileEntity.markDirty();
            tileEntity.validate();
            IBlockState state = FMLClientHandler.instance().getClient().theWorld.getBlockState(message.pos);
            FMLClientHandler.instance().getClient().theWorld.notifyBlockUpdate(message.pos, state, state, 3);
        }
        
        return null;
	}
	
	@Override
    public String toString()
    {
        return String.format("MessageProjectTablePlan - x:%s, y:%s, z:%s", pos.getX(), pos.getY(), pos.getZ());
    }
}