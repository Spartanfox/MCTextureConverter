/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mctextureconverter;

import java.io.File;
import java.io.IOException;
import static java.lang.Integer.max;
import static java.lang.Integer.min;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import mctextureconverter.Globals;
import static mctextureconverter.Globals.console;
import static mctextureconverter.Globals.progress;
import mctextureconverter.TextureConvert;

/**
 *
 * @author benro
 */
public abstract class Helpers {
    public static void print(String text){
        console.append(text+"\n");
    }
    public static String filter(String name){
        name = name.replace("_n.png","_normal.png");
        name = name.replace("_s.png","_material.png");
        return name;
    }
    public static void clear(){
        console.setText("");
    }
    public static void print(JTextArea console, String text){
        console.append(text+"\n");
    }
    public static int map(int value,int inMin,int inMax,int outMin,int outMax){
        
        value = (int)(((double)value - (double)inMin) * ((double)outMax - (double)outMin) / ((double)inMax - (double)inMin) + (double)outMin);
        value = min(value,255);
        value = max(value,0);

        return value;
    }
    
    public static Thread convertJavaToBedrock(File from, File to,TextureConvert diffuseFilter,TextureConvert normalFilter,TextureConvert materialFilter){
        return new Thread(){
            @Override
            public void run(){
                try {
                    to.mkdirs();
                    clear();
                    print("Preparing to convert textures");
                    print(progress.getMaximum()+" textures that need to be converted");
                    print(from.listFiles(Globals.diffuseFilter).length+" base textures");
                    print(from.listFiles(Globals.normalFilter).length+" normals");
                    print(from.listFiles(Globals.materialFilter).length+" materials");

                    print("converting the base textures");
                    int count = 0;
                    progress.setMaximum(from.listFiles(Globals.diffuseFilter).length);
                    for (File f : from.listFiles(Globals.diffuseFilter)) {
                        progress.setValue(++count);
                        diffuseFilter.saveTexture(diffuseFilter.convertTexture(diffuseFilter.loadTexture(f)),to.getAbsoluteFile()+"\\"+f.getName());

                    }
                    print("converting the normals");
                    progress.setMaximum(from.listFiles(Globals.normalFilter).length);
                    count = 0;
                    for (File f : from.listFiles(Globals.normalFilter)) {
                        progress.setValue(++count);
                        normalFilter.saveTexture(normalFilter.convertTexture(normalFilter.loadTexture(f)),to.getAbsoluteFile()+"\\"+f.getName());
                    }
                    print("converting the materials");
                    progress.setMaximum(from.listFiles(Globals.materialFilter).length);
                    count=0;
                    for (File f : from.listFiles(Globals.materialFilter)) {
                        progress.setValue(++count);
                        materialFilter.saveTexture(materialFilter.convertTexture(materialFilter.loadTexture(f)),to.getAbsoluteFile()+"\\"+f.getName());
                    }
                    print("Donion rings the new textures can be found at:");
                    print(from.getAbsolutePath());
                    Runtime.getRuntime().exec("explorer.exe " + to.getAbsolutePath());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
    }
}
