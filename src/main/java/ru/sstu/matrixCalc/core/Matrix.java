package ru.sstu.matrixCalc.core;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Matrix {

    private double[][] matrix;

    public Matrix(double[][] matrix) {
        this.matrix = new double[matrix.length][];

        for (int i = 0; i < matrix.length; i++) {
            this.matrix[i] = matrix[i].clone();
        }
    }

    public Matrix(int rowCount, int columnsCount) {
        matrix = new double[rowCount][columnsCount];
    }

    public static Matrix add(Matrix m1, Matrix m2) {
        if (m1.getRowsCount() != m2.getRowsCount()) {
            throw new IllegalArgumentException("Matrices' row count must match");
        }

        if (m1.getColumnsCount() != m2.getColumnsCount()) {
            throw new IllegalArgumentException("Matrices' column count must match");
        }

        int rowCount = m1.getRowsCount();
        int columnCount = m1.getColumnsCount();

        Matrix result = new Matrix(rowCount, columnCount);

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                result.setValue(i, j, m1.getValue(i, j) + m2.getValue(i, j));
            }
        }

        return result;
    }

    public static Matrix subtract(Matrix m1, Matrix m2) {
        return add(m1, multiply(m2, -1));
    }

    public static Matrix multiply(Matrix m1, Matrix m2) {
        if (m1.getColumnsCount() != m2.getRowsCount()) {
            throw new IllegalArgumentException("m1's columns count and m2's row count must match");
        }

        int sideLength = m1.getColumnsCount();
        int rowCount = m1.getRowsCount();
        int columnCount = m2.getColumnsCount();

        Matrix result = new Matrix(rowCount, columnCount);

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                int value = 0;
                for (int k = 0; k < sideLength; k++) {
                    value += m1.getValue(i, k) * m2.getValue(k, j);
                }
                result.setValue(i, j, value);
            }
        }

        return result;
    }

    public static Matrix multiply(Matrix m, double value) {
        int rowCount = m.getRowsCount();
        int columnCount = m.getColumnsCount();

        Matrix result = new Matrix(rowCount, columnCount);

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                result.setValue(i, j, value * m.getValue(i, j));
            }
        }

        return result;
    }

    public static Matrix pow(Matrix m, int value) {
        Matrix result = new Matrix(m.getData());

        for (int i = 1; i < value; i++) {
            result = multiply(result, m);
        }

        return result;
    }

    public static Matrix transpose(Matrix m) {
        int rowCount = m.getColumnsCount();
        int columnCount = m.getRowsCount();

        Matrix result = new Matrix(rowCount, columnCount);

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                result.setValue(j, i, m.getValue(i, j));
            }
        }

        return result;
    }

    public double getValue(int row, int column) {
        return matrix[row][column];
    }

    public void setValue(int row, int column, double value) {
        matrix[row][column] = value;
    }

    public int getRowsCount() {
        return matrix.length;
    }

    public int getColumnsCount() {
        if (getRowsCount() == 0) {
            return 0;
        }

        return matrix[0].length;
    }

    public double[][] getData() {
        int rowCount = getRowsCount();
        int columnCount = getColumnsCount();
        double[][] result = new double[rowCount][columnCount];

        for (int i = 0; i < rowCount; i++) {
            result[i] = matrix[i].clone();
        }

        return result;
    }

    @Override
    public String toString() {
        return Arrays.stream(matrix)
                .map(row -> Arrays.stream(row)
                        .mapToObj(value -> {
                            String num = String.valueOf(value);
                            if (value % 1 == 0) {
                                return num.substring(0, num.indexOf("."));
                            }
                            return num;
                        })
                        .collect(Collectors.joining(" "))
                ).collect(Collectors.joining("\n"));
    }
}
