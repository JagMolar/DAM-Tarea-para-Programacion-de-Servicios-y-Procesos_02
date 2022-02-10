/*
 * TAREA PSP02. EJERCICIO 1.
 * Un hilo productor debe almacenar 15 caracteres en un búfer compartido.
 * Un hilo consumidor, los recoge Pueden depositarse hasta 6 caracteres, de manera
 * que puedan ccnsumirse siempre que el bufer no esté vacío. 
 * El hilo productor solo puede añadir si el bufer está vacío o hay espacio.
 */
package tareaproductorconsumidor;

/**
 *
 * @author juang <juangmuelas@gmail.com>
 * @since 17/11/2020
 */
public class Hilo_Consumidor  extends Thread {
    /**
     * @param prod_con objeto compartido
     */
    private ProductorConsumidor prod_con;
 
    /**
     * Constructor que recoge un parámetro para uso local
     * @param pc copia del objeto
     */
    public Hilo_Consumidor(ProductorConsumidor pc){
        prod_con = pc;
    }
    
    /**
     * método run
     */
    @Override
    public void run(){
        /**
         * @param letra recoge caracteres dede el bucle
         * @throws InterruptedException
         */
        char letra;
        
        //Podemos añadir 15 caracteres
         for( int i = 0; i < 15; i++){
             letra =prod_con.recoger();
             //Muestra el caracter recogido
             System.out.println("Recogido el caracter " + letra + " en el buffer.");
             
             //Debe comprobar si puede añadir otra
             try{
                 sleep(100);
             }catch(InterruptedException e){}
         }//Fin del for que controla la ejecución       
    } //Fin run()
}//Fin clase Hilo_Consumidor
