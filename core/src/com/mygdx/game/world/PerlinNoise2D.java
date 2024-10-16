/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.world;

import com.mygdx.game.Constants;
import static java.lang.Math.abs;

/**
 *
 * @author Fery
 */
public class PerlinNoise2D {
        
    private final short standardFrequency = 1;
    private final short mountainFrequency = 5;
    private final double groundYOffsetStd = 1.5;
    private final double groundYOffsetMountain = 0.7;
    private final double TIME_STEP = 0.01;
    
    
    private double groundYOffset = groundYOffsetStd;
    private double time = 10;
    //private int noise = 0;
    private int[] noiseArr = new int[Constants.WIDTH_OF_MAP*2];
    private int[][] noiseArr2d;
        
    public int[] getNoiseArr(int height){
        return getNoiseArr(height, false);
    }
    
    public int[] getNoiseArr(int height, boolean createCave){
        double noise;
        double frequency = createCave ? 3 : standardFrequency;
        double dx = 0;
        int mountainXidx = (int )(Math.random() * 100) + 100;
        int mountainWidth = (int )(Math.random() * 50) + 90;
        if (createCave)
        {
            mountainXidx = 0;
            mountainWidth = Constants.WIDTH_OF_MAP;
            groundYOffset = 0.7;
            time = 10;
        }    
            
        System.out.println("x = " + mountainXidx + "|w = " + mountainWidth);
        
        //one index in noise are 2 blocks in map
        //so if mountainXidx = 103 it means that mountain start on 206th block in x axis
        //the same is for width
        for(int x = 0; x < (createCave ? noiseArr.length : Constants.WIDTH_OF_MAP/2); x++)
        {
            dx = (double) x / height;

            //from mountain to flat 
            if (x > mountainXidx+mountainWidth && frequency == mountainFrequency){
                frequency = standardFrequency;
                time -= (dx * standardFrequency)-dx*mountainFrequency + TIME_STEP;
                
                mountainXidx = (int )(Math.random() * 150) + 150 + x;
                mountainWidth = (int )(Math.random() * 100) + 100;
                System.out.println("x = " + mountainXidx + "|w = " + mountainWidth);
            }
            //from flat to mountain
            else if (x > mountainXidx && frequency == standardFrequency){
                frequency = mountainFrequency;
                time -= (dx * mountainFrequency)-dx*standardFrequency + TIME_STEP;
            }
            
            if (frequency == standardFrequency){
                if (groundYOffset < groundYOffsetStd)
                    groundYOffset += 0.03;    //y = (sin (x/(2*pi)) * 0.8)
            }
            else if (groundYOffset > groundYOffsetMountain)
                    groundYOffset -= 0.03;
            
            noise = noise((dx * frequency) + time, 0.5) + groundYOffset;
            //System.out.println("x = " + x + ", noise = " + (noise-groundYOffset));
            int y = abs((int)(noise*20))%height;
            //System.out.println(x + "|" + y + "|" + time);
            //image.setRGB(x, noise, 255);
            noiseArr[x] = height-y-1;
            
            time += TIME_STEP;
        }

    	return noiseArr;
    }
    
    
    public int[][] getNoiseArr2d(int width, int height, boolean createCave){

        noiseArr2d = new int[width][height];
        time = 0;
        
        //one index in noise are 2 blocks in map
        //so if mountainXidx = 103 it means that mountain start on 206th block in x axis
        //the same is for width
        for(int y = 0; y < height; y++)
        {
            for(int x = 0; x < width*2; x++)
            {
                double dx = (double) x / width;//height;
                double dy = (double) y / height;//width;
                int frequency = 12;
                double noise = 0;
                int octaves = 1;
                for (int i = 0; i < octaves; i++){
                //double noise = noise((dx * frequency) + time, (dy * frequency) + time);
                noise += noise((dx * frequency), (dy * frequency));
                //noise = (noise - 1) / 2;
                //System.out.println(x + "|" + y + "|noise = " + noise);
                /*int b = (int)(noise * 0xFF);
                int g = b * 0x100;
                int r = b * 0x10000;
                int finalValue = r;*/
                frequency +=1;
                }
                noise = noise/octaves;
                //System.out.println(x/2 + "|" + y + "|noise = " + noise);
                noiseArr2d[x/2][y] = (noise >= -0.25f && noise <= 0.8f ? -1 : 1);
                //noiseArr2d[x/2][y] = (noise >= -0.25f ? -1 : 1);
                //image.setRGB(x, y, finalValue);
                //time += TIME_STEP;
            }
    	}

    	return noiseArr2d;
    }
    

    
    
    
    
    
    private static double noise(double x, double y){
    	int xi = (int) Math.floor(x) & 255;
    	int yi = (int) Math.floor(y) & 255;
    	int g1 = p[p[xi] + yi];
    	int g2 = p[p[xi + 1] + yi];
    	int g3 = p[p[xi] + yi + 1];
    	int g4 = p[p[xi + 1] + yi + 1];
    	
    	double xf = x - Math.floor(x);
    	double yf = y - Math.floor(y);
    	
    	double d1 = grad(g1, xf, yf);
    	double d2 = grad(g2, xf - 1, yf);
    	double d3 = grad(g3, xf, yf - 1);
    	double d4 = grad(g4, xf - 1, yf - 1);
    	
    	double u = fade(xf);
    	double v = fade(yf);
    	
    	double x1Inter = lerp(u, d1, d2);
    	double x2Inter = lerp(u, d3, d4);
    	double yInter = lerp(v, x1Inter, x2Inter);
    	
    	return yInter;
    	
    }
    
    private static double lerp(double amount, double left, double right) {
		return ((1 - amount) * left + amount * right);
	}
    
    private static double fade(double t) { 
    	return t * t * t * (t * (t * 6 - 15) + 10); 
    }
    
    private static double grad(int hash, double x, double y){
    	switch(hash & 3){
    	case 0: return x + y;
    	case 1: return -x + y;
    	case 2: return x - y;
    	case 3: return -x - y;
    	default: return 0;
    	}
    }
    static final int p[] = new int[512], permutation[] = { 151,160,137,91,90,15,
    		   131,13,201,95,96,53,194,233,7,225,140,36,103,30,69,142,8,99,37,240,21,10,23,
    		   190, 6,148,247,120,234,75,0,26,197,62,94,252,219,203,117,35,11,32,57,177,33,
    		   88,237,149,56,87,174,20,125,136,171,168, 68,175,74,165,71,134,139,48,27,166,
    		   77,146,158,231,83,111,229,122,60,211,133,230,220,105,92,41,55,46,245,40,244,
    		   102,143,54, 65,25,63,161, 1,216,80,73,209,76,132,187,208, 89,18,169,200,196,
    		   135,130,116,188,159,86,164,100,109,198,173,186, 3,64,52,217,226,250,124,123,
    		   5,202,38,147,118,126,255,82,85,212,207,206,59,227,47,16,58,17,182,189,28,42,
    		   223,183,170,213,119,248,152, 2,44,154,163, 70,221,153,101,155,167, 43,172,9,
    		   129,22,39,253, 19,98,108,110,79,113,224,232,178,185, 112,104,218,246,97,228,
    		   251,34,242,193,238,210,144,12,191,179,162,241, 81,51,145,235,249,14,239,107,
    		   49,192,214, 31,181,199,106,157,184, 84,204,176,115,121,50,45,127, 4,150,254,
    		   138,236,205,93,222,114,67,29,24,72,243,141,128,195,78,66,215,61,156,180
    		   };
    		   static { for (int i=0; i < 256 ; i++) p[256+i] = p[i] = permutation[i]; }

}
