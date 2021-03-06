package thutconcrete.client.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import thutconcrete.common.utils.IRebar;

public class RenderIRebar {

	public void renderREConcrete(IBlockAccess world, Block parblock, double x, double y, double z, int meta, boolean[] sides, Icon icon, Icon icon1, boolean rebar, boolean concrete, Icon[] icons)
    {
        Tessellator tessellator = Tessellator.instance;
        boolean animated = false;
       
        tessellator.setBrightness(parblock.getMixedBrightnessForBlock(world, (int)x, (int)y, (int)z));
        tessellator.setColorRGBA(255, 255, 255, 255);
        
        int j = 15-meta;
        double yCCmax =y+ ((1 + j)) / 16.0F;
        
        double xCCmin = 0+x, zCCmin = 0+z, yCCmin = 0+y, xCCmax = 1+x, zCCmax = 1+z;
        
        if(rebar)
        {
        	tessAddRebar(parblock,tessellator, icon1, x,y,z, sides, !concrete);
        }
        if(concrete)
        {
        	new RenderCuboid(tessellator, icons, xCCmin, zCCmin, yCCmin, xCCmax, zCCmax, yCCmax);
        }
    }
	
	private void tessAddRebar(Block parblock, Tessellator tessellator, Icon icon, double x, double y, double z, boolean[] sides, boolean justRebar){
		if(parblock instanceof IRebar)
		{
			boolean connected = false;
			boolean[] renderSides = new boolean[7];
			renderSides[6] = justRebar;
			double dl = justRebar?0.005:0;
			
			if(sides[0]&&sides[1]&&sides[2]&&sides[3]&&sides[4]&&sides[5])
			{
				crossColumnRebar(tessellator, icon, x, y, z,justRebar);
				return;
			}
				
			if(sides[0]&&sides[1]&&sides[2]&&sides[3]&&!sides[4]&&!sides[5])
			{
				connected = true;
				crossRebar(tessellator, icon, x, y, z,justRebar);
			}
			else
			{
				if(sides[0]&&!sides[1])
				{
					renderSides[0] = true;
					xHorizontalRebar(tessellator, icon, x, y, z, 1+dl, 0.4,renderSides);
					renderSides[0] = false;
					connected = true;
				}
				if(sides[1]&&!sides[0])
				{
					renderSides[1] = true;
					xHorizontalRebar(tessellator, icon, x, y, z, 0-dl, 0.6,renderSides);
					renderSides[1] = false;
					connected = true;
				}
				if(sides[1]&&sides[0])
				{
					renderSides[0] = true;
					renderSides[1] = true;
					xHorizontalRebar(tessellator, icon, x, y, z, 1+dl, 0-dl,renderSides);
					renderSides[0] = false;
					renderSides[1] = false;
					connected = true;
				}
				
				if(sides[2]&&!sides[3])
				{
					renderSides[2] = true;
					zHorizontalRebar(tessellator, icon, x, y, z, 1+dl, 0.4,renderSides);
					renderSides[2] = false;
					connected = true;
				}
				if(sides[3]&&!sides[2])
				{
					renderSides[3] = true;
					zHorizontalRebar(tessellator, icon, x, y, z, 0-dl, 0.6,renderSides);
					renderSides[3] = false;
					connected = true;
				}
				if(sides[3]&&sides[2])
				{
					renderSides[3] = true;
					renderSides[2] = true;
					zHorizontalRebar(tessellator, icon, x, y, z, 1+dl, 0-dl,renderSides);
					renderSides[3] = false;
					renderSides[2] = false;
					connected = true;
				}
			}
			
			if(sides[4]&&!sides[5])
			{
				renderSides[4] = true;
				columnRebar(tessellator, icon, x, y, z, 1+dl, 0.4,renderSides);
				connected = true;
			}
			if(sides[5]&&!sides[4])
			{
				renderSides[5] = true;
				columnRebar(tessellator, icon, x, y, z, 0-dl, 0.6,renderSides);
				connected = true;
			}
			if(sides[5]&&sides[4])
			{
				renderSides[4] = true;
				renderSides[5] = true;
				columnRebar(tessellator, icon, x, y, z, 1+dl, 0.0-dl,renderSides);
				connected = true;
			}
			
			
			if(!connected)
			{
				crossRebar(tessellator, icon, x, y, z, justRebar);
			}
		}
	}

	private void crossRebar(Tessellator tessellator, Icon icon, double x, double y, double z, boolean full){
		boolean[] sides = {true,true,false,false,false,false};
		double dl = full? 0.005:0;
		xHorizontalRebar(tessellator, icon, x, y, z, 1+dl,0-dl, sides);
		sides = new boolean[]{false,false,true,true,false,false};
		zHorizontalRebar(tessellator, icon, x, y, z, 1+dl,0-dl,sides);
	//	columnRebar(tessellator, icon, x, y, z,0.6,0.4,sides);
	}
	private void xHorizontalRebar(Tessellator tessellator, Icon icon, double x, double y, double z, double length, double min, boolean[] sides){

		double dT = 0.05;
		double dS = 0.1;
		double dl = 0.005D;
		
		double 	yMin = y+(0.5-dS-dT),
				xMin = x+dl+min,
				zMin = z+(0.5-dS-dT),
				yMax = y+(0.5+dS+dT),
				xMax = x+length-dl,
				zMax = z+(0.5+dS+dT);
		tessAddCuboid(tessellator, icon, xMin, zMin, yMin, xMax, zMax, yMax, sides);
		/*/
		double 	yMin = y+(0.5-dS-dT),
				xMin = x+dl+min,
				zMin = z+(0.5-dS-dT),
				yMax = y+(0.5-dS+dT),
				xMax = x+length-dl,
				zMax = z+(0.5-dS+dT);
		tessAddCuboid(tessellator, icon, xMin, zMin, yMin, xMax, zMax, yMax);
		
		yMin = y+(0.5+dS-dT);
		xMin = x+dl+min;
		zMin = z+(0.5+dS-dT);
		yMax = y+(0.5+dS+dT);
		xMax = x+length-dl;
		zMax = z+(0.5+dS+dT);
		tessAddCuboid(tessellator, icon, xMin, zMin, yMin, xMax, zMax, yMax);
		
		yMin = y+(0.5-dS-dT);
		xMin = x+dl+min;
		zMin = z+(0.5+dS-dT);
		yMax = y+(0.5-dS+dT);
		xMax = x+length-dl;
		zMax = z+(0.5+dS+dT);
		tessAddCuboid(tessellator, icon, xMin, zMin, yMin, xMax, zMax, yMax);
		
		yMin = y+(0.5+dS-dT);
		xMin = x+dl+min;
		zMin = z+(0.5-dS-dT);
		yMax = y+(0.5+dS+dT);
		xMax = x+length-dl;
		zMax = z+(0.5-dS+dT);
		tessAddCuboid(tessellator, icon, xMin, zMin, yMin, xMax, zMax, yMax);
		//*/
		
	}
	
	private void zHorizontalRebar(Tessellator tessellator, Icon icon, double x, double y, double z, double length, double min, boolean[] sides){

		double dT = 0.05;
		double dS = 0.1;
		double dl = 0.005D;
		
		double 	yMin = y+(0.5-dS-dT),
				zMin = z+dl+min,
				xMin = x+(0.5-dS-dT),
				yMax = y+(0.5+dS+dT),
				zMax = z+length-dl,
				xMax = x+(0.5+dS+dT);
		tessAddCuboid(tessellator, icon, xMin, zMin, yMin, xMax, zMax, yMax, sides);
		
		/*/
		double 	yMin = y+(0.5-dS-dT),
				zMin = z+dl+min,
				xMin = x+(0.5-dS-dT),
				yMax = y+(0.5-dS+dT),
				zMax = z+length-dl,
				xMax = x+(0.5-dS+dT);
		tessAddCuboid(tessellator, icon, xMin, zMin, yMin, xMax, zMax, yMax);
		
		yMin = y+(0.5+dS-dT);
		zMin = z+dl+min;
		xMin = x+(0.5+dS-dT);
		yMax = y+(0.5+dS+dT);
		zMax = z+length-dl;
		xMax = x+(0.5+dS+dT);
		tessAddCuboid(tessellator, icon, xMin, zMin, yMin, xMax, zMax, yMax);
		
		yMin = y+(0.5-dS-dT);
		zMin = z+dl+min;
		xMin = x+(0.5+dS-dT);
		yMax = y+(0.5-dS+dT);
		zMax = z+length-dl;
		xMax = x+(0.5+dS+dT);
		tessAddCuboid(tessellator, icon, xMin, zMin, yMin, xMax, zMax, yMax);
		
		yMin = y+(0.5+dS-dT);
		zMin = z+dl+min;
		xMin = x+(0.5-dS-dT);
		yMax = y+(0.5+dS+dT);
		zMax = z+length-dl;
		xMax = x+(0.5-dS+dT);
		tessAddCuboid(tessellator, icon, xMin, zMin, yMin, xMax, zMax, yMax);
		//*/
	}
	
	
	private void columnRebar(Tessellator tessellator, Icon icon, double x, double y, double z, double length, double min, boolean[] sides){

		double dT = 0.05;
		double dS = 0.1;
		double dl = 0.005D;
		
		double 	xMin = x+(0.5-dS-dT),
				yMin = y+dl+min,
				zMin = z+(0.5-dS-dT),
				xMax = x+(0.5+dS+dT),
				yMax = y+length-dl,
				zMax = z+(0.5+dS+dT);
		tessAddCuboid(tessellator, icon, xMin, zMin, yMin, xMax, zMax, yMax, sides);
		
		
		/*/
		double 	xMin = x+(0.5-dS-dT),
				yMin = y+dl+min,
				zMin = z+(0.5-dS-dT),
				xMax = x+(0.5-dS+dT),
				yMax = y+length-dl,
				zMax = z+(0.5-dS+dT);
		tessAddCuboid(tessellator, icon, xMin, zMin, yMin, xMax, zMax, yMax);
		
		xMin = x+(0.5+dS-dT);
		yMin = y+dl+min;
		zMin = z+(0.5+dS-dT);
		xMax = x+(0.5+dS+dT);
		yMax = y+length-dl;
		zMax = z+(0.5+dS+dT);
		tessAddCuboid(tessellator, icon, xMin, zMin, yMin, xMax, zMax, yMax);
		
		xMin = x+(0.5-dS-dT);
		yMin = y+dl+min;
		zMin = z+(0.5+dS-dT);
		xMax = x+(0.5-dS+dT);
		yMax = y+length-dl;
		zMax = z+(0.5+dS+dT);
		tessAddCuboid(tessellator, icon, xMin, zMin, yMin, xMax, zMax, yMax);
		
		xMin = x+(0.5+dS-dT);
		yMin = y+dl+min;
		zMin = z+(0.5-dS-dT);
		xMax = x+(0.5+dS+dT);
		yMax = y+length-dl;
		zMax = z+(0.5-dS+dT);
		tessAddCuboid(tessellator, icon, xMin, zMin, yMin, xMax, zMax, yMax);
		//*/
	}
	
	
	private void crossColumnRebar(Tessellator tessellator, Icon icon, double x, double y, double z, boolean full){

		crossRebar(tessellator, icon, x, y, z, full);
		boolean[] sides = {false,false,false,false,true,true};
		double dl = full?0.005:0;
		columnRebar(tessellator, icon, x, y, z,1+dl,0-dl,sides);
		
	}
	
	
	
	
	private void tessAddCuboid(Tessellator tessellator, Icon icon, double xMin, double zMin, double yMin, double xMax, double zMax, double yMax, boolean[] sides){
		
        double d0 = (double)icon.getMinU();
        double d1 = (double)icon.getMaxU();
        double d2 = (double)icon.getMaxU();
        double d3 = (double)icon.getMinV();
        double d4 = (double)icon.getMaxV();
        if(!sides[2])
        {
        ///////////////side1///////////////
        tessellator.addVertexWithUV(xMin, yMax, zMax, d0, d3);
        tessellator.addVertexWithUV(xMin, yMin, zMax, d0, d4);
        
        tessellator.addVertexWithUV(xMax, yMin, zMax, d1, d4);
        tessellator.addVertexWithUV(xMax, yMax, zMax, d1, d3);
        
        tessellator.addVertexWithUV(xMax, yMax, zMax, d0, d3);
        tessellator.addVertexWithUV(xMax, yMin, zMax, d0, d4);
        
        tessellator.addVertexWithUV(xMin, yMin, zMax, d1, d4);
        tessellator.addVertexWithUV(xMin, yMax, zMax, d1, d3);
		////////////////////////////////////////* /
        }
        if(!sides[0])
        {
        ///////////////side2///////////////
        tessellator.addVertexWithUV(xMax, yMax, zMin, d0, d3);
        tessellator.addVertexWithUV(xMax, yMin, zMin, d0, d4);
        
        tessellator.addVertexWithUV(xMax, yMin, zMax, d1, d4);
        tessellator.addVertexWithUV(xMax, yMax, zMax, d1, d3);
        
        tessellator.addVertexWithUV(xMax, yMax, zMax, d0, d3);
        tessellator.addVertexWithUV(xMax, yMin, zMax, d0, d4);
        
        tessellator.addVertexWithUV(xMax, yMin, zMin, d1, d4);
        tessellator.addVertexWithUV(xMax, yMax, zMin, d1, d3);
		////////////////////////////////////////* /
        }
        if(!sides[1])
        {
        ///////////////side3///////////////
        tessellator.addVertexWithUV(xMin, yMax, zMax, d0, d3);
        tessellator.addVertexWithUV(xMin, yMin, zMax, d0, d4);
        
        tessellator.addVertexWithUV(xMin, yMin, zMin, d1, d4);
        tessellator.addVertexWithUV(xMin, yMax, zMin, d1, d3);
        
        tessellator.addVertexWithUV(xMin, yMax, zMin, d0, d3);
        tessellator.addVertexWithUV(xMin, yMin, zMin, d0, d4);
        
        tessellator.addVertexWithUV(xMin, yMin, zMax, d1, d4);
        tessellator.addVertexWithUV(xMin, yMax, zMax, d1, d3);
		////////////////////////////////////////*/
        }
        if(!sides[3])
        {
        ///////////////side4///////////////
        tessellator.addVertexWithUV(xMax, yMax, zMin, d0, d3);
        tessellator.addVertexWithUV(xMax, yMin, zMin, d0, d4);
        
        tessellator.addVertexWithUV(xMin, yMin, zMin, d1, d4);
        tessellator.addVertexWithUV(xMin, yMax, zMin, d1, d3);
        
        tessellator.addVertexWithUV(xMin, yMax, zMin, d0, d3);
        tessellator.addVertexWithUV(xMin, yMin, zMin, d0, d4);
        
        tessellator.addVertexWithUV(xMax, yMin, zMin, d1, d4);
        tessellator.addVertexWithUV(xMax, yMax, zMin, d1, d3);
		////////////////////////////////////////*/
        }
        if(!sides[5])
        {
        ///////////////side5///////////////
        
        tessellator.addVertexWithUV(xMax, yMin, zMax, d0, d3);
        tessellator.addVertexWithUV(xMax, yMin, zMin, d0, d4);
        
        tessellator.addVertexWithUV(xMin, yMin, zMin, d1, d4);
        tessellator.addVertexWithUV(xMin, yMin, zMax, d1, d3);
       //* 
        
        tessellator.addVertexWithUV(xMin, yMin, zMax, d1, d4);
        tessellator.addVertexWithUV(xMin, yMin, zMin, d1, d3);
        
        tessellator.addVertexWithUV(xMax, yMin, zMin, d0, d3);
        tessellator.addVertexWithUV(xMax, yMin, zMax, d0, d4);
       
		////////////////////////////////////////*/   
        }
        if(!sides[4])
        {
        ///////////////side6///////////////
        
        tessellator.addVertexWithUV(xMax, yMax, zMax, d0, d3);
        tessellator.addVertexWithUV(xMax, yMax, zMin, d0, d4);
        
        tessellator.addVertexWithUV(xMin, yMax, zMin, d1, d4);
        tessellator.addVertexWithUV(xMin, yMax, zMax, d1, d3);
       //* 
        
        tessellator.addVertexWithUV(xMin, yMax, zMax, d1, d4);
        tessellator.addVertexWithUV(xMin, yMax, zMin, d1, d3);
        
        tessellator.addVertexWithUV(xMax, yMax, zMin, d0, d3);
        tessellator.addVertexWithUV(xMax, yMax, zMax, d0, d4);
       
		////////////////////////////////////////*/    
        }
	}

}
