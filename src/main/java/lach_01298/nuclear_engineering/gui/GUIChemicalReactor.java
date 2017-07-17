package lach_01298.nuclear_engineering.gui;

import java.util.ArrayList;
import java.util.List;

import lach_01298.nuclear_engineering.NuclearEngineering;
import lach_01298.nuclear_engineering.tile.TileEntityNE;
import lach_01298.nuclear_engineering.tile.machine.TileEntityChemicalReactor;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class GUIChemicalReactor extends GuiContainer 
{

	public static final int WIDTH = 176;
    public static final int HEIGHT = 189;
    public TileEntityChemicalReactor tile;
    public NEGUI gui = new NEGUI();
    public NEGUIFluid guiF = new NEGUIFluid();
	
	private static final ResourceLocation background = new ResourceLocation(NuclearEngineering.MOD_ID, "textures/gui/chemicalReactor.png");
	
	public GUIChemicalReactor(TileEntityNE tileEntity, Container inventorySlotsIn)
	{
		super(inventorySlotsIn);
		this.tile = (TileEntityChemicalReactor) tileEntity;
        xSize = WIDTH;
        ySize = HEIGHT;
	}

	@Override
	  protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	  {
		//draws the energy bar tool tip
		if(gui.isMouseInArea(mouseX, mouseY, guiLeft+16, guiTop+16, 16, 52))
		{
			List<String> labelEnergy = new ArrayList<String>();
			labelEnergy.add(I18n.format(GUI_ID.EnergyDisplay, tile.getEnergyStorage().getEnergyStored()));
			
			drawHoveringText(labelEnergy, mouseX-guiLeft, mouseY-guiTop);
			
		}
		
		//draws input tanks bar tool tip
		if(gui.isMouseInArea(mouseX, mouseY, guiLeft+53, guiTop+34, 16, 47))
		{
		
			List<String> labelTank = new ArrayList<String>();
			if(tile.getTank(0).getFluid() != null)
			{
				labelTank.add(I18n.format(GUI_ID.FluidDisplay, tile.getTank(0).getFluidAmount()));
				labelTank.add(tile.getTank(0).getFluid().getLocalizedName());
				drawHoveringText(labelTank, mouseX-guiLeft, mouseY-guiTop);
			}
			
		}
		//draws output tanks bar tool tip
		if(gui.isMouseInArea(mouseX, mouseY, guiLeft+97, guiTop+34, 16, 47))
		{
			List<String> labelTank = new ArrayList<String>();
			
			if(tile.getTank(1).getFluid() != null)
			{
				labelTank.add(I18n.format(GUI_ID.FluidDisplay, tile.getTank(1).getFluidAmount()));
				labelTank.add(tile.getTank(1).getFluid().getLocalizedName());
				drawHoveringText(labelTank, mouseX-guiLeft, mouseY-guiTop);
			}
			
			
		}
		
		
		
	  }
	
	
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		//draws the background
		mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
        //energy energy bar
        gui.drawEnergyBar(guiLeft+16, guiTop+68, 16, 52, 176, 90, tile.getEnergyStorage().getEnergyStored() , tile.getEnergyStorage().getMaxEnergyStored());
		
        //draws progress bar
        gui.drawProgressBar(guiLeft+70, guiTop+20,27,38,176,0,tile.getWork(),tile.getMaxWork());
		
		
		//draws tanks
		//input tank
        guiF.drawTank(guiLeft+53, guiTop + 82, 16, 47, tile.getTank(0));
        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft+53, guiTop + 35, 176, 90, 6, 47); //draws the gauge over the tank
		//output Tank
		guiF.drawTank(guiLeft+97, guiTop + 82, 16, 47, tile.getTank(1));
		mc.getTextureManager().bindTexture(background);
		drawTexturedModalRect(guiLeft+97, guiTop + 35, 176, 90, 6, 47);
		
		
		
		//draws the text saying the machines speed and energy usage
		String speedString = I18n.format(GUI_ID.Speed, this.tile.getSpeed());
		String energyCostString = I18n.format(GUI_ID.RunningEnergy, this.tile.getRunningEnergy());
		fontRendererObj.drawString(speedString , guiLeft+115, guiTop+50, 4210752);
		fontRendererObj.drawString(energyCostString , guiLeft+115, guiTop+60, 4210752);
	}

}
