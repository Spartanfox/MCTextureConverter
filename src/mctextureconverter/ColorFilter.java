/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mctextureconverter;

/**
 *
 * @author benro
 */
public class ColorFilter {
    
    
    public int inMin;
    public int inMax;
    public int outMin;
    public int outMax;
    public int channel;
    public static ColorFilter red = new ColorFilter(1,0,255,0,255);
    public static ColorFilter green = new ColorFilter(2,0,255,0,255);
    public static ColorFilter greenInverted = new ColorFilter(2,0,255,255,0); //used to flip normals
    public static ColorFilter blue = new ColorFilter(3,0,255,0,255);
    public ColorFilter(int channel, int inMin,int inMax,int outMin, int outMax){
        this.channel = channel;
        this.inMin = inMin;
        this.inMax = inMax;
        this.outMin = outMin;
        this.outMax = outMax;
    }
}
