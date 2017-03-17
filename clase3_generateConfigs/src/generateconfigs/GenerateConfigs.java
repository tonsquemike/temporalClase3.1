/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generateconfigs;

import Funciones.Archivos;
import Funciones.MyListArgs;
import Funciones.MySintaxis;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.util.concurrent.ThreadLocalRandom;
/**
 *
 * @author mike
 */
public class GenerateConfigs {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String   ConfigFile = "";
        MyListArgs    Param     ;
        int ITER;
        String OUT;
        String OUT_JAR;
        Param      = new MyListArgs(args)                  ;
        ConfigFile = Param.ValueArgsAsString("-CONFIG", "");

        if (!ConfigFile.equals(""))
        {
            Param.AddArgsFromFile(ConfigFile);
        }//fin if


        String Sintaxis   = "[-OUT:str] -ITER:int -OUT_JAR:str";
        MySintaxis Review = new MySintaxis(Sintaxis, Param);


        ITER            = Param.ValueArgsAsInteger("-ITER"         , 10);
        OUT            = Param.ValueArgsAsString   ("-OUT"          , "");
        OUT_JAR  = Param.ValueArgsAsString   ("-OUT_JAR", "");
        
        new File(OUT_JAR).mkdirs();
        
        if(OUT.equals(""))
            OUT = "configs.txt";
        
        int rand;
        BufferedWriter bw = Archivos.newBuffer(OUT);
        String config;
        
        for (int i = 1; i < ITER; i++) {
            rand = ThreadLocalRandom.current().nextInt(0,100);
            //System.out.println("-OP1 "+i+" -OP2 "+rand+" -OUT sum"+i+".bin");
            config = i+" "+rand;
            Archivos.escribeArchivo(OUT_JAR+File.separator+"suma"+i+".txt", config);
        }
        Archivos.saveFile(bw);
    }
    
}
