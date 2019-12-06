/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package b1b01;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.SAXException;

/**
 *
 * @author IsabelDM
 * Función sucesor: con el grafo que hemos leído
 * Constructor: nombre del fichero donde va a estar el graphml
 * Devuelve: lista con todos los sucesores a los que puedo ir con una acción concreta 
 * ¿Está el nodo en mi grafo?
 */
public class EspacioDeEstados {
   
       private LectorXML lector;

//Tiene como entrada el fichero con extension graphml
    public EspacioDeEstados(String ficheroXML) throws SAXException, IOException {
        lector=new LectorXML();
        lector.leer(ficheroXML);
    }
   
    public List<Sucesor> sucesor (Estado estado) {  
        String id=String.valueOf(estado.getActual().getId());
        List<Sucesor> lSucesores = new ArrayList<Sucesor>();
        List<Arista> lAristas = new ArrayList<Arista>(lector.adyacentesNodo(id));
        List<Nodo> lNodes = new ArrayList<Nodo>(estado.getListaNodos()); //lista de nodos que me quedan por recorrer
        Nodo aux;
        Estado siguiente;
        String accion; //string de la estrutura ID origen-ID destino
        double distancia; //costo de la accion
        for (Arista arista: lAristas) {
            aux = lector.posicionNodo(String.valueOf(arista.getNfinal()));
            if (lNodes.remove(aux))                
                siguiente=new Estado(aux,lNodes);
            else
                siguiente=new Estado(aux,estado.getListaNodos());
            if (arista.getNcalle().equals("")) //si el nombre de la calle de esa arista no tiene nombre
                accion= id + " -> " + aux.getId() + " (Noname)"; //decirle que esa calle no tiene nombre, ID-origen, ID-siguiente
            else
                accion= id + " -> " + aux.getId() + " (" + arista.getNcalle()+ ")"; //si tiene nombre mostrarlo
            
            distancia=arista.getLongitud(); //longitud de la arista desde el ID origen al ID destino
            lSucesores.add(new Sucesor(accion,siguiente,distancia));
            /*En la lista de sucesores añadimos el nuevo sucesor compuesto por 
            la accion, el nuevo estado, y la distancia*/
        } 
        return lSucesores;        
    }
    
//devuelve si, si el estado pertenece al espacio de estados y no en caso contrario
   public boolean esta (Estado estado) {
        boolean correcto=lector.perteneceNodo(String.valueOf(estado.getActual().getId()));
        if (correcto) {
            List<Nodo> lNodos= new ArrayList<Nodo>(estado.getListaNodos());
            for (int i =0;i<lNodos.size() && correcto;i++) {
                correcto=lector.perteneceNodo(String.valueOf(lNodos.get(i).getId()));
            }
        }
        return correcto;
    }


    public LectorXML getLector() {
        return lector;
    }    
    
  }
