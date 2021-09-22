package Dictionary;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Dictionary {

    public static void main(String[] args) {

        crearCarpeta();
        programa();

    }

    private static void crearCarpeta() {
        String urlCarpeta = "src\\Dictionary\\Dictionary";
        File carpeta = new File(urlCarpeta);
        carpeta.mkdirs();
    }

    private static void programa() {
        String palabras[] = null;
        int i = 0;

        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        String linea;
        String texto;
        String letra;
        String palabra;
        ArrayList<String> letras = new ArrayList<String>();
        String urlArchivoProv = "src\\Dictionary\\Dictionary\\";
        boolean existeletra = false;

        try {
            archivo = new File("src\\Dictionary\\Imagine.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            while ((linea = br.readLine()) != null) {
                palabras = linea.split(" ");

                if (!(linea.equals(""))) {
                    for (i = 0; i < palabras.length; i++) {
                        palabra = palabras[i];
                        letra = palabras[i].substring(0, 1).toUpperCase();

                        for (int j = 0; j < letras.size(); j++) {
                            if (letras.get(j).equals(letra)) {
                                existeletra = true;
                                File letrarepetida = new File(urlArchivoProv + letra + ".txt");
                                FileWriter letrarepetidawriter = new FileWriter(letrarepetida, true);
                                BufferedWriter bw = new BufferedWriter(letrarepetidawriter);
                                bw.write("\n" + palabra);
                                bw.close();
                            }
                        }

                        if (existeletra) {
                            existeletra = false;

                        } else {
                            letras.add(letra);
                            File letranueva = new File(urlArchivoProv + letra + ".txt");

                            try {
                                letranueva.createNewFile();
                                FileWriter letranuevawriter = new FileWriter(letranueva);
                                BufferedWriter bw = new BufferedWriter(letranuevawriter);
                                bw.write(palabra);
                                bw.close();

                            } catch (Exception ex) {
                                System.out.println(ex.getMessage());
                            }
                        }

                    }
                }
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

}
