package lach_01298.nuclear_engineering.world.radiation;

import java.util.Map.Entry;

import lach_01298.nuclear_engineering.util.VectorRay;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;

public class RadiationSource
{
	private float alphaRadiation;
	private float betaRadiation;
	private float gammaRadiation;
	private float neutronRadiation;
	
	
	
	
	public RadiationSource(float alpha, float beta, float gamma, float neutron)
	{
		this.alphaRadiation = alpha;
		this.betaRadiation = beta;
		this.gammaRadiation = gamma;
		this.neutronRadiation = neutron;
	}
	
	
	
	
	public RadiationSource()
	{
		this.alphaRadiation = 0;
		this.betaRadiation = 0;
		this.gammaRadiation = 0;
		this.neutronRadiation = 0;
	}




	//inverse square law hybrid with penetration equation 
	public float getStrenghAlpha(VectorRay ray)
	{
		double distance = 0;
		double totalabsorbance = 1;
		for(Entry<IBlockState, Double> path : ray.getBlocksInRayPathWithDistance().entrySet())
		{
			
			distance += path.getValue();
			totalabsorbance *= Math.pow(Math.E, -RadiationBlocker.getAlphaCoefient(path.getKey()) * path.getValue());
		}
		return (float) ((this.alphaRadiation * totalabsorbance) / (distance * distance));
	}

	public float getStrenghBeta(VectorRay ray)
	{
		double distance = 0;
		double totalabsorbance = 1;
		for(Entry<IBlockState, Double> path : ray.getBlocksInRayPathWithDistance().entrySet())
		{
			
			distance += path.getValue();
			totalabsorbance *= Math.pow(Math.E, -RadiationBlocker.getBetaCoefient(path.getKey()) * path.getValue());
		}

		return (float) ((this.betaRadiation * totalabsorbance) / (distance * distance));
	}

	public float getStrenghGamma(VectorRay ray)
	{
		double distance = 0;
		double totalabsorbance = 1;
		for(Entry<IBlockState, Double> path : ray.getBlocksInRayPathWithDistance().entrySet())
		{
			distance += path.getValue();
			totalabsorbance *= Math.pow(Math.E, -RadiationBlocker.getGammaCoefient(path.getKey()) * path.getValue());
		}
		
		return (float) ((this.gammaRadiation * totalabsorbance) / (distance * distance));
	}

	public float getStrenghNeutron(VectorRay ray)
	{
		double distance = 0;
		double totalabsorbance = 1;
		for(Entry<IBlockState, Double> path : ray.getBlocksInRayPathWithDistance().entrySet())
		{
			distance += path.getValue();
			totalabsorbance *= Math.pow(Math.E, -RadiationBlocker.getNeutronCoefient(path.getKey()) * path.getValue());
		}

		return (float) ((this.neutronRadiation * totalabsorbance) / (distance * distance));
	}

	public void setAlpha(float value)
	{
		this.alphaRadiation = value;
	}
	public void setBeta(float value)
	{
		this.betaRadiation = value;
	}
	public void setGamma(float value)
	{
		this.gammaRadiation = value;
	}
	public void setNeutron(float value)
	{
		this.neutronRadiation = value;
	}
	
	public float getAlpha()
	{
		return this.alphaRadiation;
	}
	public float getBeta()
	{
		return this.betaRadiation;
	}
	public float getGamma()
	{
		return this.gammaRadiation;
	}
	public float getNeutron()
	{
		return this.neutronRadiation;
	}


	public void writeToNBT(NBTTagCompound tag)
	{
		tag.setFloat("alpha", this.alphaRadiation);
		tag.setFloat("beta", this.betaRadiation);
		tag.setFloat("gamma", this.gammaRadiation);
		tag.setFloat("neutron", this.neutronRadiation);
	}
	
	public void readfromNBT(NBTTagCompound tag)
	{
		this.alphaRadiation = tag.getFloat("alpha");
		this.betaRadiation = tag.getFloat("beta");
		this.gammaRadiation = tag.getFloat("gamma");
		this.neutronRadiation = tag.getFloat("neutron");
	}
	
	
	
	
	
}
