package Agenetico;


import android.os.Build;
import android.widget.EditText;

import androidx.annotation.RequiresApi;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import Modelo.Dataset;

import static Agenetico.Poblacion.binarioADecimal;
import static java.util.Map.Entry.comparingByValue;

public class Algoritmo {
    //public static Map<String, Integer> Categorias;
    private static ArrayList _Variables;
    public static Runtime Itrruntime;
    private static int datazise = 1024 * 1024; //Para sacar la conversion en megabyte
    public static ArrayList _Memorias;
    public static Map<ArrayList<String>, Double> _Solucion = new HashMap<ArrayList<String>, Double>();;

    private static double memoriaTotal = 0.0;
    private static double tiempoEjecucionT = 0.0;
    private static double tiempoEjecucion;
    private static long start_tiempoEjecucion;
    private static long end_tiempoEjecucion;

    /* GA parameters */
    private static final double uniformRate = 0.5;
    private static final double mutationRate = 0.015;
    private static final int tournamentSize = 5;
    private static final boolean elitism = true;

    public Algoritmo(){

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void Iniciar(int Iteracion, int Size_Poblacion, ArrayList variable, float soporte, boolean nosolucion, EditText editText){
        _Variables = variable;
        _Memorias = new ArrayList<Double>();
        //Poblacion Inicial
        Itrruntime = Runtime.getRuntime();
        start_tiempoEjecucion = System.currentTimeMillis();
        Poblacion poblacionIn = new Poblacion(Size_Poblacion, variable);
        SoporteCal.setSoporte(poblacionIn, Size_Poblacion, variable);
        double memoria_use = (double)(Itrruntime.totalMemory() - Itrruntime.freeMemory()) / datazise ;
        end_tiempoEjecucion = System.currentTimeMillis();
        tiempoEjecucion = end_tiempoEjecucion - start_tiempoEjecucion;
        Calcular_Solucion(poblacionIn);
        int generationCount = 1;
        //Activity_Salida activity_salida = new Activity_Salida();
        //EditText txtView = (EditText) ((activity_salida)).findViewById(R.id.Atx_Campo);
        while(generationCount < Iteracion){
            editText.append("Generation: " + generationCount + " Soporte: "+poblacionIn.getFittest().getSoporte()+"\n");
            //Formulario.frame.Atx_Campo.append("Generation: " + generationCount + " Soporte: "+poblacionIn.getFittest().getSoporte()+"\n");
            memoriaTotal += memoria_use;
            tiempoEjecucionT += tiempoEjecucion;
            editText.append("Memoria Usada: " + memoria_use+ " Mb"+"\n" );
            editText.append("Tiempo Ejecución: " + tiempoEjecucion+ " ms"+"\n");
            _Memorias.add(memoria_use);
            start_tiempoEjecucion = System.currentTimeMillis();
            poblacionIn = Evaluar_Poblacion(poblacionIn);
            SoporteCal.setSoporte(poblacionIn, Size_Poblacion, variable);
            end_tiempoEjecucion = System.currentTimeMillis();
            memoria_use = (double)(Itrruntime.totalMemory() - Itrruntime.freeMemory()) / datazise;
            tiempoEjecucion = end_tiempoEjecucion -  start_tiempoEjecucion;
            Calcular_Solucion(poblacionIn);
            generationCount++;
        }
        boolean solution = Solucion(soporte);
        editText.append("Generation: " + generationCount + " Soporte: "+poblacionIn.getFittest().getSoporte()+"\n");
        memoriaTotal += memoria_use;
        tiempoEjecucionT += tiempoEjecucion;
        editText.append("Memoria Usada : " + memoria_use+ " Mb"+"\n");
        editText.append("Tiempo Ejecución: " + tiempoEjecucion+ " ms"+"\n");
        _Memorias.add(memoria_use);
        if (solution) {
            editText.append("\n Solución: \n");
            _Solucion.forEach((k, v) -> {
                if (v >= Double.valueOf(soporte)) {
                    editText.append("Items:"+k+" => Soporte: "+v+"\n");
                }
            });
            ArrayList<ArrayList<String>> keys = new ArrayList<ArrayList<String>>(_Solucion.keySet());
            for (int i = _Solucion.size() - 1; i >= 0; i--) {
                editText.append("Items:" + keys.get(i) + " => Soporte: " + _Solucion.get(keys.get(i)) + "\n");
            }
        }
        else{
            if (nosolucion) {
                editText.append("\n Solución: \n");
                ArrayList<ArrayList<String>> keys = new ArrayList<ArrayList<String>>(_Solucion.keySet());
                for (int i = _Solucion.size()-1; i >= _Solucion.size()-3; i--) {
                    editText.append("Items:"+keys.get(i)+" => Soporte: "+_Solucion.get(keys.get(i))+"\n");
                }
            }else{
                editText.append("\n Sin Solución \n");
            }
        }
        //_Solucion.forEach((k,v) -> Formulario.frame.Atx_Campo.append("Items:"+k+" => Soporte: "+v+"\n"));
        /*Individuo IndM = poblacionIn.getFittest();
        Formulario.frame.Atx_Campo.append("\n Solución: \n");
        for (int i = 0; i < _Variables.size(); i++) {
            Formulario.frame.Atx_Campo.append("Item "+(i+1)+": "+ IndM.getItemS(i)+"\n");
        }
        Formulario.frame.Atx_Campo.append("\nGen:"+IndM.toString()+" - Soporte:"+IndM.getSoporte()+"\n"); */
        editText.append("Memoria Usada Total: " +memoriaTotal  + " Mb \n");
        editText.append("Tiempo Ejecución: " + tiempoEjecucionT+ " ms"+"\n");
        //Archivo archivo = new Archivo();
        //archivo.agregarArchivo(poblacionIn.getFittest().getSoporte(), tiempoEjecucionT, memoriaTotal, "Genetico.txt");
        memoriaTotal = 0;
        tiempoEjecucionT = 0;
        _Solucion.clear();
    }

    public static Poblacion Evaluar_Poblacion(Poblacion pop){
        Poblacion newPopulation = new Poblacion(pop.size_population);
        // Keep our best individual
        if (elitism) {
            newPopulation.saveIndivido(0, pop.getFittest());
        }

        // Crossover population
        int elitismOffset;
        if (elitism) {
            elitismOffset = 1;
        } else {
            elitismOffset = 0;
        }

        // Loop over the population size and create new individuals with
        // crossover
        for (int i = elitismOffset; i < pop.size_population; i++) {
            Individuo indiv1 = tournamentSelection(pop);
            Individuo indiv2 = tournamentSelection(pop);
            Individuo newIndiv = crossover(indiv1, indiv2);
            newPopulation.saveIndivido(i, newIndiv);
        }

        // Mutate population
        for (int i = elitismOffset; i < pop.size_population; i++) {
            mutate(newPopulation.getIndividuos(i));
            int numero = binarioADecimal(newPopulation.getIndividuos(i).toString());
            if((numero < Dataset.getSize())&&( numero >=0)){
                newPopulation.getIndividuos(i).setIndex(numero);
                newPopulation.getIndividuos(i).Inicializar_ItemV(_Variables.size());
                for (int j = 0; j < _Variables.size(); j++) {
                    int indx = Integer.valueOf(String.valueOf(_Variables.get(j)));
                    ArrayList src = (ArrayList) Dataset.dataset.get(indx);
                    newPopulation.getIndividuos(i).setSingleItemS(j, String.valueOf(src.get(numero)));
                }
            }
            else{
                i--;
            }
        }
        return newPopulation;
    }

    // Crossover individuals
    private static Individuo crossover(Individuo indiv1, Individuo indiv2) {
        Individuo newSol = new Individuo();
        for (int i = 0; i < newSol.getGenes().length; i++) {
            if (Math.random() <= uniformRate) {
                newSol.setSingleGene(i, indiv1.getGenes(i));
            } else {
                newSol.setSingleGene(i, indiv2.getGenes(i));
            }
        }
        return newSol;
    }

    // Mutate an individual
    private static void mutate(Individuo indiv) {
        for (int i = 0; i < indiv.getLargo(); i++) {
            if (Math.random() <= mutationRate) {
                byte gene = (byte) Math.round(Math.random());
                indiv.setSingleGene(i, gene);
            }
        }
    }

    private static Individuo tournamentSelection(Poblacion pop) {
        // Create a tournament population
        Poblacion tournament = new Poblacion(tournamentSize);
        // For each place in the tournament get a random individual
        for (int i = 0; i < tournamentSize; i++) {
            int randomId = (int) (Math.random() * pop.size_population);
            tournament.saveIndivido(i, pop.getIndividuos(randomId));
        }
        // Get the fittest
        Individuo fittest = tournament.getFittest();
        return fittest;
    }

    public static void Calcular_Solucion(Poblacion poup){
        try{
            for (int i = 0; i < poup.size_population; i++) {
                if (_Solucion.isEmpty()) {
                    ArrayList list = new ArrayList<>(Arrays.asList(poup._Individuos[i].getItemS()));
                    _Solucion.put(list, poup._Individuos[i].getSoporte());
                }else{
                    ArrayList list = new ArrayList<>(Arrays.asList(poup._Individuos[i].getItemS()));
                    _Solucion.put(list, poup._Individuos[i].getSoporte());
                }
            }
        }catch(Exception e){
            String s = e.getMessage();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public static boolean Solucion(float soporte){
        int c = 0;
       Map<ArrayList<String>, Double> sortedMap = _Solucion.entrySet().stream().sorted(comparingByValue()).collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        //_Solucion = sortedMap;

        for (Map.Entry<ArrayList<String>, Double> entry : _Solucion.entrySet()) {
            if (entry.getValue() >= Double.valueOf(soporte)) {
                c++;
            }
        }
        return (c != 0);
    }
}
