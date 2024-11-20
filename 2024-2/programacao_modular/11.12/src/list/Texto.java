package list;

import java.util.ArrayList;
import java.util.List;

public class Texto {
    static List<String> split(String str, char separador) {
        List<String> partes = new ArrayList<>();
        int indexStr = 0;

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == separador) {
                partes.add(str.substring(indexStr, i));
                indexStr=i+1;
            }
        } 
        partes.add(str.substring(indexStr));

        return partes;
    }
}
