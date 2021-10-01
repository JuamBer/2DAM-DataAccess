
package Patterns;

import java.util.ArrayList;


public class Patterns {
    public static void main(String[] args) {
        System.out.println("000ABCHO:"+lookForPatterns("000ABCHO")+"\n\n\n");
        System.out.println("00:"+lookForPatterns("00")+"\n\n\n");
        System.out.println("1010101:"+lookForPatterns("1010101")+"\n\n\n");
    }
    public static int lookForPatterns(String text){
        int result = 0;
        ArrayList<String> patterns = new ArrayList<String>();
        
        patterns.add("00");
        patterns.add("101");
        patterns.add("ABC");
        patterns.add("HO");
        
        for (int i = 0; i < text.length(); i++) {
            System.out.println("Position: "+i);    
            for (int j = 0; j < patterns.size(); j++) {
                int checklength = patterns.get(j).length();
                System.out.println("\tChecking "+patterns.get(j)+" pattern");
               
                if(i+checklength <= text.length()){
                    if(patterns.get(j).equals(text.substring(i,i+checklength))){
                        System.out.println("\t\tDetected: "+patterns.get(j));
                        result++;
                    }
                }
            }
        }
        return result;
    }

}
