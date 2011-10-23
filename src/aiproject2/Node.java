/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aiproject2;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.Vector;
import aiproject2.RandomVariable.State;
import java.util.Arrays;

/**
 *
 * @author Hink
 */
public class Node {
    RandomVariable associatedVariable;
    RandomVariable[] dependencies;
    Float[][] conditionalDistribution;
    
    public Node(RandomVariable dependent, 
                RandomVariable[] dependencies, 
                Float[][] distributions) {
        this.associatedVariable = dependent;
        if(dependencies.length == 0) {
            this.dependencies = new RandomVariable[] {new RandomVariable("TRUE", new String[]{"TRUE"})};
        } else {
            this.dependencies = dependencies.clone();
        }
        this.conditionalDistribution = distributions.clone();
    }
    
    public Float[] getDistributionOverStates(State[] states) {
        return getSubDistribution(states, conditionalDistribution)[0];
    }
    
    public Float[][] getSubDistribution(State[] states, Float[][] distribution) {
        int index = conditionalDistribution.length;
            
            if(states.length == 0) return distribution;
            
            int stateNumber = states[0].getVariable().getStates().indexOf(states[0]);
            int totalStates = states[0].getVariable().getStates().size();
            
            if(stateNumber == -1) return distribution;
            
            return getSubDistribution(
                    Arrays.asList(states).subList(1, states.length)
                        .toArray(new State[0]), 
                    Arrays.asList(distribution).subList(
                        (distribution.length / totalStates) * stateNumber, 
                        (distribution.length / totalStates) * (stateNumber + 1))
                        .toArray(new Float[0][0])
                    );
    }
}
