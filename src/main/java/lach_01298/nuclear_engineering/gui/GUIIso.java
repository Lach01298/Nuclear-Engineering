package lach_01298.nuclear_engineering.gui;

import java.util.ArrayList;
import java.util.List;

import lach_01298.nuclear_engineering.NuclearEngineering;
import lach_01298.nuclear_engineering.container.machine.ContainerGrinder;
import lach_01298.nuclear_engineering.container.machine.ContainerIso;
import lach_01298.nuclear_engineering.tile.machine.TileEntityGrinder;
import lach_01298.nuclear_engineering.tile.machine.TileEntityIso;
import lach_01298.nuclear_engineering.tile.TileEntityNE;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

public class GUIIso extends GuiContainer {
    public static final int WIDTH = 176;
    public static final int HEIGHT = 201;
    public TileEntityIso tile;
    public NEGUI gui = new NEGUI();
    
    private static final ResourceLocation background = new ResourceLocation(NuclearEngineering.MOD_ID, "textures/gui/IsotopeSeperator.png");

    public GUIIso(TileEntityNE tileEntity, ContainerIso container) {
        super(container);
        this.tile = (TileEntityIso) tileEntity;
        xSize = WIDTH;
        ySize = HEIGHT;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
       /* 
    	
    	
    	mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		gui.drawEnergyBar(guiLeft+16, guiTop+68, 16, 52, 176, 79, this.tile.getEnergyStored(EnumFacing.UP) , this.tile.getMaxEnergyStored(EnumFacing.UP));
		gui.drawProgressBar(guiLeft+77, guiTop+29,27,27,176,0,this.tile.getWork(),this.tile.getMaxWork());
		List<String> labelEnergy = new ArrayList<String>();
    	
		//energy storage
		labelEnergy.add(I18n.format(GUI_ID.EnergyDisplay, this.tile.getEnergyStored(EnumFacing.UP)));
		
		if(gui.isMouseInArea(mouseX, mouseY, guiLeft+16, guiTop+16, 16, 52))
		{
			drawHoveringText(labelEnergy, mouseX, mouseY);
		}
		
		
		List<String> labelUpgrades = new ArrayList<String>();
		labelUpgrades.add(I18n.format(GUI_ID.RunningEnergy, this.tile.getRunningEnergy()));
		labelUpgrades.add(I18n.format(GUI_ID.Speed, this.tile.getSpeed()));
		
		
		if(gui.isMouseInArea(mouseX, mouseY, guiLeft+132, guiTop+7, 32, 16))
		{
			
			drawHoveringText(labelUpgrades, mouseX, mouseY);
		}
		*/
	
    }

  


}
