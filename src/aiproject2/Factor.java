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
    RandomVariable dependent;
    RandomVariable[] dependencies;
    Float[][] distribution;
    
    public Factor(RandomVariable[] vars, Float[] distribution) {
        
    }
    
    public Factor pointwiseMultiplyBy(Factor other) {
        return null;
    }
    
    public Factor sumOver(RandomVariable rv) {
        return null;
    }
}
