package programming.hackersrank;

import com.google.common.collect.Lists;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Created by claudio on 7/22/17.
 */
public class PrimesToJavaArray {
    public static void main(String... args) throws FileNotFoundException {
        List<Integer> primes = Lists.newArrayList();
        Scanner scanner = new Scanner(new File("src/main/resources/primes"));
        while (scanner.hasNext()) {
            primes.add(scanner.nextInt());
        }
        System.out.println(primes);
//        System.out.println(primes.size());
    }
}
