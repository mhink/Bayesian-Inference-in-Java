/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aiproject2;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

/**
 *
 * @author Hink
 */
public class Factor {
    RandomVariable[] variables;
    Double[] distribution;
    
    public Factor(RandomVariable[] vars, Double[] distribution) {
        this.variables = vars;
        this.distribution = distribution;
    }
    
    public Factor pointwiseMultiplyBy(Factor other) {
        List<Double> resultant = new ArrayList<Double>();
        List<RandomVariable> common = new ArrayList<RandomVariable>();
        List<RandomVariable> join = new ArrayList<RandomVariable>();
        
        for(RandomVariable rv : variables) {
            if(Arrays.asList(other.variables).indexOf(rv) != -1) {
                common.add(rv);
            }
            join.add(rv);
        }
        
        for(RandomVariable rv : other.variables) {
            int idx = join.indexOf(rv);
            if(idx == -1) {
                join.add(rv);
            }
        }
        
        for(int i = 0; i < distribution.length; i++) {
            List<Integer> indices = new ArrayList<Integer>();
            
            for(int k = 0; k < other.distribution.length; k++) {
                indices.add(k);
            }
            
            for(RandomVariable rv : common) {
                int whichState = whichState(rv, this.variables, i);
                indices = Arrays.asList(stateMatch(
                        rv, 
                        other.variables, 
                        whichState, 
                        indices.toArray(new Integer[0])));
            }
            for(Integer j : indices) {
                resultant.add(this.distribution[i] * other.distribution[j]);
            }
        }
        
        return new Factor(join.toArray(new RandomVariable[0]), resultant.toArray(new Double[0]));
    }
    
    private int whichState(RandomVariable rv, RandomVariable[] rvs, int index) {
        
        int skip = 1;
        int totalStates = 1;
        for(int i = rvs.length-1; i >= 0; i--) {
            totalStates *= rvs[i].numStates;
            if(i > Arrays.asList(rvs).indexOf(rv)) 
                skip *= rvs[i].numStates;
        }
        
        return ((index / skip) % rv.numStates);
    }
    
    private Integer[] stateMatch(RandomVariable rv, RandomVariable[] rvs, int state, Integer[] indices) {
        List<Integer> extracted = new ArrayList<Integer>();
        
        int skip = 1;
        int totalStates = 1;
        for(int i = rvs.length-1; i >= 0; i--) {
            totalStates *= rvs[i].numStates;
            if(i > Arrays.asList(rvs).indexOf(rv)) 
                skip *= rvs[i].numStates;
        }
        
        for(Integer index : indices) {
            if(((index / skip) % rv.numStates) == state) 
                extracted.add(index);
        }
        
        return extracted.toArray(new Integer[0]);
    }
    
    public Factor marginalize(RandomVariable rv) {
        
        Double[][] marginalDistribution = new Double[rv.numStates][0];
        int skip = 1;
        for(int i = variables.length-1; i > Arrays.asList(variables).indexOf(rv); i--) {
            skip *= variables[i].numStates;
        }
        
        for(int i = 0; i < distribution.length; i++) {
            int j = (i / skip) % (rv.numStates);
            
            //cheap hack, but I can't work it out any further.
            ArrayList<Double> array = new ArrayList<Double>(Arrays.asList(marginalDistribution[j]));
            array.add(distribution[i]);
            marginalDistribution[j] = array.toArray(new Double[0]).clone();
        }
        
        Double[] marginalizedArray = marginalDistribution[0].clone();
        
        for(int i = 1; i < marginalDistribution.length; i++) {
            for(int j = 0; j < marginalizedArray.length; j++) {
                marginalizedArray[j] += marginalDistribution[i][j];
            }
        }
        ArrayList<RandomVariable> newVars = new ArrayList<RandomVariable>(Arrays.asList(this.variables));
        newVars.remove(rv);
        
        return new Factor(newVars.toArray(new RandomVariable[0]), marginalizedArray);        
    }
    
    public Factor normalize() {
        double norm = 0d;
        Double[] result = new Double[distribution.length];
        for(double d : distribution) {
            norm += d;
        }
        
        for(int i = 0; i < distribution.length; i++) {
            result[i] = distribution[i] / norm;
        }
        
        return new Factor(variables, result);
    }
}
