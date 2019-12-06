package b1b01;

/**

 * Idea: se puede hacer con una cola con prioridad y le vamos a pasar objetos 
 * Métodos:
 *  -CrearFrontera
 *  -Insertar(nodoARbol)
 *  -Elimina()
 *  -EsVacia() -> aquí es cuando llegaremos a nuestra función objetivo si nos da true
 */
import java.util.PriorityQueue;

public class Frontera {
    PriorityQueue<NodoArbol> colaNodosArbol;
//creamos una cola con prioridad, para que se guarden en en orden creciente
//segun el valor de la f
    public Frontera() {
        colaNodosArbol=new PriorityQueue<NodoArbol>();
    }
     //creamos la frontera vacia   
    public void CrearFrontera(){
        colaNodosArbol.clear();
    }
    //Insertamos un nodo nuevo a la frontera
    public void Inserta (NodoArbol nodo){
        colaNodosArbol.add(nodo);
    }
    
    //Devolvemos el primer nodo de la frontera y lo eliminamos
    public NodoArbol Elimina() {
        return (NodoArbol) colaNodosArbol.poll();
    }
    
    //comprobamos si la frontera esta vacia o no
    public boolean EsVacia() {
        return colaNodosArbol.isEmpty();
    }
}

