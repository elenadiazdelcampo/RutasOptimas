/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package b1b01;

/**
 *
 * @author User
 */
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.xml.sax.SAXException;

public class pruebaProblema {
    private EspacioDeEstados espaciodeestados;
    private Estado inicial;

    public pruebaProblema(String fileJSON) throws FileNotFoundException, IOException, ParseException, SAXException {
        
        Object obj = new JSONParser().parse(new FileReader(fileJSON)); 
          
        // typecasting obj to JSONObject 
        JSONObject jo = (JSONObject) obj; 
        String ruta = (String) jo.get("graphlmfile");
        
        espaciodeestados=new EspacioDeEstados(ruta+".xml");        
              
        Map address = ((Map)jo.get("IntSt")); 
        String nodo="";
        JSONArray  lista=null;
        
        // iterating address Map 
        Iterator<Map.Entry> itr1 = address.entrySet().iterator(); 
        while (itr1.hasNext()) { 
            Map.Entry pair = itr1.next(); 
            if (pair.getKey().equals("node"))                
                 nodo = (String) pair.getValue(); 
             if (pair.getKey().equals("listNodes")) 
                 lista=(JSONArray)pair.getValue();
        } 
        
        LectorXML lector=espaciodeestados.getLector();
        Nodo actual = lector.posicionNodo(nodo);
        List<Nodo> lNodos = new ArrayList<Nodo>();
        
        for (int i=0;i<lista.size();i++) {
            lNodos.add(lector.posicionNodo((lista.get(i)+"")));
        }
        
        inicial = new Estado(actual,lNodos);
    }
    
    //cuando el tamaño de la lista de nodos que me quedan por recorrer es 0, es objetivo
     public boolean Objetivo (Estado estado) {
         List<Nodo> lNodos= new ArrayList<Nodo>(estado.getListaNodos());
         return (lNodos.size()==0);
    }

    public EspacioDeEstados getEspaciodeestados() {
        return espaciodeestados;
    }

    public void setEspaciodeestados(EspacioDeEstados espaciodeestados) {
        this.espaciodeestados = espaciodeestados;
    }

    public Estado getInicial() {
        return inicial;
    }

    public void setInicial(Estado inicial) {
        this.inicial = inicial;
    }

    @Override
    public String toString() {
        return "Problema{" + "espaciodeestados=" + espaciodeestados + ", inicial=" + inicial + '}';
    }
}