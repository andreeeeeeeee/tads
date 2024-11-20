import java.util.ArrayList;
import java.util.List;

class Listas {
    static String primeiro(List<String> lista) {
        String res = "";
        if (lista.size() >= 1) {
            res = lista.get(0);
        } else {
            res = null;
        }

        return res;
    }

    static String segundo(List<String> lista) {
        String res = "";
        if (lista.size() >= 2) {
            res = lista.get(1);
        } else {
            res = null;
        }

        return res;
    }

    static String ultimo(List<String> lista) {
        String res = "";
        if (lista.size() > 0) {
            res = lista.get(lista.size() - 1);
        } else {
            res = null;
        }

        return res;
    }

    static String penultimo(List<String> lista) {
        String res = "";
        if (lista.size() > 1) {
            res = lista.get(lista.size() - 2);
        } else {
            res = null;
        }

        return res;
    }

    static List<String> sem(List<String> lista, String removivel) {
        List<String> res = new ArrayList<>(lista);

        for (int i = 0; i < res.size(); i++) {
            if (removivel.equals(res.get(i))) {
                res.remove(i);
                i--;
            }
        }

        return res;
    }

    static List<String> mesclar(List<String> lista1, List<String> lista2) {
        List<String> res = new ArrayList<>(lista1);

        for (int i = 0; i < lista2.size(); i++) {
            res.add(lista2.get(i));
        }

        return res;
    }

    static List<String> remover(List<String> lista1, List<String> lista2) {
        List<String> res = new ArrayList<>(lista1);

        for (int i = 0; i < lista2.size(); i++) {
            res = sem(res, lista2.get(i));
        }

        return res;
    }

    static List<String> combinar(List<String> lista1, List<String> lista2) {
        List<String> res = new ArrayList<>();

        for (int i = 0; i < Math.max(lista1.size(), lista2.size()); i++) {
            if (i < lista1.size()) {
                res.add(lista1.get(i));
            }
            if (i < lista2.size()) {
                res.add(lista2.get(i));
            }
        }

        return res;
    }

    // static List<String> compactar(List<String> lista) {
    // List<String> res = new ArrayList<>();

    // for (int i = 0; i < lista.size(); i++) {
    // if (!(lista.get(i).equals(null))) {
    // res.add(lista.get(i));
    // }
    // }

    // return res;
    // }

    static boolean igual(List<String> lista, List<String> lista2) {
        boolean res = true;

        if (lista.size() == lista2.size()) {
            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i) != lista2.get(i)) {
                    res = false;
                }
            }
        } else {
            res = false;
        }

        return res;
    }

    static List<List<String>> agrupar(List<String> lista, int num) {
        List<List<String>> res = new ArrayList<>();

        for (int i = 0; i < lista.size();) {
            List<String> list = new ArrayList<>();
            for (int j = 0; j < num; j++) {
                list.add(lista.get(i));
                i++;
            }
            res.add(list);
        }

        return res;
    }

}
