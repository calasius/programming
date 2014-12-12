package programming;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        List<List<Integer>> b = Lists.<List<Integer>> newArrayListWithCapacity(10);
        for (int i = 0; i < 10; i++) {
        	b.add(Lists.<Integer> newArrayList());
        }
        System.out.println(b.size());
    }
}
