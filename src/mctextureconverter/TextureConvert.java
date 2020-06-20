/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mctextureconverter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import static mctextureconverter.Helpers.map;

/**
 *
 * @author benro
 */
public class TextureConvert {
    
    int maxResolution;
    ColorFilter redFilter;
    ColorFilter greenFilter;
    ColorFilter blueFilter;
    public TextureConvert(int maxResolution,ColorFilter red,ColorFilter green,ColorFilter blue){
        
        this.maxResolution = maxResolution;
        
        this.redFilter = red;
        this.greenFilter = green;
        this.blueFilter = blue;
        
    }
    
    public BufferedImage convertTexture(BufferedImage image){
        
        int width = (image.getWidth()>maxResolution)? maxResolution : image.getWidth();
        int height = (image.getHeight()>maxResolution)? maxResolution : image.getHeight();
        
        //resize the image to the capped resolution
        BufferedImage img = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D)img.getGraphics();
        try{
            g.setBackground(new Color(0,0,0,0));
            g.clearRect(0,0, width, height);
            g.drawImage(image,0,0,img.getWidth(),img.getHeight(),null);
        }
        finally{
            g.dispose();
        }

        //then filter it
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                Color color = remapColors(new Color(img.getRGB(x,y),true));
                int[] colorArray = {0,color.getRed(),color.getGreen(),color.getBlue(),color.getAlpha()};            
                Color newColor = new Color(colorArray[redFilter.channel],colorArray[greenFilter.channel],colorArray[blueFilter.channel],color.getAlpha());
                img.setRGB(x, y,newColor.getRGB());
            }
        }
        return img;
    }
    
    public BufferedImage loadTexture(File f) throws IOException{
        return ImageIO.read(f);
    }
    
    public boolean saveTexture(BufferedImage image,String f) throws IOException{
        ImageIO.write(image,"png",new File(Helpers.filter(f)));
        return false;
    }
    
    public Color remapColors(Color color){
        int red = Helpers.map(color.getRed(),redFilter.inMin,redFilter.inMax,redFilter.outMin,redFilter.outMax);
        int green = Helpers.map(color.getGreen(),greenFilter.inMin,greenFilter.inMax,greenFilter.outMin,greenFilter.outMax);
        int blue = Helpers.map(color.getBlue(),blueFilter.inMin,blueFilter.inMax,blueFilter.outMin,blueFilter.outMax);
        return new Color(red,green,blue,color.getAlpha());
    }
}
