/**
 * Base
 *
 * Modela la definición de todos los objetos de tipo
 * <code>Base</code>
 *
 * @author Patricio Sanchez and David Benitez
 * @version 1.0
 * @date 2/11/2015
 */

package breakingbad;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import javax.swing.ImageIcon;

public class Base {

    private int iX;     //posicion en x.       
    private int iY;     //posicion en y.
    private int iAncho; //ancho del objeto
    private int iAlto; //largo del objeto
    private Image imaImagen;	//imagen.
    private ImageIcon imiIcono; //Image icon del objeto
    private Animacion aniAnima; //Animacion del Objeto

    /**
     * Base
     * 
     * Metodo constructor usado para crear el objeto base
     * creando el icono a partir de una imagen
     * 
     * @param iX es la <code>posicion en x</code> del objeto.
     * @param iY es la <code>posicion en y</code> del objeto.
     * @param iAncho es el <code>ancho</code> del objeto.
     * @param iAlto es el <code>Largo</code> del objeto.
     * @param imaImagen es la <code>imagen</code> del objeto.
     * 
     */
    public Base(int iX, int iY , int iAncho, int iAlto,Image imaImagen) {
        this.iX = iX;
        this.iY = iY;
        this.iAncho = iAncho;
        this.iAlto = iAlto;
        this.imaImagen = imaImagen;
    }
    
    /**
     * Base
     * 
     * Metodo constructor usado para crear el objeto base
     * creando el icono a partir de una animacion
     * 
     * @param iX es la <code>posicion en x</code> del objeto.
     * @param iY es la <code>posicion en y</code> del objeto.
     * @param iAncho es el <code>ancho</code> del objeto.
     * @param iAlto es el <code>Largo</code> del objeto.
     * @param aniAnima es la <code>animacion</code> del objeto.
     * 
     */
    public Base(int iX, int iY , int iAncho, int iAlto,Animacion aniAnima) {
        this.iX = iX;
        this.iY = iY;
        this.iAncho = iAncho;
        this.iAlto = iAlto;
        this.aniAnima = aniAnima;
    }

    
    /**
     * setX
     * 
     * Metodo modificador usado para cambiar la posicion en x del objeto
     * 
     * @param iX es la <code>posicion en x</code> del objeto.
     * 
     */
    public void setX(int iX) {
        this.iX = iX;
    }

    /**
     * getX
     * 
     * Metodo de acceso que regresa la posicion en x del objeto 
     * 
     * @return iX es la <code>posicion en x</code> del objeto.
     * 
     */
    public int getX() {
            return iX;
    }

    /**
     * setY
     * 
     * Metodo modificador usado para cambiar la posicion en y del objeto 
     * 
     * @param iY es la <code>posicion en y</code> del objeto.
     * 
     */
    public void setY(int iY) {
            this.iY = iY;
    }

    /**
     * getY
     * 
     * Metodo de acceso que regresa la posicion en y del objeto 
     * 
     * @return posY es la <code>posicion en y</code> del objeto.
     * 
     */
    public int getY() {
        return iY;
    }

    /**
     * setImagen
     * 
     * Metodo modificador usado para cambiar el icono de imagen del objeto
     * tomandolo de un objeto imagen
     * 
     * @param imaImagen es la <code>imagen</code> del objeto.
     * 
     */
    public void setImagen(Image imaImagen) {
        this.imaImagen = imaImagen;
    }

    /**
     * getImagen
     * 
     * Metodo de acceso que regresa la imagen que representa el icono del objeto
     * 
     * @return la imagen a partide del <code>icono</code> del objeto.
     * 
     */
    public Image getImagen() {
        return imaImagen;
    }
    
        /**
     * getAnimacion
     * 
     * Metodo de acceso que regresa la animacion 
     * que representa el icono del objeto
     * 
     * @return la imagen a partir de del <code>icono</code> del objeto.
     * 
     */
    public Animacion getAnimacion() {
        return aniAnima;
    }

    /**
     * getAncho
     * 
     * Metodo de acceso que regresa el ancho del icono 
     * 
     * @return un <code>entero</code> que es el ancho de la imagen.
     * 
     */
    public int getAncho() {
        return iAncho;
    }

    /**
     * getAlto
     * 
     * Metodo que  da el alto del icono 
     * 
     * @return un <code>entero</code> que es el alto de la imagen.
     * 
     */
    public int getAlto() {
        return iAlto;
    }
    
    /**
     * paint
     * 
     * Metodo para pintar la base
     * 
     * @param graGrafico    objeto de la clase <code>Graphics</code> para graficar
     * @param imoObserver  objeto de la clase <code>ImageObserver</code> es el 
     *    Applet donde se pintara
     * 
     */
    public void paint(Graphics graGrafico, ImageObserver imoObserver) {
        graGrafico.drawImage(getImagen(), getX(), getY(), getAncho(), getAlto(), imoObserver);
    }
    
        /**
     * paint 2
     * 
     * Metodo para pintar la base con animacion
     * 
     * @param graGrafico    objeto de la clase <code>Graphics</code> para graficar
     * @param imoObserver  objeto de la clase <code>ImageObserver</code> es el 
     *    Applet donde se pintara
     * 
     */
    public void paint2(Graphics graGrafico, ImageObserver imoObserver) {
        graGrafico.drawImage(aniAnima.getImagen(), getX(), getY(), 
                getAncho(), getAlto(), imoObserver);
    }

    /**
     * equals
     * 
     * Metodo para checar igualdad con otro objeto
     * 
     * @param objObjeto    objeto de la clase <code>Object</code> para comparar
     * @return un valor <code>boleano</code> que sera verdadero si el objeto
     *   que invoca es igual al objeto recibido como parámetro
     * 
     */
    public boolean equals(Object objObjeto) {
        // si el objeto parametro es una instancia de la clase Base
        if (objObjeto instanceof Base) {
            // se regresa la comparación entre este objeto que invoca y el
            // objeto recibido como parametro
            Base basParam = (Base) objObjeto;
            return this.getX() ==  basParam.getX() && 
                    this.getY() == basParam.getY() &&
                    this.getAncho() == basParam.getAncho() &&
                    this.getAlto() == basParam.getAlto() &&
                    this.getImagen() == basParam.getImagen();
        }
        else {
            // se regresa un falso porque el objeto recibido no es tipo Base
            return false;
        }
    }

    /**
     * toString
     * 
     * Metodo para obtener la interfaz del objeto
     * 
      * @return un valor <code>String</code> que representa al objeto
     * 
     */
    public String toString() {
        return " x: " + this.getX() + " y: "+ this.getY() +
                " ancho: " + this.getAncho() + " alto: " + this.getAlto();
    }
    
    /* actualiza
    *   
    *   Metodo que actualiza la imagen del objeto
    *   @param lTiempo es una long con el tiempo transcurrido
    *   @param bMover es un booleano que determina si se esta moviendo
    */
    public void actualiza (long lTiempo, boolean bMover){
        if (bMover){
            aniAnima.actualiza(lTiempo);
        }
        else{
            aniAnima.iniciar();
        }
    }
    
    /*
     * intersecta
     *
     * Metodo que checa si un objeto intersecta a otro
     *
     * @param objObjeto es un objeto de la clase <code>Object</code>
     * @return un boleano para saber si intersecta o no
     */
    public boolean intersecta(Object objObjeto) {
        if (objObjeto instanceof Base) {
            Rectangle rctEsteObjeto = new Rectangle(this.getX(), this.getY(),
                    this.getAncho(), this.getAlto());
            Base aniObjeto = (Base) objObjeto;
            Rectangle rctObjetoParam = new Rectangle(aniObjeto.getX(),
                    aniObjeto.getY(), aniObjeto.getAncho(), aniObjeto.getAlto());
            return rctEsteObjeto.intersects(rctObjetoParam);
        } 
        else {
            return false;
        }
    }
    
    /*
     * intersecta 2
     *
     * Metodo que checa si un objeto intersecta con otro del mismo tipo, y regresa
     * si fue vertical u horizontal
     *
     * @param objObjeto es un objeto de la clase <code>Object</code>
     * @return un int para saber si intersecta o no, y por donde fue
     */
    public int intersectaLado (Object objObjeto) {
        if (objObjeto instanceof Base) {
            Rectangle rctBrick = new Rectangle(this.getX(), this.getY(),
                    this.getAncho(), this.getAlto());

            
            Base basPelota = (Base) objObjeto;
            Rectangle rctPelota = new Rectangle(basPelota.getX(),
                    basPelota.getY(), basPelota.getAncho(), basPelota.getAlto());
            
            //Si hay interseccion, checar por que lado
            if(rctBrick.intersects(rctPelota)){
                Rectangle intersection = rctBrick.getBounds().intersection(rctPelota.getBounds());
                
                if (intersection.width >= intersection.height){ //interseccion vertical
                    return 1;
                }

                else { //interseccion horizontal
                    return 2;
                }
            }
            else {
                return 0;
            }
        } 
        else {
            return 0;
        }
    }
}