/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clase3_paraleliza;

import Funciones.Archivos;
import Funciones.MyListArgs;
import Funciones.MySintaxis;
import java.io.File;
import java.util.ArrayList;
import pkg00001_threads_lib.runHilos;

/**
 *
 * @author mike
 */
public class Paraleliza {
    int               CORES;
    String PATH_OUT;
    String           PATH;
    String             OUT;  
    String              JAR; 
    String                 IN;
    /**
     * @param args the command line arguments
     */
    public Paraleliza(String[]args)
    {
            String   ConfigFile = "";       
            MyListArgs    Param     ;

            Param      = new MyListArgs(args)                  ;
            ConfigFile = Param.ValueArgsAsString("-CONFIG", "");

            if (!ConfigFile.equals(""))
                Param.AddArgsFromFile(ConfigFile);

            String Sintaxis         = "(-IN:str|(-PATH:str -PATH_OUT:str)) -JAR:str -OUT:str";
            MySintaxis Review = new MySintaxis(Sintaxis, Param);//verifica que los argumentos recibidos cimplan la sintaxis declarada

            CORES = Runtime.getRuntime().availableProcessors();//extrae el número de nucleos
            //lectura de argumentos2
            PATH_OUT = Param.ValueArgsAsString("-PATH_OUT", "");
            PATH            = Param.ValueArgsAsString("-PATH"          , "");
            OUT              = Param.ValueArgsAsString("-OUT"            , "");
            JAR               = Param.ValueArgsAsString("-JAR"            , "");       
            IN                  = Param.ValueArgsAsString( "-IN"               , "");
            
            new File(OUT).mkdirs();//crea la ruta de salida
            new File(PATH_OUT).mkdirs();//crea la ruta de salida
    }
    public void process()
    {
        if(!IN.equals(""))
            this.runFromTxt();
        else if(!PATH.equals(""))
            this.runFromPath();;
    }
    public void runFromTxt(){
            String configs[] = Archivos.leerArchivoDeTexto(this.IN); 
            for (int i = 0; i < configs.length; i++) {
                System.out.println(configs[i]);
        }
            runHilos r = new runHilos(configs, "java -jar "+this.JAR, CORES);
            r.ejecuta();  
    }
    public void runFromPath(){
            String configs[];        
            String content;
            String files[];
            String OPs[];
            
            files       = new File(this.PATH).list();
            
            configs = new String[files.length];
            
            for (int i = 0; i < files.length; i++) {
                System.out.println(this.PATH+File.separator+files[i]);
                content   = Archivos.leerArchivoTexto(this.PATH+File.separator+files[i]);              
                OPs           = content.split(" ");
                configs[i] = " -OUT "+PATH_OUT+File.separator+"sum"+i+".bin -OP1 "+OPs[0]+" -OP2 "+OPs[1];
                //System.out.println(configs[i]);
            }
        
            runHilos r = new runHilos(configs, "java -jar "+this.JAR, CORES);
            r.ejecuta();  
    }
    
}
