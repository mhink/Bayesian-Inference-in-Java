/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aiproject2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author Hink
 */
public class RandomVariable {
  
    public int numStates;
    public String name;
    public List<RandomVariable> parents;
    public List<RandomVariable> children;
    
    public RandomVariable(String name, int numStates, RandomVariable[] parents) {
        this.name = name;
        this.numStates = numStates;
        this.parents = new ArrayList<RandomVariable>(Arrays.asList(parents));
        this.children = new ArrayList();
        if(parents.length > 0)
            for(RandomVariable rv : parents) {
               rv.children.add(this);
            }
    }
    
    @Override
    public boolean equals(Object other) {
        if(this == other) return true;
        if(!(other instanceof RandomVariable)) return false;
        
        if(this.name.equals(((RandomVariable)other).name)) return true;
        else return false;
    }
}
