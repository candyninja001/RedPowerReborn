package candy.redpowerreborn.client.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import candy.redpowerreborn.RedPower;
import candy.redpowerreborn.inventory.ContainerAssembler;
import candy.redpowerreborn.network.MessageAssemblerUpdate;
import candy.redpowerreborn.tileentity.TileEntityAssembler;

public class GuiAssembler extends GuiContainer {
	
	private IInventory tileEntityAssembler;
	
	public GuiAssembler(InventoryPlayer inventoryPlayer, TileEntityAssembler tileEntity) {
		// the container is instantiated and passed to the superclass for
		// handling
		super(new ContainerAssembler(inventoryPlayer, tileEntity));
		tileEntityAssembler = tileEntity;
		ySize = 195;
	}
	
	@Override
	public void initGui() {
		super.initGui(); // make buttons //id, x, y, width, height, text
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		int modeStart = tileEntityAssembler.getField(0);
		boolean[] activeSlots = ((TileEntityAssembler)tileEntityAssembler).getActiveSlots();
		buttonList.add(new GuiModeButton(0, x + 152, y + 37, 14, 14, modeStart));
		int i = 1;
		while (i < 8){
			buttonList.add(new GuiAssemblerSlotButton(i, x + 8 + i * 18, y + 18, 16, 16, !activeSlots[i], modeStart == 0));
			i++;
		}
		while (i < 16){
			buttonList.add(new GuiAssemblerSlotButton(i, x + 8 + (i - 8) * 18, y + 36, 16, 16, !activeSlots[i], modeStart == 0));
			i++;
		}
	}
	
	protected void actionPerformed(GuiButton guibutton) { // id is the id you give your button
		int mode = ((TileEntityAssembler) tileEntityAssembler).getField(0);
		int slots = ((TileEntityAssembler) tileEntityAssembler).getField(2);
		switch (guibutton.id) {
			case 0:
				mode = ((GuiModeButton)this.buttonList.get(0)).nextMode();
				//tileEntityAssembler.setField(0, mode);
				for (int i = 1; i < 16; i++){
					this.buttonList.get(i).visible = mode == 0;
				}
				break;
			default:
				boolean[] activeSlots = ((TileEntityAssembler)tileEntityAssembler).getActiveSlots();
				if(((TileEntityAssembler)tileEntityAssembler).getStackInSlot(guibutton.id) == null){
					activeSlots[guibutton.id] = !activeSlots[guibutton.id];
					//((GuiAssemblerSlotButton)this.buttonList.get(guibutton.id)).toggleHide();
				}else{
					activeSlots[guibutton.id] = true;
				}
				//((GuiAssemblerSlotButton)this.buttonList.get(guibutton.id)).toggleHide();
				slots = ((TileEntityAssembler)tileEntityAssembler).getActiveSlotsInt(activeSlots);
				break;
				//TODO send packet code to make this work
		} // Packet code here //
		RedPower.network.sendToServer(new MessageAssemblerUpdate(((TileEntityAssembler) this.tileEntityAssembler).getPos(), ((TileEntityAssembler) this.tileEntityAssembler).getWorld().provider.getDimension(), mode, slots));
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int param1, int param2) {
		// TODO
		// draw text and stuff here
		// the parameters for drawString are: string, x, y, color
		String s = I18n.format("container." + RedPower.MODID + "_assembler");
		fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
		// draws "Inventory" or your regional equivalent
		fontRendererObj.drawString(I18n.format("container.inventory"), 8, ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		// draw your Gui here, only thing you need to change is the path
		// int texture = mc.renderEngine.bindTexture("/gui/trap.png");
		
		//TODO find a better place for this code so it doesn't run every render
		boolean[] activeSlots = ((TileEntityAssembler)tileEntityAssembler).getActiveSlots();
		for (int i = 1; i < 16; i++){
			if(((TileEntityAssembler)tileEntityAssembler).getStackInSlot(i) != null)
				activeSlots[i] = true;
			((GuiAssemblerSlotButton)this.buttonList.get(i)).hide = activeSlots[i];
		}
		
		int mode = this.tileEntityAssembler.getField(0);
		if(mode != ((GuiModeButton) this.buttonList.get(0)).getMode()){
			((GuiModeButton) this.buttonList.get(0)).setMode(mode);
			for (int i = 1; i < 16; i++){
				this.buttonList.get(i).visible = mode == 0;
			}
		}
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		if (mode == 0) {
			this.mc.renderEngine.bindTexture(new ResourceLocation("redpowerreborn", "textures/gui/assembler.png"));
			this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
			int index = this.tileEntityAssembler.getField(1) % 16;
			if (index < 8)
				this.drawTexturedModalRect(x + 6 + index * 18, y + 16, 176, 0, 20, 20);
			else
				this.drawTexturedModalRect(x + 6 + (index - 8) * 18, y + 34, 176, 0, 20, 20);
		}
		if (mode == 1) {
			this.mc.renderEngine.bindTexture(new ResourceLocation("redpowerreborn", "textures/gui/assembler2.png"));
			this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
		}
		
	}
}