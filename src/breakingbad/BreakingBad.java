/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breakingbad;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import javax.swing.JFrame;
import java.util.LinkedList;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

/**
 *
 * @author Patricio Sanchez and David Benítez 
 */
public class BreakingBad extends JFrame implements Runnable, KeyListener {
    private boolean bVivo; //el jugador tiene vidas
    private boolean bPausa; //el juego esta en pausa
    private boolean bInicio; //el juego no ha empezado
    private boolean bFinal; //ganaste el juego
    private boolean bLose; //perdiste
    private Base basBrick; //objeto para los bricks
    private Base basPelota; //objeto para la pelota
    private Base basGameOver; //
    private Base basVidas; //
    private Base basBarra; //
    private static final int iWidth = 450; //ancho de la pantalla
    private static final int iHeight = 600; //alto de la pantalla
    /* objetos para manejar el buffer del Applet y este no parpadee */
    private Image    imaImagenApplet;   // Imagen a proyectar en Applet	
    private Graphics graGraficaApplet;  // Objeto grafico de la Imagen
    private SoundClip sndSonidoBrick;   // Objeto sonido del brick
    private LinkedList <Base> llsBricks;   //Linkedlist de bricks
    private LinkedList <Base> llsVidas;   //Linkedlist de vidas
    private int iDireccion; //direccion de la barra
    private int iVidas;  //vidas del jugador
    private int iDireccionYpelota; //Direccion en Y de la pelota
    private int iDireccionXpelota; //Direccion en X de la pelota
    private int iScore; //Score del juego
    private int iVelPelota; //Velocidad de la pelota
    private int iBricks; //cantidad de bricks en el juego
    Animacion animBarra; 
    long lTiempo;
    private boolean bMover; //Determinar si esta en movimiento
    
    public BreakingBad () {
        bVivo = false; //El juego todavia no comienza
        bPausa = false; //No está en pausa
        bInicio = true; //El juego esta en la pantalla de inicio
        bFinal = false; //No se ha ganado el juego
        bLose = false;
        
        iVidas = 3;
        
        iScore = 0;
        iVelPelota = 1;
        iBricks = 0;
        
        //Se define sonido del brick
        sndSonidoBrick = new SoundClip("dinero.wav");
        
        // defino la imagen del brick
	URL urlImagenBrick = this.getClass().getResource("bricks.gif");
        llsBricks = new LinkedList <Base> ();
        
        int iPosX;
        int iPosY = 65;   
        
        //Se crean los bricks en forma de matriz
        for (int iI = 0; iI < 5; iI++) {
            iPosX = 24;
            for(int iJ = 0; iJ < 8; iJ++) {
                basBrick = new Base(iPosX, iPosY, 49, 30,
                    Toolkit.getDefaultToolkit().getImage(urlImagenBrick));
                
                llsBricks.add(basBrick);
                
                iBricks++;
                
                iPosX += 50;
            }
            iPosY += 31;
        }
        
        iDireccionYpelota = 3; //Empieza hacia abajo
        iDireccionXpelota = (int) (Math.random() * 2) + 1; //Izq o derecha
        
        // defino la imagen de la pelota
	URL urlImagenPelota = this.getClass().getResource("pill.gif");
        
        // se crea el objeto para la pelota
        iPosX = 200;
        iPosY = 250;       
	basPelota = new Base(iPosX, iPosY, 21, 22,
                Toolkit.getDefaultToolkit().getImage(urlImagenPelota));
        
        iPosX = 200;
        iPosY = 530;
        
        
        //Se crean vidas
        llsVidas = new LinkedList <Base> ();
        iPosX = 10;
        URL urlImagenVidas = this.getClass().getResource("heisenberg.gif");
        for(int i = 0; i < iVidas; i++) {
            basVidas = new Base(iPosX, 27, 30, 30,
            Toolkit.getDefaultToolkit().getImage(urlImagenVidas));
            
            llsVidas.add(basVidas);
            iPosX += 32;
        }

        //Creando game over
        URL urlImagenGameOver = this.getClass().getResource("game-over.gif");
        basGameOver = new Base (200, 200, 450/2, 600/2, 
            Toolkit.getDefaultToolkit().getImage(urlImagenGameOver));

        //Inicializando Animacion
        animBarra = new Animacion();
        Image imaBarra1 = Toolkit.getDefaultToolkit().getImage(
                        this.getClass().getResource("barra1.gif"));
        Image imaBarra2 = Toolkit.getDefaultToolkit().getImage(
                        this.getClass().getResource("barra2.gif"));
        Image imaBarra3 = Toolkit.getDefaultToolkit().getImage(
                        this.getClass().getResource("barra3.gif"));
        
        animBarra.sumaCuadro(imaBarra1, 5000);
        animBarra.sumaCuadro(imaBarra2, 5000);
        animBarra.sumaCuadro(imaBarra1, 5000);
        animBarra.sumaCuadro(imaBarra3, 5000);

        
        //Inicializando la Barra
        basBarra = new Base(iPosX, iPosY, 90, 55, animBarra);

        //Inicializando el booleano de movimiento
        bMover = false;
        
        //Inicializando el keylistener
        addKeyListener(this);
        
        //Inicializacion del Hilo
        Thread th = new Thread (this);
        th.start();
    }
    
    /*
    *   Metodo Restart
    *   Reinicia todos los valores del juego
    */
    public void restart(){
        if (bFinal || bLose){
            bVivo = true; //El juego todavia no comienza
            bPausa = false; //No está en pausa
            bFinal = false; //No se ha ganado el juego
            bLose = false;

            iVidas = 3;
            iScore = 0;
            iVelPelota = 1;
            iBricks = 0;
            
            // defino la imagen del brick
            URL urlImagenBrick = this.getClass().getResource("bricks.gif");
            llsBricks = new LinkedList <Base> ();

            int iPosX;
            int iPosY = 65;   

            //Se crean los bricks en forma de matriz
            for (int iI = 0; iI < 5; iI++) {
                iPosX = 24;
                for(int iJ = 0; iJ < 8; iJ++) {
                    basBrick = new Base(iPosX, iPosY, 49, 30,
                        Toolkit.getDefaultToolkit().getImage(urlImagenBrick));

                    llsBricks.add(basBrick);

                    iBricks++;

                    iPosX += 50;
                }
                iPosY += 31;
            }

            iDireccionYpelota = 3; //Empieza hacia abajo
            iDireccionXpelota = (int) (Math.random() * 2) + 1; //Izq o derecha

            // defino la imagen de la pelota
            URL urlImagenPelota = this.getClass().getResource("pill.gif");

            // se crea el objeto para la pelota
            iPosX = 200;
            iPosY = 250;       
            basPelota = new Base(iPosX, iPosY, 21, 22,
                    Toolkit.getDefaultToolkit().getImage(urlImagenPelota));

            iPosX = 200;
            iPosY = 530;


            //Se crean vidas
            llsVidas = new LinkedList <Base> ();
            iPosX = 10;
            URL urlImagenVidas = this.getClass().getResource("heisenberg.gif");
            for(int i = 0; i < iVidas; i++) {
                basVidas = new Base(iPosX, 27, 30, 30,
                Toolkit.getDefaultToolkit().getImage(urlImagenVidas));

                llsVidas.add(basVidas);
                iPosX += 32;
            }

            //Inicializando el booleano de movimiento
            bMover = false;

        }
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
        lTiempo = System.currentTimeMillis();
        while (true) {
            if (!bPausa && bVivo) {
                actualiza();
                checaColision();
            }
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
    public void actualiza () {
        if(iDireccion == 1) {
            basBarra.setX(basBarra.getX() - 2);
        }
        else if(iDireccion == 2) {
            basBarra.setX(basBarra.getX() + 2);
        }

        long lTiempo2 = System.currentTimeMillis() - lTiempo;
        lTiempo = lTiempo + lTiempo2;
        basBarra.actualiza(lTiempo, bMover);

        if(iDireccionXpelota == 1) { //Izquierda
           basPelota.setX(basPelota.getX() - (1 + iVelPelota)); 
        }
        if(iDireccionXpelota == 2) { //Derecha
            basPelota.setX(basPelota.getX() + (1 + iVelPelota));
        }
        if(iDireccionYpelota == 3) { //Abajo
           basPelota.setY(basPelota.getY() + (1 + iVelPelota)); 
        }
        if(iDireccionYpelota == 4) { //Arriba
            basPelota.setY(basPelota.getY() - (1 + iVelPelota));
        }

    }
    
    /**
     * checaColision
     * 
     * Metodo usado para checar la colision entre objetos
     * 
     */
    public void checaColision () {
        //Colisiones de la pelota con la pared
        if(basPelota.getX() <= 0) { //Si choca con la izq
            iDireccionXpelota = 2;
        }
        if(basPelota.getX() + basPelota.getAncho() >= iWidth) { //Si choca derecha
            iDireccionXpelota = 1;
        }
        if(basPelota.getY() <= 30) { //Si choca arriba
            iDireccionYpelota = 3;
        }
        if(basPelota.getY() > iHeight) { //Si choca abajo
            if(iVidas == 1) {
                iVidas --;
                bVivo = !bVivo;
                bLose = true;
            }
            else {
                iVidas --;
                llsVidas.removeLast();
            }
            basPelota.setX(200);
            basPelota.setY(250);
            iDireccionYpelota = 3; //Empieza hacia abajo
            iDireccionXpelota = (int) (Math.random() * 2) + 1; //Izq o derecha          
        }
        
        //Colision de la barra con la pared
        if(basBarra.getX() <= 0) { //Choca a la izq
            basBarra.setX(0);
        }
        if(basBarra.getX() + basBarra.getAncho() >= iWidth) { //Choca a la der
            basBarra.setX(iWidth-basBarra.getAncho());
        }
        
        //Colision pelota con la barra
        if(basPelota.intersecta(basBarra)) {
            if(iDireccionXpelota == 1) {
                iDireccionXpelota = 2;
            }
            else if(iDireccionXpelota == 2) {
                iDireccionXpelota = 1;
            }
            
            iDireccionYpelota = 4;
        }
        
        //Colision pelota con brick
        for(Base basBrick : llsBricks){
            if(basBrick.intersectaLado(basPelota) == 1) {
                if(iDireccionYpelota == 3) {
                    iDireccionYpelota = 4;
                    iScore++;
                    sndSonidoBrick.play();
                    llsBricks.remove(basBrick);
                    iBricks--;
                    break; //NECESARIO para evitar un exception
                }
                else if(iDireccionYpelota == 4) {
                    iDireccionYpelota = 3;
                    iScore++;
                    sndSonidoBrick.play();
                    llsBricks.remove(basBrick);
                    iBricks--;
                    break; //NECESARIO para evitar un exception
                }
            }
            else if(basBrick.intersectaLado(basPelota) == 2) {
                if(iDireccionXpelota == 1) {
                    iDireccionXpelota = 2;
                    iScore++;
                    sndSonidoBrick.play();
                    llsBricks.remove(basBrick);
                    iBricks--;
                    break; //NECESARIO para evitar un exception
                }
                else if(iDireccionXpelota == 2) {
                    iDireccionXpelota = 1;
                    iScore++;
                    sndSonidoBrick.play();
                    llsBricks.remove(basBrick);
                    iBricks--;
                    break; //NECESARIO para evitar un exception
                }
            }
        }
        
        //se aumenta velocidad de la pelota dependiendo de los bricks
        if(iBricks > 19 && iBricks < 31) {
            iVelPelota = 2;
        }
        if(iBricks > 9 && iBricks < 21) {
            iVelPelota = 3;
        }
        if(iBricks < 10) {
            iVelPelota = 4;
        }
        
        //Si los ladrillos estan en 0 se acaba
        if (iBricks == 0){
            bFinal = true;
            bVivo = false;
        }

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
        URL urlImagenFondo;
        urlImagenFondo = this.getClass().getResource("title.gif");
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
        if (llsBricks != null && basPelota != null && basBarra != null 
                && llsVidas != null) {
            if (bVivo){
                graDibujo.drawImage(Toolkit.getDefaultToolkit().getImage(
                        this.getClass().getResource("background2.gif")),0,0,this);
                
                //Dibuja la imagen del brick en el Applet
                for(Base basBrick : llsBricks){
                        basBrick.paint(graDibujo, this);
                }
                
                //Dibuja la imagen de la pelota en el Applet
                basPelota.paint(graDibujo, this);
                
                //Dibuja la barra
                basBarra.paint2(graDibujo, this);
                
                //Despliega vidas
                for(Base basVidas : llsVidas){
                    basVidas.paint(graDibujo, this);
                }

                //Puntos desplegados en la esquina superior izquierda
                graDibujo.setFont(new Font("Arial", Font.PLAIN, 17));
                graDibujo.setColor(Color.red);
                graDibujo.drawString("Puntos: " + iScore, 340, 45);
            }
            else if (bFinal){
                graDibujo.drawImage(Toolkit.getDefaultToolkit().getImage(
                        this.getClass().getResource("win.gif")),0,0,this);
            }
            else if (bLose) {
                graDibujo.drawImage(Toolkit.getDefaultToolkit().getImage(
                        this.getClass().getResource("gameover.gif")),0,0,this);
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

        if (ke.getKeyCode() == KeyEvent.VK_LEFT) {    //Presiono flecha izquierda
            iDireccion = 1;
            bMover = true;
        } 
        else if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {    //Presiono flecha derecha
            iDireccion = 2;
            bMover = true;
        }
        
        //Al presionar ESC se sale del juego
        if (ke.getKeyCode() == KeyEvent.VK_ESCAPE){
            bVivo = false;
            bFinal = false;
            bLose = true;
        }
        
        //Al presionar P se pausa el juego
        if (ke.getKeyCode() == KeyEvent.VK_P){
            if (!bInicio){
              bPausa = !bPausa;  
            }
        }
        
        if (ke.getKeyCode() == KeyEvent.VK_R){
            restart ();
        }
        
        if (ke.getKeyCode() == KeyEvent.VK_S && !bFinal){
            bInicio = false;
            bVivo = true;
        }
        
    }

    @Override
    public void keyReleased(KeyEvent ke) {
       if (ke.getKeyCode() == KeyEvent.VK_LEFT) { //Suelta flecha izquierda
            iDireccion = 0;
            bMover = false;
        } 
        else if (ke.getKeyCode() == KeyEvent.VK_RIGHT) { //Suelta flecha derecha
            iDireccion = 0;
            bMover = false;
        }
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
