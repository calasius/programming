package programming;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GridSearch {
	
	

	public static void main(String[] args) throws FileNotFoundException {
		FileInputStream stream = new FileInputStream("test2.in");
		System.setIn(stream);
		Scanner scanner = new Scanner(System.in);
		int tests = scanner.nextInt();
		Map<Character, List<Point>> numbers = null; 
		for (int i = 0; i < tests; i++) {
			numbers = new HashMap<Character, List<Point>>();
			char[][] matrix = readMatrix(scanner,numbers);
			char[][] pattern = readMatrix(scanner, null);
			
			List<Point> points = numbers.get(pattern[0][0]);
			Boolean found = false;
			if (points != null) {
				for (Point point : points) {
					if (isPatternFound(matrix,pattern,point)) {
						found = true;
						break;
					}
				}
			}
			System.out.println(found.toString());
		}
	}

	private static boolean isPatternFound(char[][] matrix, char[][] pattern, Point point) {
		int prows = rows(pattern);
		int pcols = cols(pattern);
		if (!fitPatternAtPoint(matrix,pattern,point)) {
			return false;
		}
		for (int row = 0; row < prows; row++) {
			for (int col = 0; col < pcols; col++) {
				if (pattern[row][col] != matrix[row+point.getRow()][col+point.getCol()]) {
					return false;
				}
			}
		}
		return true;
	}

	private static boolean fitPatternAtPoint(char[][] matrix, char[][] pattern,
			Point point) {
		int prows = rows(pattern);
		int pcols = cols(pattern);
		
		int mrows = rows(matrix);
		int mcols = cols(matrix);
		return (point.getRow() + prows <= mrows) && (point.getCol() + pcols <= mcols);
	}

	private static int cols(char[][] pattern) {
		return pattern[0].length;
	}

	private static int rows(char[][] pattern) {
		return pattern.length;
	}

	private static char[][] readMatrix(Scanner scanner, Map<Character, List<Point>> numbers) {
		int rows = scanner.nextInt();
		int columns = scanner.nextInt();
		char[][] matrix = new char[rows][columns];
		String line = null;
		Character c = null;
		scanner.nextLine();
		for(int row = 0; row < rows; row++) {
			line = scanner.nextLine();
			for (int col = 0; col < columns; col++) {
				c = line.charAt(col);
				matrix[row][col] = c;
				if (numbers != null) {
					if (numbers.containsKey(c)) {
						numbers.get(c).add(new Point(row, col));
					} else {
						List<Point> points = new ArrayList<Point>();
						points.add(new Point(row, col));
						numbers.put(c, points);
					}
				}
			}
		}
		return matrix;
	}
	
	public static class Point {
		private final int row;
		private final int col;
		
		public Point(int row, int col) {
			this.row = row;
			this.col = col;
		}

		public int getRow() {
			return row;
		}

		public int getCol() {
			return col;
		}
	}

}
