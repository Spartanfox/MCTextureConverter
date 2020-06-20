/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mctextureconverter;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileFilter;
import java.nio.file.Path;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

/**
 *
 * @author benro
 */
public abstract class Globals {
    //being mr convinient over here
    public static File defaultPath;
    public static File minecraftPath;
    public static File bedrockPath;
    public static JTextArea console;
    public static JProgressBar progress;
    public static FileFilter pngFilter;
    public static FileFilter diffuseFilter;
    public static FileFilter normalFilter;
    public static FileFilter materialFilter;
    
    public static Dimension screen;
    public static void initialize(){
        System.out.println("Initalizing");
        
        screen = Toolkit.getDefaultToolkit().getScreenSize();

        minecraftPath = new File(System.getenv("APPDATA")+"/.minecraft");
        if(minecraftPath.exists()){
            System.out.println("Java Minecraft located at: "+minecraftPath);
        }else{
            minecraftPath = new File(System.getProperty("user.home")+"/Downloads");
        }
        defaultPath = new File(System.getProperty("user.home")+"/Documents");
        
        bedrockPath = new File(new File(System.getenv("APPDATA")).getParent()+"/Local/Packages/");
        File[] files = bedrockPath.listFiles((File f) -> {return f.getAbsolutePath().contains("Microsoft.Minecraft");});
        if(files.length>0){
            bedrockPath = files[0];
            System.out.println("Minecraft Bedrock located at: "+bedrockPath);
        }else{
            System.out.println("Couldn't find bedrock sosorri");
            bedrockPath = new File(System.getProperty("user.home")+"/Downloads");
        }
        
        //stinky filters
        
        pngFilter = (File f) -> {return f.getName().endsWith(".png");};
        
        materialFilter = (File f) -> {return f.getName().endsWith("_s.png");};
        
        normalFilter = (File f) -> {return f.getName().endsWith("_n.png");};
        
        diffuseFilter = (File f) -> {
            if(normalFilter.accept(f)||f.getName().endsWith("_o.png")||materialFilter.accept(f))
                return false;
            if(pngFilter.accept(f))
                return true;
            return false;
        };
    }

}
