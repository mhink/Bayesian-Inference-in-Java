/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aiproject2;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import aiproject2.RandomVariable.State;

/**
 *
 * @author Hink
 */
public class CDEntry {
    
    ArrayList<State> K;
    Distribution V;
    
    public CDEntry(ArrayList<State> K, Distribution V) {
        this.K = K;
        this.V = V;
    }
    
    public ArrayList<State> getCombination() {
        return this.K;
    }
    public Distribution getDistribution() {
        return this.V;
    }
    public void setCombination(ArrayList<State> K) {
        this.K = K;
    }
    public void setDistribution(Distribution V) {
        this.V = V;
    }
}
