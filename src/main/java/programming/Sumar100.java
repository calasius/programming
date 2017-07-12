package programming;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

/**
 * Created by claudio on 7/12/17.
 */
public class Sumar100 {

    private static BiFunction<Integer,Integer,Integer> suma = (n1, n2) -> n1+n2;
    private static BiFunction<Integer,Integer,Integer> resta = (n1, n2) -> n1+n2;
    public static void main(String[] args) {
        for (int i = 1; i <= 8; i++) {
            for (int j = i+1; j <= 8; j++) {
                for (int k = j+1; k <= 8; k++) {
                    Set<List<Integer>> expresiones = crearExpresiones(getNumeros(i,j,k));
                    expresiones.stream().filter(integers -> integers.stream().mapToInt(Integer::intValue).sum() == 100)
                    .forEach(integers -> System.out.println(integers));
                }
            }
        }
    }

    private static Set<List<Integer>> crearExpresiones(List<Integer> numeros) {
        Set<List<Integer>> expresiones = Sets.newHashSet();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    List<Integer> expresion = Lists.newArrayList();
                    expresion.add(numeros.get(0));
                    expresion.add((int)Math.pow(-1,i) * numeros.get(1));
                    expresion.add((int)Math.pow(-1,j) * numeros.get(2));
                    expresion.add((int)Math.pow(-1,k) * numeros.get(3));
                    expresiones.add(expresion);
                }
            }
        }
        return expresiones;
    }

    private static List<Integer> getNumeros(int i, int j, int k) {
        List<Integer> numeros = Lists.newArrayList();
        numeros.add(getNumero(0,i));
        numeros.add(getNumero(i,j));
        numeros.add(getNumero(j,k));
        numeros.add(getNumero(k,9));
        return numeros;
    }

    private static int getNumero(int inicio, int fin) {
        String s1 = "";
        for (int l = inicio; l < fin; l++) {
            s1 += l+1;
        }
        return Integer.valueOf(s1);
    }
}
