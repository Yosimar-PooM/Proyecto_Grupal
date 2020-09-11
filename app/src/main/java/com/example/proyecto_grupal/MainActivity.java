package com.example.proyecto_grupal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TableLayout;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import Agenetico.Tabla;
import Modelo.Dataset;

public class MainActivity extends AppCompatActivity {

    private String archivo = "miarchivo";
    private String carpeta = "/archivos/";
    private Button btndatos, ejecutar;
    private CheckBox Chck_Solucion;
    private EditText txtfile,txtpoblacion,txtiteracion,txtsoporte;
    private TableLayout tabla;
    private String path;
    public Dataset data;
    public static ArrayList<Integer> variables;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtfile = (EditText) findViewById(R.id.txt_File);
        txtpoblacion = (EditText) findViewById(R.id.txt_poblacion);
        txtiteracion = (EditText) findViewById(R.id.txt_iteracion);
        txtsoporte = (EditText) findViewById(R.id.txt_Soporte);
        ejecutar = (Button) findViewById(R.id.Btn_Ejecutar);
        btndatos = (Button) findViewById(R.id.Btnabrir);
        Chck_Solucion = (CheckBox) findViewById(R.id.Chck_Solucion);
        tabla = (TableLayout)findViewById(R.id.Jtable_Attr);
    }


    public void abrir_activity() {
        // Comprobamos que el EditText contenga algo escrito, de no tener nada
        // escrito no hará nada cuando se pulse el botón.
        if (txtfile.getText().length() != 0) {

            // Utilizamos un objeto de la clase Bundle para incluir un par
            // "Clave/Valor", este objeto tendrá como clave "datos", y su valor
            // será el texto que se introduzca en el EditText.
            Bundle b = new Bundle();
            b.putString("txtfile", txtfile.getText().toString());
            b.putString("txtpoblacion", txtpoblacion.getText().toString());
            b.putString("txtiteracion", txtiteracion.getText().toString());
            b.putString("txtsoporte", txtsoporte.getText().toString());
            boolean nosolucion = Chck_Solucion.isChecked();
            b.putBoolean("Chck_Solucion", nosolucion);

            // La clase Intent establece un link entre esta Activity y la nueva
            // que queremos lanzar, para ello al instanciar el Intent
            // introducimos como parámetros esta propia Activity, y la clase que
            // representa a la nueva Activity.
            Intent i = new Intent(MainActivity.this, Activity_Salida.class);
            //Intent nuevo = new Intent(MainActivity.this, ActivityTabla.class);
            //En el Intent añadimos el Bundle, para que lleve la información a
            // la siguiente Activity.
            i.putExtras(b);

            // Lanzamos la siguiente Activity.
            startActivity(i);
            //startActivity(nuevo);
        }
    }

    public void  Devolver_Archivo() throws IOException {
        //File ff = new File(Environment.getExternalStorageDirectory()+File.separator+"Music_Folder", "Report Files");
        //File f =null;
        //this.path=ff.getAbsolutePath();
        InputStream fraw= getResources().openRawResource(R.raw.pr_datos_pepsi);
        //this.path =(Environment.getExternalStorageDirectory()+ this.carpeta);
        txtfile.setText(fraw.toString());
        //txtfile.setText(this.path.toString());
        //f = new File(this.path);
        BufferedReader br = new BufferedReader(new InputStreamReader(fraw));
        String line = br.readLine();
        String[] datos = line.split(",");
        data = new Dataset(datos.length);
        data.Rotulo_Data(datos);
        line = br.readLine();
        while (line != null) {
            datos = line.split(",");
            data.Llenar_Data(datos);
            line = br.readLine();
        }
        /* FileReader fr = new FileReader(f);
         BufferedReader  br = new BufferedReader(fr);
         String line = br.readLine();
         String[] datos = line.split(",");
         data = new Dataset(datos.length);
         data.Rotulo_Data(datos);
         line = br.readLine();
         while (line != null) {
             datos = line.split(",");
             data.Llenar_Data(datos);
             line = br.readLine();
         }*/
        //Cargar_Conjunto();
    }
    public void onClick(View view) throws IOException {
        switch (view.getId()){
            case R.id.Btnabrir:
                Devolver_Archivo();
                break;
            case R.id.Btn_Ejecutar:
                abrir_activity();
                break;

        }
    }

    public void Cargar_Conjunto(){

        try{
            Tabla tabla = new Tabla(this, (TableLayout)findViewById(R.id.Jtable_Attr));

            tabla.agregarCabecera(data);
            ArrayList<String> elementos = null;
            try{
                // DefaultTableModel modelo = (DefaultTableModel)Jtable_Attr.getModel();
                //Limpiar Data
                // modelo.setRowCount(0);
                ArrayList rt = data.getRotulo();
                for (int i = 0; i < rt.size(); i++) {
                    //tabla.add(new Object[] {(i+1), false, rt.get(i)});

                }
            }catch(Exception e){ System.out.print(e.getMessage());}
            /*for(int i = 0; i < 15; i++)
            {
                ArrayList<String> elementos = new ArrayList<String>();
                elementos.add(Integer.toString(i));
                elementos.add("Casilla [" + i + ", 0]");
                elementos.add("Casilla [" + i + ", 1]");
                tabla.agregarFilaTabla(elementos);
            }*/
            //tabla.agregarFilaTabla(elementos,data);
        }catch(Exception e){
            System.out.print(e.getMessage());
        }
    }
    private void openFile(File url) {


        //Uri uri = null;
        /*try {
        uri = Uri.fromFile(f);
       /* Intent intent = new Intent(Intent.ACTION_VIEW);
        if (f.toString().contains(".txt")) {
            intent.setDataAndType(uri, "text/plain");
        }
        else {
            intent.setDataAndType(uri, "*///*");
        //}
        //  intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //  startActivity(intent);
        //}
        // catch (ActivityNotFoundException e)
        // {
        //  Toast.makeText(getApplicationContext(), "No application found which can open the file", Toast.LENGTH_SHORT).show();
        // }
        //this.path = uri.toString();*/
        /*try {
            Uri uri = Uri.fromFile(url);
            Intent intent = new Intent(Intent.ACTION_VIEW);

            if (url.toString().contains(".doc") || url.toString().contains(".docx"))
            {  Word document intent.setDataAndType(uri, "application/msword"); }
            else if (url.toString().contains(".pdf")) {
                PDF file intent.setDataAndType(uri, "application/pdf"); }
            else if (url.toString().contains(".ppt") || url.toString().contains(".pptx"))
                { Powerpoint file intent.setDataAndType(uri, "application/vnd.ms-powerpoint"); }
            else if (url.toString().contains(".xls") || url.toString().contains(".xlsx"))
                {  Excel file intent.setDataAndType(uri, "application/vnd.ms-excel"); }
            else if (url.toString().contains(".zip") || url.toString().contains(".rar"))
                {  WAV audio file intent.setDataAndType(uri, "application/x-wav"); }
            else if (url.toString().contains(".rtf"))
                { RTF file intent.setDataAndType(uri, "application/rtf"); }
            else if (url.toString().contains(".wav") || url.toString().contains(".mp3"))
                { WAV audio file intent.setDataAndType(uri, "audio/x-wav"); }
            else if (url.toString().contains(".gif"))
                {  GIF file intent.setDataAndType(uri, "image/gif"); }
            else if (url.toString().contains(".jpg")|| url.toString().contains(".jpeg") || url.toString().contains(".png"))
                { JPG file intent.setDataAndType(uri, "image/jpeg"); }
            else if (url.toString().contains(".txt"))
                {  Text file intent.setDataAndType(uri, "text/plain"); }
            else if (url.toString().contains(".3gp") || url.toString().contains(".mpg")|| url.toString().contains(".mpeg") || url.toString().contains(".mpe")
                || url.toString().contains(".mp4") || url.toString().contains(".avi"))
                {  Video files intent.setDataAndType(uri, "video/*"); }
                else { //intent.setDataAndType(uri, //"*///*//"); }
               /* intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Activity context;
            context.startActivity(intent); }
                catch (ActivityNotFoundException e)
                { Toast.makeText(context, "No application found which can open the file", Toast.LENGTH_SHORT).show(); }*/
    }
}