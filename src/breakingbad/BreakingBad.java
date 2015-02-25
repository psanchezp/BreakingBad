/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breakingbad;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import javax.swing.JFrame;
import java.util.LinkedList;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Patricio Sanchez and David Benítez 
 */
public class BreakingBad extends JFrame implements Runnable, KeyListener {
    private boolean bVivo;
    private boolean bPausa;
    private Base basBrick;
    private Base basPelota;
    private Base basGameOver;
    private static final int iWidth = 800; //ancho de la pantalla
    private static final int iHeight = 500; //alto de la pantalla
    /* objetos para manejar el buffer del Applet y este no parpadee */
    private Image    imaImagenApplet;   // Imagen a proyectar en Applet	
    private Graphics graGraficaApplet;  // Objeto grafico de la Imagen
    private LinkedList <Base> llsBricks;   //Linkedlist de bricks
    private int iDireccion;
    
    public BreakingBad () {
        bVivo = true;
        bPausa = true;
        
        // defino la imagen del brick
	URL urlImagenBrick = this.getClass().getResource("bricks.gif");
        llsBricks = new LinkedList <Base> ();
        
        int iPosX;
        int iPosY = 25;   
        
        for (int iI = 0; iI < 5; iI++) {
            iPosX = 22;
            for(int iJ = 0; iJ < 15; iJ++) {
                basBrick = new Base(iPosX, iPosY, 49, 30,
                    Toolkit.getDefaultToolkit().getImage(urlImagenBrick));
                
                llsBricks.add(basBrick);
                
                iPosX += 50;
            }
            iPosY += 31;
        }
        
        // defino la imagen de la pelota
	URL urlImagenPelota = this.getClass().getResource("pill.gif");
        
        // se crea el objeto para la pelota
        iPosX = 200;
        iPosY = 200;       
	basPelota = new Base(iPosX, iPosY, 21, 22,
                Toolkit.getDefaultToolkit().getImage(urlImagenPelota));
        
        //Inicializando el keylistener
        addKeyListener(this);
        
        //Inicializacion del Hilo
        Thread th = new Thread (this);
        th.start();
    }
    
    /** 
     * run
     * 
     * Metodo sobrescrito de la clase <code>Thread</code>.<P>
     * En este metodo se ejecuta el hilo, que contendrá las instrucciones
     * de nuestro juego.
     * 
     */
    public void run () {
        /* mientras dure el juego, se actualizan posiciones de jugadores
           se checa si hubo colisiones para desaparecer jugadores o corregir
           movimientos y se vuelve a pintar todo
        */ 
        while (true) {
            actualiza();
            checaColision();
            repaint();
            try	{
                // El thread se duerme.
                Thread.sleep (20);
            }
            catch (InterruptedException iexError) {
                System.out.println("Hubo un error en el juego " + 
                        iexError.toString());
            }
	}
    }
    
    /** 
     * actualiza
     * 
     * Metodo que actualiza la posicion de los objetos 
     * 
     */
    public void actualiza (){

    }
    
    /**
     * checaColision
     * 
     * Metodo usado para checar la colision entre objetos
     * 
     */
    public void checaColision () {

    }
    
    /**
     * update
     * 
     * Metodo sobrescrito de la clase <code>Applet</code>,
     * heredado de la clase Container.<P>
     * En este metodo lo que hace es actualizar el contenedor y 
     * define cuando usar ahora el paint
     * 
     * @param graGrafico es el <code>objeto grafico</code> usado para dibujar.
     * 
     */
    public void paint (Graphics graGrafico){
        // Inicializan el DoubleBuffer
        if (imaImagenApplet == null){
                imaImagenApplet = createImage (this.getSize().width, 
                        this.getSize().height);
                graGraficaApplet = imaImagenApplet.getGraphics ();
        }

        // Actualiza la imagen de fondo.
        URL urlImagenFondo = this.getClass().getResource("Ciudad.png");
        Image imaImagenFondo = Toolkit.getDefaultToolkit().getImage(urlImagenFondo);
         graGraficaApplet.drawImage(imaImagenFondo, 0, 0, iWidth, iHeight, this);

        // Actualiza el Foreground.
        graGraficaApplet.setColor (getForeground());
        paint1(graGraficaApplet);

        // Dibuja la imagen actualizada
        graGrafico.drawImage (imaImagenApplet, 0, 0, this);
    }

    /**
     * paint1
     * 
     * Metodo sobrescrito de la clase <code>Applet</code>,
     * heredado de la clase Container.<P>
     * En este metodo se dibuja la imagen con la posicion actualizada,
     * ademas que cuando la imagen es cargada te despliega una advertencia.
     * 
     * @param graDibujo es el objeto de <code>Graphics</code> usado para dibujar.
     * 
     */
    public void paint1 (Graphics graDibujo) {
        // si la imagen ya se cargo
        if (llsBricks != null && basPelota != null) {
            if (bVivo){
                
                //Dibuja la imagen del brick en el Applet
                for(Base basBrick : llsBricks){
                        basBrick.paint(graDibujo, this);
                }
                
                //Dibuja la imagen de la pelota en el Applet
                basPelota.paint(graDibujo, this);

                //Puntos y vidas desplegados en la esquina superior izquierda
                //graDibujo.setColor(Color.red);
                //graDibujo.drawString("Puntos: " + iPuntos, 15, 45);
                //graDibujo.drawString ("Vidas: " + iVidas, 15, 60);
            }
            else{
                basGameOver.paint(graDibujo, this);
            }
        } // sino se ha cargado se dibuja un mensaje 
        else {
                //Da un mensaje mientras se carga el dibujo	
                graDibujo.drawString("No se cargo la imagen..", 20, 20);
        }
    }
    
    @Override
    public void keyTyped(KeyEvent ke) {
        
    }

    @Override
    public void keyPressed(KeyEvent ke) {

        if (ke.getKeyCode() == KeyEvent.VK_A) {    //Presiono flecha izquierda
            iDireccion = 1;
        } 
        else if (ke.getKeyCode() == KeyEvent.VK_D) {    //Presiono flecha derecha
            iDireccion = 2;
        }
        
        //Al presionar ESC se sale del juego
        if (ke.getKeyCode() == KeyEvent.VK_ESCAPE){
            bVivo = false;
        }
        
        //Al presionar P se pausa el juego
        if (ke.getKeyCode() == KeyEvent.VK_P){
            bPausa = !bPausa;
        }
        


    }

    @Override
    public void keyReleased(KeyEvent ke) {    
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        BreakingBad juego = new BreakingBad();
        juego.setSize(iWidth, iHeight);
        juego.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        juego.setVisible(true);
    }
    
}
