/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package b1b01;

import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

import java.util.*;
import java.io.*;

public class LectorXML extends DefaultHandler {

    private final XMLReader xr;
    private List<String> lista = new ArrayList<String>();
    private List<String> lista2 = new ArrayList<String>();
    private List<Nodo> lNodo = new ArrayList<Nodo>();
    private List<Arista> lArista = new ArrayList<Arista>();
    //variables de prueba 

    boolean latitud;
    boolean longitud;
    boolean idnodo;
    boolean calle;
    boolean distancia;
    String slatitud;
    String slongitud;
    String sidnodo;
    String scalle;
    String sdistancia;

    //fin
    public LectorXML() throws SAXException {
        xr = XMLReaderFactory.createXMLReader();
        xr.setContentHandler(this);
        xr.setErrorHandler(this);
    }

    public void leer(final String archivoXML)
            throws FileNotFoundException, IOException,
            SAXException {
        FileReader fr = new FileReader(archivoXML);
        xr.parse(new InputSource(fr));
    }

    @Override
    public void startElement(String uri, String name,
            String qName, Attributes atts) {
        if("key".equals(name)){            
                switch(atts.getValue("attr.name")){
                    case "name":
                        if (atts.getValue("for").equals("edge"))
                            scalle = atts.getValue("id");                        
                        break;
                    case "length":
                        sdistancia = atts.getValue("id");                     
                        break;
                    case "x":
                        slatitud = atts.getValue("id");                        
                        break;
                    case "y":
                        slongitud = atts.getValue("id");                        
                        break;
                    case "osmid":
                        if (atts.getValue("for").equals("node"))
                            sidnodo=atts.getValue("id");  
                        break;
                }                 
        }
        
        if (qName.equals("data")) {
            latitud = (atts.getValue("key").equals(slatitud));
            longitud = (atts.getValue("key").equals(slongitud));
            idnodo = (atts.getValue("key").equals(sidnodo));
            calle = (atts.getValue("key").equals(scalle));
            distancia = (atts.getValue("key").equals(sdistancia));
        }

        if (qName.equals("edge")) {
            lista2.add(atts.getValue("source"));
            lista2.add(atts.getValue("target"));
        }
    }

    public void characters(char ch[], int start, int length) throws SAXException {

        if (latitud) {
            lista.add(new String(ch, start, length).trim().replaceAll("\n", ""));
            latitud = false;
        }
        if (longitud) {
            lista.add(new String(ch, start, length).trim().replaceAll("\n", ""));
            longitud = false;
        }
        if (idnodo) {
            lista.add(new String(ch, start, length).trim().replaceAll("\n", ""));
            idnodo = false;
            Double dlatitud = Double.parseDouble(lista.get(0).trim() + "0");
            Double dlongitud = Double.parseDouble(lista.get(1).trim() + "0");
            long iinodo = Long.parseLong(lista.get(2).trim());
            lNodo.add(new Nodo(iinodo, dlatitud, dlongitud));
            lista.clear();
        }
        
        if (calle) {
            lista2.add(new String(ch, start, length).trim().replaceAll("\n", ""));
            calle = false;
        }       
        if (distancia) {
            lista2.add(new String(ch, start, length).trim().replaceAll("\n", ""));
            distancia = false;       
        }
    }

    public void endElement(String uri, String name, String qName) {
            if (qName.equals("edge")) {
               
        
            long lsource = Long.parseLong(lista2.get(0).trim());
            long ltarget = Long.parseLong(lista2.get(1).trim());
            String scalle="";
            Double ddistancia=0.0; 
            if (lista2.size()==4){
                try {
                    scalle= lista2.get(2).trim();
                    ddistancia = Double.parseDouble(lista2.get(3).trim() + "0");  
                }
                catch(Exception e) {
                    scalle= lista2.get(3).trim();
                    ddistancia = Double.parseDouble(lista2.get(2).trim() + "0"); 
                }                    
            }
            else if (lista2.size()==3){
                try {                    
                    ddistancia = Double.parseDouble(lista2.get(2).trim() + "0");  
                }
                catch(Exception e) {
                    scalle= lista2.get(2).trim();                    
                }              
            }
            lArista.add(new Arista(lsource, ltarget, scalle, ddistancia));    
            lista2.clear();
            }
    }
    
    public void Imprimir () {
        for (int i =0;i<lNodo.size();i++)
            System.out.println(lNodo.get(i));
        
        System.out.println();
        
        for (int i =0;i<lArista.size();i++)
            System.out.println(lArista.get(i));
    }
    
    public boolean perteneceNodo (String id) {
        return lNodo.contains(new Nodo(Long.parseLong(id)));
    }
    
    public Nodo posicionNodo (String id) {
        Nodo aux=null;
        for(Nodo nodo: lNodo) {
            if (nodo.getId()==Long.parseLong(id))
                aux=nodo;
        }
        return aux;
    }
    public List<Arista> adyacentesNodo (String id) {
        List<Arista> aux = new ArrayList<Arista>();
        for(Arista arista: lArista) {
            if (arista.getNinicial()==Long.parseLong(id))
                aux.add(arista);
        }
        return aux;
    }    
}