package ru.sstu.matrixCalc.exception;

public class IncorrectMatrixException extends RuntimeException {

    public IncorrectMatrixException() {
        super("The entered matrix is incorrect!");
    }

    public IncorrectMatrixException(String message) {
        super(message);
    }
}
