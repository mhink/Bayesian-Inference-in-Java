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
    public class State {
        private String name;
        private RandomVariable associatedVariable;
        
        public State(String name, RandomVariable associatedVariable) {
            this.name = name;
            this.associatedVariable = associatedVariable;
        }
        
        public String getName() {
            return this.name;
        }
        public RandomVariable getVariable() {
            return this.associatedVariable;
        }
    }
    
    private Vector<State> states;
    private Vector<Float> values;
    public String name;
    
    public RandomVariable(String varName, String[] stateNames) {
        this.states = new Vector<State>();
        
        this.name = varName;
        for(String s : stateNames) {
            states.add(new State(s, this));
        }
    }
    
    public Vector<State> getStates() {
        return states;
    }
    
    public int numStates() {
        return states.size();
    }
}
