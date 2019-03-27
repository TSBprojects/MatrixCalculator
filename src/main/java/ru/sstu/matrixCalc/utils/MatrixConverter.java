package ru.sstu.matrixCalc.utils;

import ru.sstu.matrixCalc.exception.IncorrectMatrixException;

import java.util.Arrays;
import java.util.stream.Collectors;

public class MatrixConverter {

    private static int lineLength = 0;

    private MatrixConverter() {
    }

    public static double[][] convert(String matrix) {

        matrix = matrix.trim();

        double[][] resMatrix = Arrays.stream(matrix.split("\n"))
                .map(s -> {

                    double[] arr = parseArray(s.split(" "));

                    lineLength = lineLength == 0 ? arr.length : lineLength;
                    if (lineLength != arr.length) {
                        throw new IncorrectMatrixException();
                    }

                    return arr;
                })
                .collect(Collectors.toList())
                .toArray(new double[][]{});

        lineLength = 0;

        return resMatrix;
    }

    private static double[] parseArray(String[] array) {

        double[] res = new double[array.length];

        for (int i = 0; i < array.length; i++) {
            res[i] = Double.parseDouble(array[i].replace(',', '.'));
        }

        return res;

    }

}
