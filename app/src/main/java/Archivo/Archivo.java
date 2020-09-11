package Archivo;

import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Archivo {
    //añadir más estudiantes al archivo
    public void agregarArchivo(double soporte, double tiempo, double memoria, String archivo) {
        String fileName = archivo + ".txt";
        File root = new File(Environment.getExternalStorageDirectory()+ File.separator+"Music_Folder", "Report Files");
        //File root = new File(Environment.getExternalStorageDirectory(), "Notes");
        if (!root.exists())
        {
            root.mkdirs();
        }
        File gpxfile = new File(root, fileName);
        FileWriter flwriter=null;
        try {
            //además de la ruta del archivo recibe un parámetro de tipo boolean, que le indican que se va añadir más registros
            flwriter = new FileWriter(gpxfile,true);
            BufferedWriter bfwriter = new BufferedWriter(flwriter);
            bfwriter.write("\r\n" + soporte + "," + tiempo + "," + memoria);
            bfwriter.close();
            //Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
            //Toast.makeText(this, "Data has been written to Report File", Toast.LENGTH_SHORT).show();
            //System.out.println("Archivo modificado satisfactoriamente..");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (flwriter != null) {
                try {
                    flwriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
