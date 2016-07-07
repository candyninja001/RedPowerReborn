package candy.redpowerreborn.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;

public class GuiModeButton extends GuiButton {
	
	private int mode;
	
	private int modeCount = 2;
	
	public GuiModeButton(int buttonId, int x, int y, int widthIn, int heightIn, int modeStart) {
		super(buttonId, x, y, widthIn, heightIn, "");
		this.mode = modeStart;
	}
	
	public int nextMode() {
		mode++;
		if (mode == modeCount)
			mode = 0;
		return mode;
	}
	
	public int getMode(){
		return mode;
	}
	
	public void setMode(int mode){
		this.mode = mode;
	}
	
	/**
	 * Draws this button to the screen.
	 */
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		if (this.visible) {
			FontRenderer fontrenderer = mc.fontRendererObj;
			mc.getTextureManager().bindTexture(new ResourceLocation("redpowerreborn", "textures/gui/assembler.png"));
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
			int i = this.getHoverState(this.hovered);
			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
			this.drawTexturedModalRect(this.xPosition, this.yPosition, 196, mode*14, this.width, this.height);
			
			
//			this.mouseDragged(mc, mouseX, mouseY);
//			int j = 14737632;
//			
//			if (packedFGColour != 0) {
//				j = packedFGColour;
//			} else if (!this.enabled) {
//				j = 10526880;
//			} else if (this.hovered) {
//				j = 16777120;
//			}
			
			// this.drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, j);
		}
	}
	
	/**
	 * Fired when the mouse button is dragged. Equivalent of MouseListener.mouseDragged(MouseEvent e).
	 */
	protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {}
	
	/**
	 * Fired when the mouse button is released. Equivalent of MouseListener.mouseReleased(MouseEvent e).
	 */
	public void mouseReleased(int mouseX, int mouseY) {}
	
	/**
	 * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent
	 * e).
	 */
	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
		return this.enabled && this.visible && mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
	}
	
	/**
	 * Whether the mouse cursor is currently over the button.
	 */
	public boolean isMouseOver() {
		return this.hovered;
	}
	
	public void drawButtonForegroundLayer(int mouseX, int mouseY) {}
	
	public void playPressSound(SoundHandler soundHandlerIn) {
		// soundHandlerIn.playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.ui_button_click, 1.0F));
	}
	
	public int getButtonWidth() {
		return this.width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
}
