package frc.robot.lib;

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
        this.funcList = funcs;
        this.numFuncs = funcs.length;
        this.numSplits = split.length;
    }

    public double ramp(double input) {
        if(numFuncs == 0 || numSplits == 0) {
            return input;
        }

        if(input <= splitPoints[0]) {
            return funcList[0].applyAsDouble(input);
        }

        if(input > splitPoints[numSplits-1]) {
            return  funcList[numFuncs-1].applyAsDouble(input);
        }

        for(int i=0; i<numSplits-1; i++) {
            if(input > splitPoints[i] && input <= splitPoints[i+1]) {
                return funcList[i+1].applyAsDouble(input);
            }
        }

        return 0;
    }
}