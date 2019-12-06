/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package b1b01;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import org.xml.sax.SAXException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author User
 */
public class tarea3 {

  static final int INFINITO=5000;
    
    public static void main(String[] args) throws SAXException, IOException, org.json.simple.parser.ParseException {
       String opcion;
        boolean valido,conPoda,solucion=false;        
        int Prof_Max=20, Inc_Prof=1;
        Scanner sc=new Scanner(System.in);
        pruebaProblema prob= new pruebaProblema("problema.json");        
        
        do {
            System.out.print("1. Con Poda  2. Sin Poda\n Elige una opcion: ");
            opcion=sc.next();
            valido=opcion.equals("1") || opcion.equals("2");
            if (!valido)
                System.out.println("Opcion no válida. Vuelva a Intentarlo.\n");
        } while (!valido);    
        
        conPoda=opcion.equals("1") ? true:false;        
        
        // pedimos al usuario la estrategia de búsqueda
        do {
            System.out.print("\n1. Anchura\n2. Profundidad simple\n3. Profundidad Acotada\n4. Profundidad Iterativa"
                    + "\n5. Coste Uniforme\n6. Voraz\n7. A Asterisco\n Elige una opcion: ");
            opcion=sc.next();
            valido=opcion.equals("1") || opcion.equals("2") || opcion.equals("3") || opcion.equals("4") || opcion.equals("5")
                    || opcion.equals("6") || opcion.equals("7");
            if (!valido)
                System.out.println("Opcion no válida. Vuelva a Intentarlo.\n");
        } while (!valido);        
        
        // Para cualquier opcion distinta de la profundidad simple elegimos la profundidad máxima acotada
        if (!opcion.equals("2")) {
            do {
                valido=true;
                System.out.print ("Introduce profundida máxima: ");
                try {
                    Prof_Max=Integer.parseInt(sc.next());
                } catch (Exception e) {
                    System.out.println("Valor no númerico");
                    valido=false;
                }
            } while(!valido);
        }
        
        // Si la opcion es la profuncidad iterativa pedimos al usuario el incremento por iteración
        if (opcion.equals("4")) {
            do {
                valido=true;
                System.out.print ("Introduce Incremento Profundidad Iterativa: ");
                try {
                    Inc_Prof=Integer.parseInt(sc.next());
                } catch (Exception e) {
                    System.out.println("Valor no númerico");
                    valido=false;
                }
            } while(!valido);
        }
        
        switch (opcion) {
            case "1":
                solucion=Busqueda_Acotada (prob, "Anchura", Prof_Max, conPoda); //anchura
                break;
            case "2":
                solucion=Busqueda_Acotada (prob, "ProfundidadSimple", INFINITO, conPoda); //Profundidad simple, utilizada prof infinita
                break;
            case "3":
                solucion=Busqueda_Acotada (prob, "ProfundidadAcotada", Prof_Max, conPoda); // Profundidad acotada
                break;
            case "4":
                solucion=Busqueda (prob, "ProfundidadIterativa", Prof_Max,Inc_Prof, conPoda); // Profundidad iterativa
                break;
            case "5":
                solucion=Busqueda_Acotada (prob, "CosteUniforme", Prof_Max, conPoda); // Coste uniforme
                break;
            case "6":
                solucion=Busqueda_Acotada (prob, "Voraz", Prof_Max, conPoda); // Voraz
                break;
            case "7":
                solucion=Busqueda_Acotada (prob, "A", Prof_Max, conPoda); // A asterisco
                break;
           
        }
        
        if (!solucion)
            System.out.println("No ha sido posible encontrar solución en la profundidad dada");   
    }
       
     
    public static boolean Busqueda_Acotada (pruebaProblema prob, String estrategia, int Prof_Max, boolean conPoda) throws IOException { 
        int total=0;
        Frontera frontera = new Frontera();
        frontera.CrearFrontera();
        NodoArbol n_inicial= new NodoArbol(null,prob.getInicial(),0,"",0,0);
        frontera.Inserta(n_inicial);
        boolean solucion=false;
        NodoArbol n_actual=null;
        Map<String,Double> nodosVisitados=new HashMap<String,Double>(); // HashMap para la poda de estados repetidos
        Deque<NodoArbol> nodosSolucion = new LinkedList<NodoArbol>(); // cola doble para almacenar la solución generada
        
        while (!solucion && !frontera.EsVacia()) {            
            n_actual=frontera.Elimina();            
            if (prob.Objetivo(n_actual.getEstado()))
                solucion=true;
            else {
                List<Sucesor> LS = prob.getEspaciodeestados().sucesor(n_actual.getEstado());               
                List<NodoArbol> LN= CreaListaNodosArbol(LS,n_actual,Prof_Max,estrategia); 
                
                for (NodoArbol nodo:LN) {    
                    if (conPoda) { // si se ha elegido poda no se insertan en la frontera los estados repetidos                   
                        String nodoString=nodo.getEstado().getIdentificador();
                        if (nodosVisitados.containsKey(nodoString)) {                             
                            if ((nodo.getF()<nodosVisitados.get(nodoString).intValue() && !estrategia.contains("Profundidad"))
                                || (nodo.getF()>nodosVisitados.get(nodoString).intValue() && estrategia.contains("Profundidad"))){                           
                                frontera.Inserta(nodo);     
                                total++;
                                nodosVisitados.replace(nodoString, nodo.getF());//                                                          
                            }                            
                        }
                        else {                       
                            nodosVisitados.put(nodoString, nodo.getF());
                            frontera.Inserta(nodo);      
                            total++;
                        }                
                    }
                    else frontera.Inserta(nodo);
                }
            }            
        }
        if (solucion) {
            // si encontramos solucion la introducimos en la cola doble nodosSolucion            
            while (n_actual.getPadre()!=null) {                
                nodosSolucion.addFirst(n_actual);
                n_actual=n_actual.getPadre();               
            }
            // se inserta el nodo inicial y se genera el fichero en la carpeta de la solución
            nodosSolucion.addFirst(n_inicial);

           solucionenConsola (nodosSolucion,estrategia,total);
           escribirSolucion(nodosSolucion, estrategia,total);            
            System.out.println("\nFICHERO GENERADO");

            
        }        
        return solucion;
    }
    
    public static List<NodoArbol> CreaListaNodosArbol(List<Sucesor> LS, NodoArbol n_actual, int Prof_Max, String estrategia) {        
        List<NodoArbol> LN = new ArrayList<NodoArbol>();
        if (n_actual.getP()<Prof_Max) { // si aún podemos seguir iterando por no alcanzar la profundidad máxima
            NodoArbol aux=null;            
            for (Sucesor sucesor:LS) {
               //dependiendo de la estrategia generamos los nodos
               switch (estrategia) {
                   case "Anchura":
                        aux= new NodoArbol(n_actual, sucesor.getEstado(),n_actual.getCoste()+sucesor.getCoste(),sucesor.getAccion(),
                          n_actual.getP()+1, n_actual.getP()+1); 
                        break;
                   case "ProfundidadSimple":
                   case "ProfundidadAcotada":
                   case "ProfundidadIterativa":
                        aux= new NodoArbol(n_actual, sucesor.getEstado(), n_actual.getCoste()+sucesor.getCoste(),sucesor.getAccion(),
                        n_actual.getP()+1, Prof_Max-(n_actual.getP()+1));                        
                        break;                
                   case "CosteUniforme":
                        aux= new NodoArbol(n_actual, sucesor.getEstado(), n_actual.getCoste()+sucesor.getCoste(),sucesor.getAccion(),
                        n_actual.getP()+1, n_actual.getCoste()+sucesor.getCoste());                        
                        break;
                   case "Voraz":
                        aux= new NodoArbol(n_actual, sucesor.getEstado(), n_actual.getCoste()+sucesor.getCoste(),sucesor.getAccion(),
                        n_actual.getP()+1, n_actual.getCoste()+sucesor.getEstado().getHeuristica());                         
                        break;
                   case "A":
                        aux= new NodoArbol(n_actual, sucesor.getEstado(), n_actual.getCoste()+sucesor.getCoste(),sucesor.getAccion(),
                        n_actual.getP()+1, n_actual.getCoste()+sucesor.getCoste()+sucesor.getEstado().getHeuristica());                         
                        break;
               }
               LN.add(aux);
               
            }
        }
        return LN;
    }
    
    // metodo utilizado para la busqueda de profundidad iterativa, que recibe ademas el incremento de profundidad 
    public static boolean Busqueda (pruebaProblema prob, String estrategia, int Prof_Max, int Inc_Prof, boolean conPoda) throws IOException {        
        int Prof_Actual=Inc_Prof;
        boolean solucion = false;
        while (!solucion && Prof_Actual<=Prof_Max) {
            solucion=Busqueda_Acotada(prob,estrategia,Prof_Actual,conPoda);
            Prof_Actual+=Inc_Prof;
        }
        return solucion;        
    }  
    
    
     // método para mostrar la solución por consola
   public static void solucionenConsola (Deque<NodoArbol> nodosSolucion, String estrategia, int total) {        
        System.out.println("\nLa solución es: ");
        System.out.println("Estrategia: "+estrategia.toUpperCase());
        System.out.println("Total Nodos Generados: "+total);
        System.out.println("Profundidad: " + (nodosSolucion.getLast().getP()+1));
        System.out.println("Costo: " + format(nodosSolucion.getLast().getCoste()));
        System.out.println("");  
        int i=0;
        for (NodoArbol nodoarbol: nodosSolucion) {            
            if (i==0) {
                if (estrategia.equals("Voraz") || estrategia.equals("A")) 
                     System.out.println("None " + format(nodoarbol.getEstado().getHeuristica()) + " " + nodoarbol.getP() + " " + format(nodoarbol.getCoste()));
                else
                    System.out.println("None " + format(nodoarbol.getF()) + " " + nodoarbol.getP() + " " + format(nodoarbol.getCoste()));
                    System.out.println(nodoarbol.getEstado());
            }
            else {
                System.out.println(nodoarbol.getAccion() + " " + format(nodoarbol.getF()) + " " + nodoarbol.getP() + " " + format(nodoarbol.getCoste()));
                System.out.println(nodoarbol.getEstado());
            }    
            
            System.out.println("");  
            i++;
        }        
    }
    
  public static void escribirSolucion(Deque<NodoArbol> nodosSolucion, String estrategia, int total) throws IOException{
       
       Writer out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("fichero_solucion.txt"), "UTF-8"));
			
           // Escribimos linea a linea en el fichero
           out.write("La solucion es:\n");
           out.write("Estrategia: "+estrategia+"\n");
           out.write("Total nodos generados:"+total+"\n");
           out.write("Profundidad:"+(nodosSolucion.getLast().getP()+1)+"\n");
           out.write("Costo:"+nodosSolucion.getLast().getCoste()+"\n\n");
           
           int i=0;
           for (NodoArbol nodoarbol: nodosSolucion) {            
            if (i==0) {
                if (estrategia.equals("Voraz") || estrategia.equals("A")) 
                     out.write("None " + format(nodoarbol.getEstado().getHeuristica()) + " " + nodoarbol.getP() + " " + format(nodoarbol.getCoste()));
                else
                    out.write("None " + format(nodoarbol.getF()) + " " + nodoarbol.getP() + " " + format(nodoarbol.getCoste()));
                    out.write(nodoarbol.getEstado()+"\n\n");
            }
            else {
                out.write(nodoarbol.getAccion() + " " + format(nodoarbol.getF()) + " " + nodoarbol.getP() + " " + format(nodoarbol.getCoste()));
                out.write(nodoarbol.getEstado()+"\n\n");
            }  
             out.write("");  
            i++;
           }
		} catch (UnsupportedEncodingException | FileNotFoundException ex2) {
			System.out.println("Mensaje error 2: " + ex2.getMessage());
		} finally {
			try {
				out.close();
			} catch (IOException ex3) {
				System.out.println("Mensaje error cierre fichero: " + ex3.getMessage());
			}
		}
  }



    // metodo para comprobar si una cadena es numérica
    public static boolean IsNumericInteger (String valor) {
        try {
            int numero=Integer.parseInt(valor);
            return true;
        }
        catch (NumberFormatException nfe){
            return false;
        }
    }
    
    public static String format(double numero) {
        String aux;
        int numeroint = (int)numero; // lo convertimos a entero
        if (numeroint==numero) // vemos si coincide con el double para quitar los decimales con 0 a la derecha
            aux=""+numeroint; // en caso afirmativo devolvemos el numero entero
        else
            aux=String.format("%.2f",numero); // sino lo devolvemos con dos decimales
        return aux;
    }  
    
    
}

 
