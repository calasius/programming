package programming.partition;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.commons.math3.primes.Primes;
import org.apache.commons.math3.util.ArithmeticUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class BestPartition {

	public static List<Integer> bestPartition(int n) {
		BigInteger newMCM = null;
		BigInteger oldMCM = null;
		int sum = 0;
		List<List<Integer>> b = initializePartitions(n);
		List<BigInteger> L = initializeMinCommonMultiplier(n);
		int i = 1;
		int p = 1;

		while (i < n) {
			while(p < n) {
				if (Primes.isPrime(p)) {
					sum = p + i + 1;
					if (sum <= n && coprime(p, b.get(i))) {
						newMCM = L.get(i).multiply(BigInteger.valueOf(p));
						oldMCM = L.get(sum-1);
						if (newMCM.compareTo(oldMCM) > 0) {
							L.set(sum-1, newMCM);
							updatePartition(b, i, p);
						}
					} 
				} else if (p > 1) {
					List<Integer> factors = Primes.primeFactors(p);
					int r = factors.size();
					sum = p + i + 1;
					if (powerOfPrime(factors)) {
						if (sum <= n && coprime(p, b.get(i))) {
							newMCM = L.get(i).multiply(BigInteger.valueOf(p));
							oldMCM = L.get(sum-1);
							if (newMCM.compareTo(oldMCM) > 0) {
								L.set(sum-1, newMCM);
								updatePartition(b, i, p);
							}
						} else if (sum <= n){
							Integer q = findNoComprime(b, i, p);
							List<Integer> factorsq = Primes.primeFactors(q);
							int s = factorsq.size();
							sum = i + p - q + 1;
							if (r > s && sum <= n) {
								newMCM = L.get(i).multiply(BigInteger.valueOf(factors.get(0)).pow(r-s));
								oldMCM = L.get(sum-1);
								if (newMCM.compareTo(oldMCM) > 0) {
									L.set(sum-1, newMCM);
									updatePartition(b, i, p, q);
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

		System.out.println(L.get(n-1));
		Collections.sort(b.get(n - 1));
		return b.get(n - 1);
	}

	private static BigInteger multiply(List<Integer> list) {
		BigInteger res = BigInteger.valueOf(1);
		for (Integer integer : list) {
			res = res.multiply(BigInteger.valueOf(integer));
		}
		return res;
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
		b.set(i+p, Lists.<Integer> newArrayList());
		b.get(i+p).addAll(b.get(i));
		b.get(i+p).add(p);
	}
	
	private static void updatePartition(List<List<Integer>> b, int i, int p, int q) {
		b.set(i+p-q, Lists.<Integer> newArrayList());
		b.get(i+p-q).addAll(b.get(i));
		b.get(i+p-q).remove(Integer.valueOf(q));
		b.get(i+p-q).add(p);
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
