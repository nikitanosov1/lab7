package functions;

import java.io.*;

public abstract class TabulatedFunctions {
    public static TabulatedFunction tabulate(Function function, double leftX, double rightX, int pointsCount) throws IllegalArgumentException {
        if ((function.getLeftDomainBorder() > leftX) || (function.getRightDomainBorder() < rightX)) {
            throw new IllegalArgumentException();
        }
        ArrayTabulatedFunction arrayTabulatedFunction = new ArrayTabulatedFunction(leftX, rightX, pointsCount);
        for (int i = 0; i < pointsCount; i++) {
            arrayTabulatedFunction.setPointY(i, function.getFunctionValue(arrayTabulatedFunction.getPointX(i)));
        }
        return arrayTabulatedFunction;
    }

    public static void outputTabulatedFunction(TabulatedFunction function, OutputStream out) {
        try (DataOutputStream dataOutputStream = new DataOutputStream(out)){
            dataOutputStream.writeInt(function.getPointsCount());
            for (int i = 0; i < function.getPointsCount(); i++) {
                dataOutputStream.writeDouble(function.getPointX(i));
                dataOutputStream.writeDouble(function.getPointY(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static TabulatedFunction inputTabulatedFunction(InputStream in){
        ArrayTabulatedFunction arrayTabulatedFunction = new ArrayTabulatedFunction();
        try (DataInputStream dataInputStream = new DataInputStream(in)){
            int countPoints = dataInputStream.readInt();
            for (int i = 0; i < countPoints; i++) {
                FunctionPoint functionPoint = new FunctionPoint();
                functionPoint.setX(dataInputStream.readDouble());
                functionPoint.setY(dataInputStream.readDouble());
                arrayTabulatedFunction.addPoint(functionPoint);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InappropriateFunctionPointException e) {
            e.printStackTrace();
        }
        return arrayTabulatedFunction;
    }

    public static void writeTabulatedFunction(TabulatedFunction function, Writer out){
        PrintWriter printWriter = new PrintWriter(out);
        printWriter.print(function.getPointsCount());
        printWriter.print(" ");
        for (int i = 0; i < function.getPointsCount(); i++) {
            printWriter.print(function.getPointX(i));
            printWriter.print(" ");
            printWriter.print(function.getPointY(i));
            printWriter.print(" ");
        }
        printWriter.close();
    }

    public static TabulatedFunction readTabulatedFunction(Reader in){
        ArrayTabulatedFunction arrayTabulatedFunction = new ArrayTabulatedFunction();
        StreamTokenizer streamTokenizer = new StreamTokenizer(in);
        try {
            streamTokenizer.nextToken();
            int countsPoints = (int) streamTokenizer.nval;
            double x;
            double y;
            for (int i = 0; i < countsPoints; i++) {
                FunctionPoint functionPoint = new FunctionPoint();
                streamTokenizer.nextToken();
                x = streamTokenizer.nval;
                streamTokenizer.nextToken();
                y = streamTokenizer.nval;
                arrayTabulatedFunction.addPoint(new FunctionPoint(x, y));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InappropriateFunctionPointException e) {
            e.printStackTrace();
        }
        return arrayTabulatedFunction;
    }
}
