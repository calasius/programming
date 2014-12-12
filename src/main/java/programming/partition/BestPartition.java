package programming.partition;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.math3.primes.Primes;
import org.apache.commons.math3.util.ArithmeticUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class BestPartition {

	public static List<Integer> bestPartition(int n) {
		
		List<List<Integer>> b = initializePartitions(n);
		List<BigInteger> L = initializeMinCommonMultiplier(n);
		int i = 1;
		int p = 1;

		while (i < n) {
			while(p < n) {
				if (Primes.isPrime(p)) {
					if (p + i + 1 <= n && coprime(p, b.get(i))) {
						updatePartition(b, i, p);
					} 
				} else if (p > 1) {
					List<Integer> factors = Primes.primeFactors(p);
					int r = factors.size();
					if (powerOfPrime(factors)) {
						if (p + i + 1 <= n && coprime(p, b.get(i))) {
							if (L.get(i).multiply(BigInteger.valueOf(p))
									.compareTo(L.get(i + p - 1)) > 0) {
								updatePartition(b, i, p);
								L.set(i + p - 1, L.get(i).multiply(BigInteger.valueOf(p)));
							}
						} else if (p + i + 1 <= n){
							Integer q = findNoComprime(b, i, p);
							List<Integer> factorsq = Primes.primeFactors(q);
							int s = factorsq.size();
							if (r > s && i + p - q + 1 <= n) {
								if (L.get(i).multiply(BigInteger.valueOf(factors.get(0)).pow(r-s)).compareTo(L.get(i + p - q - 1)) > 0) {
									updatePartition(b, i, p, q);
									L.set(i + p - q - 1, L.get(i).multiply(BigInteger.valueOf(factors.get(0)).pow(r-s)));
								}
							}
						}				
					}
				}
				p = p + 1;
			}
			i = i + 1;
			p = 1;
		}

		System.out.println(L);
		return b.get(n - 2);

	}

	private static Integer findNoComprime(List<List<Integer>> b, int i, int p) {
		Integer res = 0;
		for (Integer k : b.get(i)) {
			if (ArithmeticUtils.gcd(p, k) != 1) {
				res =  k;
			}
		}
		return res;
	}

	private static void updatePartition(List<List<Integer>> b, int i, int p) {
		b.get(i+p-1).clear();
		b.get(i+p-1).addAll(b.get(i));
		b.get(i+p-1).add(p);
	}
	
	private static void updatePartition(List<List<Integer>> b, int i, int p, int q) {
		b.get(i+p-q-1).clear();
		b.get(i+p-q-1).addAll(b.get(i));
		b.get(i+p-q-1).remove(Integer.valueOf(q));
		b.get(i+p-q-1).add(p);
	}

	private static List<BigInteger> initializeMinCommonMultiplier(int n) {
		List<BigInteger> L = Lists.<BigInteger> newArrayListWithCapacity(n);
		for (int i = 0; i < n; i++) {
			L.add(BigInteger.valueOf(1));
		}
		
		L.set(0, BigInteger.valueOf(1));
		L.set(1, BigInteger.valueOf(2));
		L.set(2, BigInteger.valueOf(3));
		
		return L;
	}

	private static List<List<Integer>> initializePartitions(int n) {
		List<List<Integer>> b = Lists
				.<List<Integer>> newArrayListWithCapacity(n);
		for (int i = 0; i < n; i++) {
			b.add(Lists.<Integer> newArrayList());
		}
		
		b.get(0).add(1);
		b.get(1).add(2);
		b.get(2).add(3);
		
		return b;
	}

	private static boolean powerOfPrime(List<Integer> factors) {
		Set<Integer> distintcFactors = Sets.<Integer> newHashSet();
		distintcFactors.addAll(factors);
		return factors.size() > 1 && distintcFactors.size() == 1;
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
		System.out.println(bestPartition(100));
	}

}
