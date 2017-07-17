package lach_01298.nuclear_engineering.gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import lach_01298.nuclear_engineering.NuclearEngineering;
import lach_01298.nuclear_engineering.container.machine.ContainerGrinder;
import lach_01298.nuclear_engineering.tile.machine.TileEntityGrinder;
import lach_01298.nuclear_engineering.tile.TileEntityNE;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;

public class GUIGrinder extends GuiContainer {
    public static final int WIDTH = 176;
    public static final int HEIGHT = 166;
    public TileEntityGrinder tile;
    public NEGUI gui = new NEGUI();
    
    private static final ResourceLocation background = new ResourceLocation(NuclearEngineering.MOD_ID, "textures/gui/grinder.png");

    public GUIGrinder(TileEntityNE tileEntity, ContainerGrinder container) {
        super(container);
        this.tile = (TileEntityGrinder) tileEntity;
        xSize = WIDTH;
        ySize = HEIGHT;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        
    	
    	
    	mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		gui.drawEnergyBar(guiLeft+16, guiTop+68, 16, 52, 176, 79, tile.getEnergyStorage().getEnergyStored() , tile.getEnergyStorage().getMaxEnergyStored());
		gui.drawProgressBar(guiLeft+77, guiTop+29,27,27,176,0,tile.getWork(),tile.getMaxWork());
		List<String> labelEnergy = new ArrayList<String>();
    	
		//energy storage
		labelEnergy.add(I18n.format(GUI_ID.EnergyDisplay, tile.getEnergyStorage().getEnergyStored()));
		
		if(gui.isMouseInArea(mouseX, mouseY, guiLeft+16, guiTop+16, 16, 52))
		{
			drawHoveringText(labelEnergy, mouseX, mouseY);
		}
		
		
		
		String speedString = I18n.format(GUI_ID.Speed, this.tile.getSpeed());
		String energyCostString = I18n.format(GUI_ID.RunningEnergy, this.tile.getRunningEnergy());
		
		fontRendererObj.drawString(speedString , guiLeft+110, guiTop+61, 4210752);
		fontRendererObj.drawString(energyCostString , guiLeft+110, guiTop+71, 4210752);
		
	
    }

  


}
