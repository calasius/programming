package programming.partition;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

import org.apache.commons.math3.primes.Primes;
import org.apache.commons.math3.util.ArithmeticUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class BestPartition {
	
	

	public static List<Integer> bestPartition(int n) {
		BigInteger cycleLenght = BigInteger.valueOf(1);
		List<List<Integer>> b = Lists.<List<Integer>> newArrayListWithCapacity(n);
				b.add(Lists.<Integer> newArrayList(1));
				b.add(Lists.<Integer> newArrayList(2));
				b.add(Lists.<Integer> newArrayList(3));
		;
		int i = 2;
		int p = 1;

		if (Primes.isPrime(p)) {
			 if (p + i <= n && coprime(p,b.get(i))) {
				 List<Integer> newPartition = Lists.<Integer> newArrayList();
				 newPartition.addAll(b.get(i));
				 newPartition.add(p);
				 b.set(i+p, newPartition);
			 } else {
				 
			 }
		} else if (powerOfPrime(p)) {
			
		}
		
		return b.get(n-1);

	}

	private static boolean powerOfPrime(int p) {
		List<Integer> factors = Primes.primeFactors(p);
		Set<Integer> distintcFactors = Sets.<Integer> newHashSet();
		distintcFactors.addAll(factors);
		return factors.size() > 0 && distintcFactors.size() == 1;
	}

	private static boolean coprime(int p, List<Integer> partition) {
		for (Integer k : partition) {
			if (ArithmeticUtils.gcd(p, k) != 1) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {

	}

}
