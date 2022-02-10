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

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 *
 * @author juang <juangmuelas@gmail.com>
 * @since 18/11/2020
 * @version 1
 */
public class Filosofo extends Thread {
    
    /**
     * Siguiendo la documentación facilitada en el recurso javadoc de la tarea,
     * Se crea el hilo Filósofo, con métodos pensar y comer, que se invocan en un
     * bucle infinito en el método run()
     */
    
    /**
     * Declaramos las variables siguiendo las indicaciones del javadoc de la tarea.
     * @param miIndice índice que identifica al filósofo (un entero del 0 al 4)
     * @param semaforoPalillo vector de semáforos (uno para cada palillo).
     * @param palilloFilosofo matriz que para cada valor de su primer índice, 
     * la fila, almacena los palillos que necesita el filósofo de ese índice 
     * para comer. Por ejemplo: el filósofo de índice 0 necesita los palillos de 
     * índices {0,4}, el de índice 1 los de índices {1,0}, etc... Puedes 
     * prescindir de este vector si se te ocurre como calcular en tiempo real 
     * los índices de los palillos que necesita cada filósofo para comer
     * @param palilloDerecho integer extraido del array comentado antes.
     * @param palilloIzquierdo integer extraido del array comentado antes.
     */
    private int miIndice;
    private final Semaphore[] semaforoPalillo;
    private final int[][] palilloFilosofo;
    private int palilloDerecho;
    private int palilloIzquierdo;
    
    /**
     * Constructor de tres parámetros, cada uno de los cuales se guardará en una 
     * variable local para usarla cuando sea neceario
     * @param miIndice
     * @param semaforoPalillo
     * @param palilloFilosofo 
     */
    public Filosofo(int miIndice,Semaphore[] semaforoPalillo,int[][] palilloFilosofo){
        this.miIndice = miIndice;
        this.semaforoPalillo = semaforoPalillo;
        this.palilloFilosofo = palilloFilosofo;
        this.palilloDerecho = palilloFilosofo[miIndice][1];
        this.palilloIzquierdo = palilloFilosofo[miIndice][0];
    }//Fin constructor
    
    /**
     * @param tiempoAleatorio objeto de la clase Random para marcar los tiempos 
     * en cada proceso
     */
    private final Random tiempoAleatorio = new Random();
    
    
    /**
     * método pensar(): mostrará un mensaje en la Salida de que el 'Filósofo 
     * ' N ' está pensado'.
     * @return miIndice indica el filósofo que está pensando
     * @throws InterruptedException si no captura ningún valor
     */
    protected void pensar() {
        System.out.println("Filósofo " + miIndice + " está pensando.");
        try {
            // El tiempo que tarda el filósofo en pensar, entre 100 y 1000 milisegundos:
            Filosofo.sleep(tiempoAleatorio.nextInt(3000) + 100);
        } catch (InterruptedException ex) {
            System.out.println("Error en el método pensar(): " + ex.toString());
        }
    }//Fin método pensar
    
    protected void comer(){
        /**
         * método comer(): mostrará un mensaje en la Salida de que el 'Filósofo 
         * ' N ' está hambriento', mientras trata de conseguir los dos palillos 
         * que necesita para comer.
         * 
         * El filósofo debe intentar coger el palillo de su izquierda. Si lo
         * tiene, intenta coger el de la derecha para comer.
         * Para ello, usamos el boolean tryAquire, que intenta adquirir un 
         * permiso en caso de estar disponible, devolviendo en ese supuesto un true.
         * 
         * Asimismo, facilitamos la lectura en consola mediante el uso de color
         * en cada tipo de salida.
         * 
         * @throws InterruptedException si falla el booleano o el tiempo.
         */

        if (semaforoPalillo[palilloIzquierdo].tryAcquire()) {
            if (semaforoPalillo[palilloDerecho].tryAcquire()) {
                System.out.println("\033[36mFILÓSOFO " + miIndice + " ESTÁ COMIENDO.");
                try {
                    // Simular el tiempo que tarda el filósofo en comer,
                    // entre 0.5 y 1 segundos:
                    sleep(tiempoAleatorio.nextInt(3000) + 500);
                } catch (InterruptedException ex) {
                    System.out.println("Error : " + ex.toString());
                }
                System.out.println("\033[33mFilósofo " + miIndice + " termina de comer.Libres palillos " + palilloIzquierdo + " , " + palilloDerecho);
                /**
                 * Tras usar Acquire para el permiso, se libera con release
                 */
                semaforoPalillo[palilloDerecho].release();
            }
            /**
             * Tras usar Acquire para el permiso, se libera con release
             */
            semaforoPalillo[palilloIzquierdo].release();
        } else { 
            System.out.println("\033[31mFilósofo " + miIndice + " hambriento.");
            // si no consigue comer.
        }
    }//Fin método comer   
    
    /**
     * bucle infinito: llamada al método pensar(), llamada al método comer()
     */
    @Override
    public void run(){
        while (true){
            pensar();
            comer();
        }//fin bucle while
    } //fin método run 
}//Fin clase Filosofo
