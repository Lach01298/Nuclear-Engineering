package lach_01298.nuclear_engineering.gui;

import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

public class NEGUI extends GuiScreen
{
	/** draws the hover label
	 * @param gui */

			

	
	
	public static boolean isMouseInArea(int mouseX, int mouseY, int x, int y, int width, int height)
	{
		if(mouseX >= x && mouseX <= x + width)
		{
			if(mouseY >= y && mouseY <= y + height)
			{
				return true;
			}
		}
		
		return false;
	}
	
	
	/**draws the energy bar with scaling */
	public  void drawEnergyBar(int x,int y,int width, int hight,int textureX,int textureY,int energy,int maxEnergy)
	{
		float barHeight = (float)hight;
		drawTexturedModalRect(x, y-scaleBar(energy,maxEnergy,barHeight), textureX, textureY-scaleBar(energy,maxEnergy,barHeight), width, scaleBar(energy,maxEnergy,barHeight));
	}
	
	
	public  void drawVerticalBar(int x,int y,int width, int hight,int textureX,int textureY,int energy,int maxEnergy)
	{
		float barHeight = (float)hight;
		drawTexturedModalRect(x, y, textureX, textureY, width, scaleBar(energy,maxEnergy,barHeight));
	}

	protected  int scaleBar(int value,int maxValue,float barHeight)
	{
		return (int)(barHeight* value/maxValue); 
	}
	
	public  void drawProgressBar(int x,int y,int width, int hight,int textureX,int textureY,int progress,int completeProgress)
	{
		float barWidth = (float)width;
		drawTexturedModalRect(x,y,textureX,textureY,scaleBar(progress, completeProgress, barWidth),hight);
	}
	
	public void drawQuad(int x, int y, int width, int height, float minU, float minV, float maxU, float maxV)
	{
		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer buffer = tessellator.getBuffer();
		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		buffer.pos((double) x, (double) (y + height), (double) this.zLevel).tex(minU, maxV).endVertex();
		buffer.pos((double) (x + width), (double) (y + height), (double) this.zLevel).tex(maxU, maxV).endVertex();
		buffer.pos((double) (x + width), (double) y, (double) this.zLevel).tex(maxU, minV).endVertex();
		buffer.pos((double) x, (double) y, (double) this.zLevel).tex(minU, minV).endVertex();
		tessellator.draw();
	}
	
}
