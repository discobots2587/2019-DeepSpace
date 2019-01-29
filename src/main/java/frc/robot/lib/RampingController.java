package frc.robot.lib;

import java.util.Arrays;
import java.util.function.DoubleUnaryOperator;

public class RampingController {
    private final DoubleUnaryOperator[] funcList;
    private final double[] splitPoints;
    private final int numFuncs;
    private final int numSplits;


    /**
     * Controller that ramps an input according to a set of functions and split points
     * MUST have one less split point than function
     * @param split array with split points not including 0
     * @param funcs functions for respective split point
     */
    public RampingController(double[] split, DoubleUnaryOperator... funcs) {
        this.splitPoints = split;
        Arrays.sort(splitPoints);
        this.funcList = funcs;
        this.numFuncs = funcs.length;
        this.numSplits = split.length;
    }

    public double ramp(double input) {
        DoubleUnaryOperator outputFunc = x -> 0;

        if(numFuncs == 0 || numSplits == 0) {
            outputFunc = x -> x;
        }

        if(input <= splitPoints[0]) {
            outputFunc = funcList[0];
        }

        if(input > splitPoints[numSplits-1]) {
            outputFunc = funcList[numFuncs-1];
        }

        for(int i=0; i<numSplits-1; i++) {
            if(input > splitPoints[i] && input <= splitPoints[i+1]) {
                try {
                    outputFunc = funcList[i + 1];
                } catch(ArrayIndexOutOfBoundsException e) {
                    outputFunc = funcList[numFuncs-1];
                }
            }
        }

        return outputFunc.applyAsDouble(input);
    }
}