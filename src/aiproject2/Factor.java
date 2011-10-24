/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aiproject2;

import java.util.Arrays;

/**
 *
 * @author Hink
 */
public class Factor {
    RandomVariable[] variables;
    Float[][] distribution;
    
    public Factor(RandomVariable[] vars, Float[][] distribution) {
        this.variables = vars;
        this.distribution = distribution;
    }
    
    public Factor pointwiseMultiplyBy(Factor other) {
        return null;
    }
    
    public Factor marginalize(RandomVariable rv) {
        int variablePosition = Arrays.asList(variables).indexOf(rv);
        
        Float[][][] marginalValues = new Float[rv.numStates()][0][0];
        
        for(int i = 0; i < distribution.length; i++) {
            Arrays.asList(marginalValues[i%rv.numStates()]).add(distribution[i]);
        }
        
        Float[][] marginalDistribution = marginalValues[0];
        
        for(int i = 1; i < marginalValues.length; i++) {
            for(int j = 0; j < marginalDistribution.length; j++) {
                for( int k = 0; k < marginalDistribution[0].length; k++) {
                    
                }
            }
        }
        
        
        return null;
    }
    
    public Factor normalize() {
        return this;
    }
}
