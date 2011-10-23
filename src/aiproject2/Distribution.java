/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aiproject2;

import java.util.Arrays;
import java.util.Vector;

/**
 *
 * @author Hink
 */
public class Distribution {
    private Vector<RandomVariable> variables;
    private Vector<Float> probabilities;
    
    //Generates a normal distribution over initialVariable
    public Distribution(RandomVariable initialVariable) {
        variables = new Vector<RandomVariable>();
        probabilities = new Vector<Float>();
        
        int numStates = initialVariable.numStates();
        float uniformValue = 1f / numStates;
        
        variables.add(initialVariable);
        for(int i = 0; i < numStates; i++) {
            probabilities.add(uniformValue);
        }
    }
    
    //Generates a normalized distribution over initialVariable
    public Distribution(RandomVariable initialVariable,
                        Float[] values) {
        variables = new Vector<RandomVariable>();
        probabilities = new Vector<Float>();
        
        variables.add(initialVariable);
        probabilities.addAll(normalize(new Vector(Arrays.asList(values))));
    }
    
    private Vector<Float> normalize(Vector<Float> toNormalize) {
        Vector<Float> toReturn = new Vector<Float>();
        
        float total = 0;
        for(Float f : toNormalize) {
            total += f;
        }
        for(Float f : toNormalize) {
            toReturn.add(f/total);
        }
        
        return null;
    }
}
