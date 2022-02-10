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
public class ProductorConsumidor {

    /**
     * @param args the command line arguments
     */
    
    // pide reducir la entrada a 6, generamos el buffer
    private char buffer[] = new char[6];
    
    // Controles para el estado del buffer
    /**
     * @param nuevodato integer de llenado
     * @param buffervacio booleano que controla estado buffer. Si es true permite
     * añadir nuevos caractees al buffer.
     * @param bufferlleno booleano que controla estado buffer
     */
    private int nuevodato = 0;
    private boolean bufferVacio = true;
    private boolean bufferlleno = false;
       
    /**
     * Método depositar que añade caracteres en el buffer
     * Mientras esté lleno, este espera con wait().
     * Si puede, añade un caracter.
     * Luego comprueba si está lleno (máximo 6 caracteres), para ver si puede
     * continuar.
     * @param c 
     */
    public synchronized void depositar( char c ) {
        // Espera hasta que haya sitio para otra letra
        while( bufferlleno == true )
            {
            try {
                wait(); 
            } catch( InterruptedException e ) {}
            }//Fin while
        buffer[nuevodato] = c;
        nuevodato++;
        if( nuevodato == 6 )
            bufferlleno = true;
            bufferVacio = false;
        notify();
        } //Fin método depositar

    /**
     * Método recoger para retirar letras del buffer.
     * Mientras esté vacío, este espera con wait().
     * Si hay alguno, decrementa en uno al consumirlo.
     * Luego comprueba si es la última, de ser así cambiaría a vacío y pueden
     * entrar nuevos valores.
     * @return nuevodato al thread del consumidor
     */
    public synchronized char recoger() {
        while( bufferVacio == true ){
            try {
                wait();
            } catch( InterruptedException e ) {}
            }//Fin del while
        nuevodato--;
        // Comprueba si se retiró la última letra
        if( nuevodato == 0 )
            bufferVacio = true;
        bufferlleno = false;
        notify();

        return( buffer[nuevodato] );
        }//Fin método recoger
   
    public static void main(String[] args) {
        /**
         * Creamos un nuevo objeto de nuestra clase principal
         */
        ProductorConsumidor prod_con = new ProductorConsumidor();
        
        //Creamos los hilos
        Hilo_Productor hp = new Hilo_Productor(prod_con);
        Hilo_Consumidor hc = new Hilo_Consumidor(prod_con);
        
        //Iniciamos ejecución de los hilos
        hp.start();
        hc.start();
    }//Fin main   
}//Fin clase ProductorConsumidor
