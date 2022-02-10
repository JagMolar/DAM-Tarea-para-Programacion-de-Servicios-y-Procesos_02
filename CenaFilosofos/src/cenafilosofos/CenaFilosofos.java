/*
 * TAREA PSP02. EJERCICIO 2.
 * La cena de los filósofos.
 * 5 filósofos se sientan alrdedor de una mes para comer arroz y pensar.
 * Cada filósofo tiene un plato de arroz chino y un palillo a la izquierda de 
 * su plato. Cuando un filósofo quiere comer arroz, cogerá los dos palillos de 
 * cada lado del plato y comerá. El problema es el siguiente: establecer un ritual 
 * (algoritmo) que permita comer a los filósofos. El algoritmo debe satisfacer 
 * la exclusión mutua (dos filósofos no pueden emplear el mismo palillo a la 
 * vez), además de evitar el interbloqueo y la inanición. 
 * 
 */
package cenafilosofos;

import java.util.concurrent.Semaphore;

/**
 *
 * @author juang <juangmuelas@gmail.com>
 * @since 18/11/2020
 * @version 1
 */
public class CenaFilosofos {
    
    /**
     * Siguiendo la documentación facilitada en el recurso javadoc de la tarea,
     * Define los parámetros necesarios para construir hilos filósofos (ver el 
     * constructor de la clase Filosofo). Se crean los 5 filósofos, y los inicia. 
     * @param args the command line arguments
     */
      
    final static int numFilosofos = 5;
    
    final static int[][] palilloFilosofo = {
        {0, 4}, // palillos filosofo 0
        {1, 0}, // palillos filosofo 1
        {2, 1}, // palillos filosofo 2
        {3, 2}, // palillos filosofo 3
        {4, 3} // palillos filosofo 4
    };
    
    final static Semaphore[] semaforoPalillo = new Semaphore[numFilosofos];
        
    public static void main(String[] args) {
                
        /**
         * Control a través del semáforo de los indices de palillos para cada 
         * filósofo. El valor es 1 para dar un acceso por palillo
         */
        for(int i = 0; i < numFilosofos; i++){
        semaforoPalillo[i] = new Semaphore(1);
        } //Fin for
        
        //Creamos los filósofos y se inician.
        for(int miIndice=0; miIndice < numFilosofos; miIndice++){
            new Filosofo(miIndice, semaforoPalillo, palilloFilosofo).start();
        }
    } //Fin main   
}//Fin clase CenaFilosofos
