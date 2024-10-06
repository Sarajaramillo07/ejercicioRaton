
import java.io.*;
import javax.swing.*;

public class Archivo {

    
    public static String elegirArchivo() {
        JFileChooser fc = new JFileChooser();
        if (fc.showOpenDialog(new JFrame()) == JFileChooser.APPROVE_OPTION) {
            File f = fc.getSelectedFile();
            return f.getAbsolutePath();
        } else {
            return "";
        }
    }

    
    public static BufferedReader abrirArchivo(String nombreArchivo) {
        File f = new File(nombreArchivo);
        if (f.exists()) {
            try {
                FileReader fr = new FileReader(f);
                return new BufferedReader(fr);
            } catch (IOException e) {
                return null;
            }
        } else {
            return null;
        }
    }

    
    public static boolean guardarArchivo(String nombreArchivo, String[] lineas) {
        if (lineas != null) {
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(nombreArchivo));
                for (String linea : lineas) {
                    bw.write(linea);
                    bw.newLine();
                }
                bw.close();
                return true;
            } catch (IOException e) {
                return false;
            }
        } else {
            return false;
        }
    }
}