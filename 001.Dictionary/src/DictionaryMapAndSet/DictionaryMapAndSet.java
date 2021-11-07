package DictionaryMapAndSet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.function.Consumer;

public class DictionaryMapAndSet {

    public static void main(String[] args) {

        crearCarpeta();

        File archivo;
        FileReader fr;
        BufferedReader br;

        String linea;
        String palabra;
        String inicial;

        String palabras[];
        ArrayList<String> palabrasTotales = new ArrayList<>();
        HashMap<String, HashSet<String>> iniciales = new HashMap<>();
        HashSet<String> palabrasporiniciales = new HashSet<>();

        try {
            archivo = new File("src\\DictionaryMapAndSet\\Imagine.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            while ((linea = br.readLine()) != null) {
                if (!(linea.equals(""))) {
                    palabras = linea.split(" ");

                    for (String palabra1 : palabras) {
                        palabra = palabra1;
                        palabrasTotales.add(palabra);
                        inicial = palabra.substring(0, 1).toUpperCase();
                        palabrasporiniciales.add(palabra);
                        iniciales.put(inicial, new HashSet<>());
                    }
                }
            }

            iniciales.entrySet().forEach(new Consumer<Map.Entry<String, HashSet<String>>>() {
                @Override
                public void accept(Map.Entry<String, HashSet<String>> entry) {
                    String key = entry.getKey();
                    HashSet<String> value = entry.getValue();
                    
                    File letranueva = new File("src\\DictionaryMapAndSet\\Dictionary\\" + key + ".txt");
                    
                    try {
                        letranueva.createNewFile();
                    } catch (IOException e) {
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
                                } catch (IOException e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                            
                        }

                    }
                }
            });

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    private static void crearCarpeta() {
        String urlCarpeta = "src\\DictionaryMapAndSet\\Dictionary";
        File carpeta = new File(urlCarpeta);
        carpeta.mkdirs();
    }

}
