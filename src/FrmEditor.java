import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class FrmEditor extends JFrame {
    private Dibujo dibujo;
    private Point puntoInicial;
    private boolean modoSeleccion = false;
    private Trazo trazoSeleccionado = null;

    private JButton btnEliminar, btnGuardar, btnCargar, btnSeleccionar;
    private JComboBox<String> comboTipos;

    public FrmEditor() {
        dibujo = new Dibujo();
        setTitle("Editor de Dibujos Vectoriales");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelBotones = new JPanel();
        String[] tipos = {"Linea", "Cuadrado", "Circulo", "Triangulo"};
        comboTipos = new JComboBox<>(tipos);

        btnEliminar = new JButton("Eliminar Trazo");
        btnGuardar = new JButton("Guardar");
        btnCargar = new JButton("Cargar");
        btnSeleccionar = new JButton("Seleccionar");

        panelBotones.add(comboTipos);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCargar);
        panelBotones.add(btnSeleccionar);
        add(panelBotones, BorderLayout.SOUTH);

        DibujoPanel panelDibujo = new DibujoPanel();
        add(panelDibujo, BorderLayout.CENTER);

        btnEliminar.addActionListener(e -> btnEliminarClick(e));
        btnSeleccionar.addActionListener(e -> modoSeleccion = !modoSeleccion);

        btnGuardar.addActionListener(e -> btnGuardarClick(e));
        btnCargar.addActionListener(e -> {
            btnCargarClick(e);
            panelDibujo.repaint();
        });
    }

    private void btnGuardarClick(ActionEvent evt) {
        String nombreArchivo = Archivo.elegirArchivo();
        String[] lineas = dibujo.aArchivo();
        if (Archivo.guardarArchivo(nombreArchivo, lineas)) {
            JOptionPane.showMessageDialog(this, "Dibujo guardado correctamente");
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar el dibujo");
        }
    }

    private void btnEliminarClick(ActionEvent evt) {
        if (trazoSeleccionado != null) {
            dibujo.eliminar(trazoSeleccionado.getX1(), trazoSeleccionado.getY1());
            trazoSeleccionado = null; 
            repaint();
        } else {
            dibujo.eliminarUltimoTrazo();
            repaint();
        }
    }



    private void btnCargarClick(ActionEvent evt) {
        String nombreArchivo = Archivo.elegirArchivo();
        dibujo.desdeArchivo(nombreArchivo);
    }

    class DibujoPanel extends JPanel {
        public DibujoPanel() {
            addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    if (modoSeleccion) {
                        
                        trazoSeleccionado = dibujo.seleccionarTrazo(e.getX(), e.getY());
                        if (trazoSeleccionado != null) {
                            System.out.println("Trazo seleccionado: " + trazoSeleccionado);
                        } else {
                            System.out.println("No se seleccionó ningún trazo.");
                        }
                    } else {
                        
                        puntoInicial = e.getPoint(); 
                    }
                }

                public void mouseReleased(MouseEvent e) {
                    if (!modoSeleccion) {
                        Point puntoFinal = e.getPoint(); 
                        try {
                            Tipotrazo tipoDeTrazo = Tipotrazo.valueOf(((String) comboTipos.getSelectedItem()).toUpperCase());
                            dibujo.agregar(new Trazo(tipoDeTrazo, puntoInicial.x, puntoInicial.y, puntoFinal.x, puntoFinal.y));
                            repaint();
                        } catch (IllegalArgumentException ex) {
                            JOptionPane.showMessageDialog(null, "Tipo de trazo no válido");
                        }
                    }
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            dibujo.dibujar(g);
        }
    }
}