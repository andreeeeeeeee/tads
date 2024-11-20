package array;

public class Texto {
    static String[] split(String str, char separador) {
        int qtdPartes = 1;
        for (int i = 0; i < str.length(); i++) 
            if (str.charAt(i) == separador) qtdPartes++;

        String[] partes = new String[qtdPartes];
        int indexPartes = 0;
        int indexStr = 0;

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == separador) {
                partes[indexPartes] = str.substring(indexStr, i);
                indexPartes++;
                indexStr=i+1;
            } else if (i == str.length()-1) 
                partes[indexPartes] = str.substring(indexStr);
        }

        return partes;
    }
}
