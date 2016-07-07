package candy.redpowerreborn.network;

import candy.redpowerreborn.tileentity.TileEntityAssembler;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.state.IBlockState;
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

public class MessageAssemblerUpdate implements IMessage, IMessageHandler<MessageAssemblerUpdate, IMessage> {
	
	public BlockPos pos;
	public int dimension;
	public int selectionMode;
	public int activeSlots;
	
	public MessageAssemblerUpdate() {}
	
	public MessageAssemblerUpdate(BlockPos pos, int dimension, int selectionMode, int activeSlots) {
		this.pos = pos;
		this.dimension = dimension;
		this.selectionMode = selectionMode;
		this.activeSlots = activeSlots;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		pos = new BlockPos(ByteBufUtils.readVarInt(buf, 5), ByteBufUtils.readVarInt(buf, 5), ByteBufUtils.readVarInt(buf, 5));
		dimension = ByteBufUtils.readVarInt(buf, 5);
		selectionMode = ByteBufUtils.readVarInt(buf, 1);
		activeSlots = ByteBufUtils.readVarInt(buf, 3);// TODO 2 might not be right. Number of bytes needed to read
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeVarInt(buf, pos.getX(), 5);
		ByteBufUtils.writeVarInt(buf, pos.getY(), 5);
		ByteBufUtils.writeVarInt(buf, pos.getZ(), 5);
		ByteBufUtils.writeVarInt(buf, dimension, 5);
		ByteBufUtils.writeVarInt(buf, selectionMode, 1);
		ByteBufUtils.writeVarInt(buf, activeSlots, 3);
	}

	@Override
	public IMessage onMessage(MessageAssemblerUpdate message, MessageContext ctx) {
		// code for client FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.pos);
		// code for server ctx.getServerHandler().playerEntity.worldObj.getTileEntity(message.pos);
		//DimensionManager.getWorld(message.dimension)
		TileEntity tileEntity = ctx.getServerHandler().playerEntity.worldObj.getTileEntity(message.pos);

        if (tileEntity instanceof TileEntityAssembler)
        {
            ((TileEntityAssembler) tileEntity).setField(0, message.selectionMode);
            ((TileEntityAssembler) tileEntity).setField(2, message.activeSlots);
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
        return String.format("MessageAssemblerUpdate - x:%s, y:%s, z:%s, selectionMode:%s, activeSlots:%s", pos.getX(), pos.getY(), pos.getZ(), this.selectionMode, this.activeSlots);
    }
}