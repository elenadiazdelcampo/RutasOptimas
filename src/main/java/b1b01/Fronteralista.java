/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package b1b01;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author User
 */
//Ejemplo de frontera pero con listas
public class Fronteralista {
    
    List<NodoArbol> lista;

    public Fronteralista() {
        lista=new ArrayList<NodoArbol>();
    }
        
    public void CrearFrontera(){
        lista.clear();
    }
    public void Inserta (NodoArbol nodo){
        lista.add(nodo);
    }
    
    public NodoArbol Elimina() {
        return (NodoArbol) lista.remove(lista.size()-1);
    }
    
    public boolean EsVacia() {
        return lista.isEmpty();
    }
}

    

