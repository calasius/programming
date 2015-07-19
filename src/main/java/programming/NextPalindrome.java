package programming;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Scanner;

class NextPalindrome {
	
	public static void main(String[] args) throws FileNotFoundException {
		FileInputStream stream = new FileInputStream("palindromes.txt");
		System.setIn(stream);
		Scanner scanner = new Scanner(System.in);
		int examples = scanner.nextInt();
		scanner.nextLine();
		for (int i = 0; i < examples; i++) {
			String line = scanner.nextLine().trim();
			while (line.isEmpty()) {
				line = scanner.nextLine();
			}
			int[] number = readNumber(line);
			int[] palindrome = next(number);
			System.out.println(String.format("Numero = %s, Next Palindromo = %s, Menores = %s", toString(number),toString(palindrome),contarPalindromosMenores(number)));
		}
	}
	
	private static int[] readNumber(String line) {
		int[] number = new int[line.length()];
		for (int i = 0; i < line.length(); i++) {
			number[i] = Integer.valueOf(""+line.charAt(i));
		}
		return number;
	}
	
	private static int[] next(int[] number) {
		int[] copy = Arrays.copyOf(number, number.length);
		int extra = copy.length % 2 == 0 ? 0 : 1;
		int index = copy.length/2 + extra - 1;
		boolean incrementar = false;
		boolean iguales = true;
		for(int i = (copy.length/2)-1; i >= 0; i--) {
			if (copy[i] < copy[copy.length-1-i] && iguales) {
				iguales = false;
				incrementar = true;
			} else if (copy[i] > copy[copy.length-1-i]) {
				iguales = false;
			}
			copy[copy.length-1-i] = copy[i];
		}
		if (incrementar || iguales) {
			boolean hasCarry = false;
			if (copy[index] == 9) {
				hasCarry = true;
				copy[index] = 0;
				if (extra == 0) {
					copy[copy.length-1-index] = 0;				
				}
				for (int i = index-1; i >= 0 && hasCarry; i--) {
					if (copy[i] == 9) {
						copy[i] = 0;
						copy[copy.length-1-i] = 0;
						hasCarry = true;
					} else {
						hasCarry = false;
						copy[i] = copy[i] + 1;
						copy[copy.length-1-i] = copy[i];
					}
				}
			} else {
				copy[index] = copy[index] + 1;
				if (extra == 0) {
					copy[index+1] = copy[index];
				}
			}
			
			if (hasCarry) {
				copy = new int[copy.length+1];
				Arrays.fill(copy, 0);
				copy[0] = 1;
				copy[copy.length-1] = 1;
			}
		}
		
		return copy;
	}

	private static String toString(int[] number) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < number.length; i++) {
			buffer.append(number[i]);
		}
		return buffer.toString();
	}
	
	public static BigInteger contarPalindromosMenores(int[] number) {
		if (isPalindrome(number)) {
			return palindromeIndex(number).subtract(BigInteger.ONE);			
		} else {
			return palindromeIndex(next(number)).subtract(BigInteger.ONE);
		}
	}
	
	public static BigInteger contarPalindromoMenoresAUnaPotenciaDe10(int potencia)  {
		if (potencia % 2 == 0) {
			return BigInteger.valueOf(10).pow(potencia/2).subtract(BigInteger.ONE).multiply(BigInteger.valueOf(2));
		} else {
			return BigInteger.valueOf(10).pow((potencia-1)/2).multiply(BigInteger.valueOf(11)).subtract(BigInteger.valueOf(2));
		}
	}
	
	public static BigInteger palindromeIndex(int[] number) {
		int[] left = left(number);
		BigInteger res = BigInteger.valueOf(10).pow(number.length/2).add(BigInteger.valueOf(Integer.valueOf(toString(left))));
		return res;
	}
	
	private static int[] left(int[] number) {
		int extra = number.length % 2 == 0 ? 0 : 1;
		int lenght = (number.length / 2) + extra;
		int[] l = new int[lenght];
		for (int i = 0; i < lenght; i++) {
			l[i] = number[i];
		}
		return l;
	}
	
	private static boolean isPalindrome(int[] number) {
		for (int i = 0; i < number.length/2; i++) {
			if (number[i] != number[number.length-1-i]) {
				return false;
			}
		}
		return true;
	}

}
