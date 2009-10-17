package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.media.opengl.*;

public class JOGL extends JFrame implements WindowListener {

    private TetrisGL canvas;

    public JOGL() {
        super("Tetris 3D");
        // utworzenie kontenera
        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        // dodanie panelu do okna
        c.add(makeRenderPanel(), BorderLayout.CENTER);

        // dodanie obs?ugi dla zda?e? okna
        addWindowListener(this);

        pack();
        setVisible(true);
    }

    public JPanel makeRenderPanel() {
        JPanel renderPane = new JPanel();	// utworzenie panleu
        renderPane.setLayout(new BorderLayout());
        renderPane.setOpaque(true);
        renderPane.setPreferredSize(new Dimension(800, 600));
        // utworzeni p?ótna
        canvas = makeCanvas();
        // dodanie p?utna do panelu (okna)
        renderPane.add(canvas, BorderLayout.CENTER);
        // p?ótno otrzymuje informacje o zdarzeniach (klawisze)
        canvas.setFocusable(true);
        canvas.requestFocus();
        // wykrywa rozmiary okna i kszta?tuje p?ótno wg tych rozmiarów
        renderPane.addComponentListener(new ComponentAdapter() {

            public void componentResized(ComponentEvent evt) {
                Dimension d = evt.getComponent().getSize();
                canvas.reshape(d.width, d.height);
            }
        });

        return renderPane;
    }

    public TetrisGL makeCanvas() {
        // pobieranie konfiguracji odpowiedniej dla p?ótna (canvas)
        GLCapabilities caps = new GLCapabilities();

        System.out.println(caps.toString());
        AWTGraphicsDevice dev = new AWTGraphicsDevice(null);
        // wybiera konfiguracje graficzn? odpowiedni? dla danego systemu, opart? n ainformacji z GLCapabilities
        AWTGraphicsConfiguration awtConfig = (AWTGraphicsConfiguration) GLDrawableFactory.getFactory().chooseGraphicsConfiguration(caps, null, dev);

        GraphicsConfiguration config = null;
        if (awtConfig != null) {
            config = awtConfig.getGraphicsConfiguration();
        }

        return new TetrisGL(caps);
    }

    public void windowActivated(WindowEvent e) {
        canvas.resumeGame();
    }

    public void windowDeactivated(WindowEvent e) {
        canvas.pauseGame();
    }

    public void windowDeiconified(WindowEvent e) {
        canvas.resumeGame();
    }

    public void windowIconified(WindowEvent e) {
        canvas.pauseGame();
    }

    public void windowClosing(WindowEvent e) {
        canvas.stopGame();
    }

    public void windowClosed(WindowEvent e) {
    }

    public void windowOpened(WindowEvent e) {
    }

    public static void main(String[] args) {
        new JOGL();
    }
}

