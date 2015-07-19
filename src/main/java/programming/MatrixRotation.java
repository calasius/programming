package programming;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MatrixRotation {

	public static void main(String[] args) throws FileNotFoundException {
		FileInputStream stream = new FileInputStream("matrix_rotation_1.txt");
		System.setIn(stream);
		Scanner scanner = new Scanner(System.in);
		int rows = scanner.nextInt();
		int columns = scanner.nextInt();
		int shift = scanner.nextInt();
		int[][] matrix = readMatrix(scanner, rows, columns, shift);
		
		showMatrix(matrix, rows, columns);
	}
	
	private static void showMatrix(int[][] matrix, int rows, int columns) {
		for (int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
	}

	private static int[][] readMatrix(Scanner scanner, int rows, int columns, int shift) {
		int[][] matrix = new int[rows][columns];
		Integer c = null;
		for(int row = 0; row < rows; row++) {
			scanner.nextLine();
			for (int col = 0; col < columns; col++) {
				c = scanner.nextInt();
				Point point = rotatePoint(new Point(row, col), shift, rows, columns);
				matrix[point.getRow()][point.getCol()] = c;
			}
		}
		return matrix;
	}
	
	private static Point rotatePoint(Point point, int k, int rows, int cols) {
		Point rotatedPoint = point;
		int circuit = circuit(point, rows, cols);
		int circuitSize = circuitSize(rows, cols, circuit);
		k = k % circuitSize;
		while(k != 0) {
			if (canMoveleft(point, circuit, rows, cols)) {
				if(point.getCol() - k >= circuit) {
					rotatedPoint = new Point(point.getRow(), point.getCol() - k);
					k = 0;
				} else {
					k = k - (point.getCol() - circuit);
					point = new Point(point.getRow(), circuit);
				}
			} else if (canMoveDown(point, circuit, rows, cols)) {
				if(point.getRow() + k <= rows - 1 - circuit) {
					rotatedPoint = new Point(point.getRow() + k, point.getCol());
					k = 0;
				} else {
					k = k - (rows - 1 - circuit - point.getRow());
					point = new Point(rows - 1 - circuit, point.getCol());
				}
			} else if (canMoveRight(point, circuit, rows, cols)) {
				if (point.getCol() + k <= cols - 1 - circuit) {
					rotatedPoint = new Point(point.getRow(), point.getCol() + k);
					k = 0;
				} else {
					k = k - (cols - 1 - circuit - point.getCol());
					point = new Point(point.getRow(), cols - 1 - circuit);
				}
			} else if (canMoveUp(point, circuit, rows, cols)) {
				if (point.getRow() - k >= circuit) {
					rotatedPoint = new Point(point.getRow() - k, point.getCol());
					k = 0;
				} else {
					k = k - (point.getRow() - circuit);
					point = new Point(circuit, point.getCol());
				}
			}
			
		}
		return rotatedPoint;
	}
	
	private static boolean canMoveUp(Point point, int circuit, int rows, int cols) {
		return point.getCol() == cols - 1 - circuit && !(point.getRow() == circuit);
	}

	private static boolean canMoveRight(Point point, int circuit, int rows, int cols) {
		return point.getRow() == rows - 1 - circuit && !(point.getCol() == cols - 1 - circuit);
	}

	private static boolean canMoveDown(Point point, int circuit, int rows, int cols) {
		return point.getCol() == circuit && !(point.getRow() == rows - 1 - circuit);
	}

	private static int circuitSize(int rows, int cols, int circuit) {
		return 2*((rows - 2*circuit) + (cols-2*circuit)) -4;
	}

	private static boolean canMoveleft(Point point, int circuit, int rows, int cols) {
		return point.getRow() == circuit && !(point.getCol() == circuit);
	}

	private static int circuit(Point point, int rows, int cols) {
		int distFistRow = point.getRow();
		int distLastRow = rows - 1 - point.getRow();
		int distFirstCol = point.getCol();
		int distLastCol = cols - 1 - point.getCol();
		int min1 = Math.min(distLastRow, distFistRow);
		int min2 = Math.min(distLastCol, distFirstCol);
		return Math.min(min1, min2);
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
