package frc.robot.lib;

import java.util.function.DoubleUnaryOperator;

public class RampingController {
    private final DoubleUnaryOperator[] funcList;
    private final int numFuncs;
    private final double[] splitPoints;
    

    /**
     * Controller that ramps an input according to a set of functions and split points
     * MUST have one less split point than function
     * @param nFuncs number of functions
     * @param split array with split points not including 0
     * @param funcs functions for respective split point
     */
    public RampingController(int nFuncs, double[] split, DoubleUnaryOperator... funcs) {
        this.numFuncs = nFuncs;
        this.splitPoints = split;
        this.funcList = funcs;
    }

    public double ramp(double input) {
        double output = 0;

        for(int i=0; i<numFuncs; i++) {
            if(i == 0 && input <= splitPoints[0]) {
                output = funcList[0].applyAsDouble(input);
                break;
            }
            if(input > splitPoints[i-1] && input >= splitPoints[i]) {
                output = funcList[i].applyAsDouble(input);
                break;
            }
        }

        return output;
    }
}