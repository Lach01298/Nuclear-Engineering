package lach_01298.nuclear_engineering.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import lach_01298.nuclear_engineering.NuclearEngineering;
import lach_01298.nuclear_engineering.tile.TileEntityNE;
import lach_01298.nuclear_engineering.tile.machine.TileEntityChemicalReactor;
import lach_01298.nuclear_engineering.tile.multiblock.TileEntityReactorCore;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class GUIFissionReactor extends GuiScreen
{
	public static final int WIDTH = 176;
    public static final int HEIGHT = 189;
  
    
    
    public NEGUI gui = new NEGUI();
	
    private int guiLeft;
	private int guiTop;
	private TileEntityReactorCore tile;
    
	private static final ResourceLocation background = new ResourceLocation(NuclearEngineering.MOD_ID, "textures/gui/fissionReactor.png");
	
	
	public GUIFissionReactor(TileEntityReactorCore tile)
	{
		this.tile = tile;
	}

	
	public void initGui()
    {
        super.initGui();
       
        this.guiLeft = (this.width - this.WIDTH) / 2;
        this.guiTop = (this.height - this.HEIGHT) / 2;
    }
	
	
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		// draws the background
		this.drawDefaultBackground();
		mc.getTextureManager().bindTexture(background);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, WIDTH, HEIGHT);

		// fuel bar

		gui.drawEnergyBar(guiLeft + 68, guiTop + 129, 13, 108, 183, 108, tile.getFuelLeft(), 100);
		gui.drawEnergyBar(guiLeft + 96, guiTop + 129, 13, 108, 183, 108, tile.getFuelLeft(), 100);
		// control rod

		int cRodY = guiTop + 21;
		gui.drawVerticalBar(guiLeft + 57, cRodY, 7, 106, 176, 0, (int) (tile.getControlrodLevel() * 100.0), 100);
		gui.drawVerticalBar(guiLeft + 85, cRodY, 7, 106, 176, 0, (int) (tile.getControlrodLevel() * 100.0), 100);
		gui.drawVerticalBar(guiLeft + 113, cRodY, 7, 106, 176, 0, (int) (tile.getControlrodLevel() * 100.0), 100);

		// info
		String temprature = I18n.format(GUI_ID.ReactorTemp, this.tile.getTemprature());
		String neutronflux = I18n.format(GUI_ID.NeutronFlux, this.tile.getNeutronNumber());
		String reflectorFactor = I18n.format(GUI_ID.ReflectorFactor, this.tile.getReflectorFactor()*100);
		String moderatorFactor = I18n.format(GUI_ID.ModeratorFactor, this.tile.getModeratorFactor());
		String rSize = I18n.format(GUI_ID.ReactorSize, this.tile.getSize());
		

		if(gui.isMouseInArea(mouseX, mouseY, guiLeft + 68, guiTop + 21, 13, 108)|| gui.isMouseInArea(mouseX, mouseY, guiLeft + 96, guiTop + 21, 13, 108))
		{
			
			List<String> labelFuel = new ArrayList<String>();

			labelFuel.add(I18n.format(GUI_ID.FissionFuel, tile.getFuelLeft(),tile.getFuelName()));
			
			drawHoveringText(labelFuel, mouseX, mouseY);

		}

		if(gui.isMouseInArea(mouseX, mouseY, guiLeft + 57, cRodY, 7, 106)|| gui.isMouseInArea(mouseX, mouseY,  guiLeft + 85, cRodY, 7, 106)||gui.isMouseInArea(mouseX, mouseY,  guiLeft + 113, cRodY, 7, 106))
		{

			List<String> labelControlRod = new ArrayList<String>();

			labelControlRod.add(I18n.format(GUI_ID.ControlRod, tile.getControlrodLevel() * 100));
			drawHoveringText(labelControlRod, mouseX, mouseY);

		}

		int textX = ((guiLeft + 10) * 10) / 7;
		int textY = ((guiTop + 140) * 10) / 7;

		GlStateManager.scale(0.7, 0.7, 0.7);
		fontRendererObj.drawString(temprature, textX, textY, 4210752);
		fontRendererObj.drawString(neutronflux, textX, textY + 10, 4210752);
		fontRendererObj.drawString(reflectorFactor, textX, textY + 20, 4210752);
		fontRendererObj.drawString(moderatorFactor, textX, textY + 30, 4210752);
		fontRendererObj.drawString(rSize, textX, textY + 40, 4210752);
		

	}
	
	public boolean doesGuiPauseGame()
	{
		return false;
	}
	
	
}
