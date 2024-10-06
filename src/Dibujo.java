import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;


public class Dibujo {
    private ArrayList<Trazo> trazos;

    public Dibujo() {
        trazos = new ArrayList<>();
    }

    public void agregar(Trazo trazo) {
        trazos.add(trazo);
    }

    public void eliminar(int x, int y) {
        
        trazos.removeIf(trazo -> trazo.contiene(x, y));
    }

    public void eliminarUltimoTrazo() {
        if (!trazos.isEmpty()) {
            trazos.remove(trazos.size() - 1);
        }
    }

    public Trazo seleccionarTrazo(int x, int y) {
        for (Trazo trazo : trazos) {
            if (trazo.contiene(x, y)) {
                return trazo;
            }
        }
        return null;
    }

    public void dibujar(Graphics g) {
        for (Trazo trazo : trazos) {
            trazo.dibujar(g);
        }
    }

    public String[] aArchivo() {
        ArrayList<String> lineas = new ArrayList<>();
        for (Trazo trazo : trazos) {
            lineas.add(trazo.aArchivo());
        }
        return lineas.toArray(new String[0]);
    }

    public void desdeArchivo(String nombreArchivo) {
        BufferedReader br = Archivo.abrirArchivo(nombreArchivo);
        if (br != null) {
            try {
                trazos.clear();
                String linea;
                while ((linea = br.readLine()) != null) {
                    trazos.add(Trazo.desdeArchivo(linea));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}