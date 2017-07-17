package lach_01298.nuclear_engineering.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

public class NEGUIFluid  extends NEGUI
{

	private static Minecraft minecraft = Minecraft.getMinecraft();
	
	
	
	public void drawTank(int x, int y, int width,int height, IFluidTank tank)
	{
		if(tank.getFluid() != null)
		{

			int capacity = tank.getCapacity();
			int level = tank.getFluidAmount();
			TextureAtlasSprite fluidTexture = minecraft.getTextureMapBlocks().getTextureExtry(tank.getFluid().getFluid().getStill().toString());
			if(fluidTexture == null)
			{
				System.out.println("could not find texture at "
						+ tank.getFluid().getFluid().getStill().toString()
						+ " for fluid " + (tank.getFluid().getFluid()));
				fluidTexture = minecraft.getTextureMapBlocks().getMissingSprite();
			}

			minecraft.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			int texHighet = fluidTexture.getIconHeight();
			int scaledHight = scaleBar(level, capacity, height);

			float minU = fluidTexture.getInterpolatedU(0);
			float minV = fluidTexture.getInterpolatedV(0);
			float maxU = fluidTexture.getInterpolatedU(16);
			float maxV = fluidTexture.getInterpolatedV(16);

			for(int i = 0; i < scaledHight / texHighet; i++)
			{
				drawQuad(x, y - scaledHight + (texHighet * i), texHighet, texHighet, minU, minV, maxU, maxV);
			}
			if(scaledHight % texHighet != 0)
			{
				drawQuad(x, y - (scaledHight % texHighet), texHighet, scaledHight
						% texHighet, minU, minV, maxU, fluidTexture.getInterpolatedV(scaledHight
						% texHighet));
			}

		}
	}
	
	
	
	
	
}
