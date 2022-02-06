package com.company;
import java.util.*;
import java.util.Collections;
// error code 1: invalid input
// error code 2: vector of size 0
// TO-DO: IQR and outliers (create vector of outliers): DONE
// min, q1, q3, max: DONE
// sample std deviation + variance: DONE
// population std deviation + variance: DONE
// formulae:
// pop std formula = sqrt(sum (mean - data point) / N)
// sample std formula = sqrt(sum (mean - data point) / n-1)
// iqr = q3-q1
// outlier if 1.5(q3-q1) < dist from median

public class Main {
    // returns the min, q1, median, q3, and max of a sorted vector
    static double BoxAndWhisker(Vector<Double> vec, String desiredOutput) {
        double output = 0.0;
        int size = vec.size();
        double min = 0.0;
        double q1 = 0.0;
        double median = 0.0;
        double q3 = 0.0;
        double max = 0.0;
        double index1 = 0.0;
        double index2 = 0.0;
        // min
        if (desiredOutput.toLowerCase().equals("min")) {
            min = vec.elementAt(0);
            output = min;
        }
        // q1
        if (desiredOutput.toLowerCase().equals("q1")) {
            if (vec.size() % 2 == 0) {
                q1 = vec.elementAt(((size / 4) + (size / 4 - 1)) / 2);
            }
            else q1 = vec.elementAt(size/4);
            output = q1;
        }
        // median
        if (desiredOutput.toLowerCase().equals("median")) {
            if (vec.size() % 2 == 0) {
                index1 = vec.elementAt(size / 2 - 1);
                index2 = vec.elementAt(size / 2);
                median = (index1 + index2) / 2;
            }
            else median = vec.elementAt(vec.size() / 2);
            output = median;
        }
        // q3
        if (desiredOutput.toLowerCase().equals("q3")) {
            if (vec.size() % 2 == 0) {
                q3 = vec.elementAt(((3 * size / 4) + (3 * size / 4 - 1)) / 2);
            }
            else q3 = vec.elementAt(3 * size / 4);
            output = q3;
        }
        // max
        if (desiredOutput.toLowerCase().equals("max")) {
            max = vec.elementAt(size - 1);
            output = max;
        }
        return output;
    }
    // return a vector of outliers
    static Vector<Double> Outliers(Vector<Double> vec, double q1, double q3, double iqr) {
        Vector<Double> outs = new Vector<>();
        for (double a : vec) {
            if (a < (q1 - (1.5 * iqr))) outs.add(a);
            if (a > (q3 + (1.5 * iqr))) outs.add(a);
        }
        return outs;
    }
    // returns population variance
    static double PopVariance(Vector<Double> vec, double mean, int popSize) {
        double popVar = 0.0;
        double diffSqr = 0.0;
        for (int i = 0; i < vec.size(); i++) {
            diffSqr += Math.pow((mean - vec.elementAt(i)), 2);
        }
        popVar = diffSqr / popSize;
        return popVar;
    }
    // returns sample variance
    static double SamVariance(Vector<Double> vec, double mean, int samSize) {
        double samVar = 0.0;
        double diffSqr = 0.0;
        for (int i = 0; i < vec.size(); i++) {
            diffSqr += Math.pow((mean - vec.elementAt(i)), 2);
        }
        samVar = diffSqr / (samSize - 1);
        return samVar;
    }
    // main
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("welcome to a java statistics calculator!");
        System.out.println("please enter numerical values (done to quit): ");
        String s;
        Vector<Double> data = new Vector<>();
        int dataSetSize = 0;
        double x = 0;
        double sum = 0.0;
        double average = 0.0;
        double min = 0.0;
        double max = 0.0;
        double q1 = 0.0;
        double q3 = 0.0;
        double median = 0.0;
        double iqr = 0.0;
        double popVar = 0.0;
        double samVar = 0.0;
        double popSD = 0.0;
        double samSD = 0.0;
        // read in a user input (string)
        while (true) {
            s = input.nextLine();
            // if user input = "done" break;
            if (s.toLowerCase().equals("done")) break;
                // else convert string to double and return "invalid input" if not double
            else {
                try {
                    x = Double.parseDouble(s);
                }
                catch (Exception e) {
                    System.out.println("invalid input (value must be numerical)");
                    continue;
                }
            }
            data.add(x);
            sum += x;
        }
        Collections.sort(data);
        // sorted data output (visual)
        System.out.println("sorted data: ");
        System.out.print("{");
        for (int i = 0; i < data.size() - 1; i++) {
            System.out.print(data.elementAt(i) + ",");
        }
        System.out.println(data.elementAt(data.size() - 1) + "}");

        // data calculations
        average = Math.round(sum / data.size() * 100) / 100.0;
        min = Math.round(BoxAndWhisker(data, "min") * 100) / 100.0;
        q1 = BoxAndWhisker(data, "q1");
        median = Math.round(BoxAndWhisker(data, "median") * 100) / 100.0;
        q3 = BoxAndWhisker(data, "q3");
        max = Math.round(BoxAndWhisker(data, "max") * 100) / 100.0;
        iqr = q3-q1;
        dataSetSize = data.size();
        popVar = PopVariance(data, average, dataSetSize);
        samVar = SamVariance(data, average, dataSetSize);
        popSD = Math.sqrt(popVar);
        samSD = Math.sqrt(samVar);
        Vector<Double> outliers = Outliers(data, q1, q3, iqr);

        // data output
        System.out.println("size of data set: " + dataSetSize);
        System.out.println("min/max: " + min + ", " + max);
        System.out.println("data set mean: " + average);
        System.out.println("data set median: " + median);
        if (outliers.size() != 0) {
            System.out.print("outliers: ");
            System.out.print("{");
            for (int i = 0; i < outliers.size() - 1; i++) {
                System.out.print(outliers.elementAt(i) + ",");
            }
            System.out.println(outliers.elementAt(outliers.size() - 1) + "}");
        }
        else System.out.println("outliers: none");
        System.out.println("σ: " + Math.round(popSD * 100) / 100.0);
        System.out.println("σ²: " + Math.round(popVar * 100) / 100.0);
        //System.out.println("pop variance (σ²): " + Math.round(popVar * 100) / 100.0);
        System.out.println("s: " + Math.round(samSD * 100) / 100.0);
        System.out.println("s²: " + Math.round(samVar * 100) / 100.0);
        //System.out.println("sample variance (s²): " + Math.round(samVar * 100) / 100.0);
    }
}
