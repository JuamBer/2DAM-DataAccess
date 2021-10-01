package DictionaryMapAndSet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class DictionaryMapAndSet {

    public static void main(String[] args) {

        crearCarpeta();

        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        String linea;
        String palabra;
        String inicial;

        String palabras[] = null;
        ArrayList<String> palabrasTotales = new ArrayList<String>();
        HashMap<String, HashSet<String>> iniciales = new HashMap<>();
        HashSet<String> palabrasporiniciales = new HashSet<>();

        try {
            archivo = new File("src\\DictionaryMapAndSet\\Imagine.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            while ((linea = br.readLine()) != null) {
                if (!(linea.equals(""))) {
                    palabras = linea.split(" ");

                    for (int i = 0; i < palabras.length; i++) {
                        palabra = palabras[i];
                        palabrasTotales.add(palabra);
                        inicial = palabra.substring(0, 1).toUpperCase();
                        palabrasporiniciales.add(palabra);
                        iniciales.put(inicial, new HashSet<String>());
                    }
                }
            }

            for (Map.Entry<String, HashSet<String>> entry : iniciales.entrySet()) {
                String key = entry.getKey();
                HashSet<String> value = entry.getValue();

                File letranueva = new File("src\\DictionaryMapAndSet\\Dictionary\\" + key + ".txt");

                try {
                    letranueva.createNewFile();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                for (int i = 0; i < palabrasTotales.size(); i++) {

                    String inicialpalabra = palabrasTotales.get(i).substring(0, 1).toUpperCase();
                    if (key.equals(inicialpalabra)) {
                        if (value.add(palabrasTotales.get(i))) {
                            try {
                                FileWriter letranuevawriter = new FileWriter(letranueva, true);
                                BufferedWriter bw = new BufferedWriter(letranuevawriter);
                                bw.write(palabrasTotales.get(i) + "\n");
                                bw.close();
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }

                    }

                }

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private static void crearCarpeta() {
        String urlCarpeta = "src\\DictionaryMapAndSet\\Dictionary";
        File carpeta = new File(urlCarpeta);
        carpeta.mkdirs();
    }

}
