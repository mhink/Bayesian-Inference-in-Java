/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aiproject2;

import java.util.Vector;

/**
 *
 * @author Hink
 */
public class AIProject2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        RandomVariable T = new RandomVariable(
                "T",
                new String[]{"State0", "State1"});
        RandomVariable L = new RandomVariable(
                "L",
                new String[]{"State0", "State1"});
        RandomVariable A = new RandomVariable(
                "A",
                new String[]{"State0", "State1"});
        
        Node nA = new Node(
                A,
                new RandomVariable[]{T, L}, 
                new Float[][]{
                    {0.15f, 0.85f},
                    {0.25f, 0.75f},
                    {0.65f, 0.35f},
                    {0.80f, 0.20f}
                });
        
        RandomVariable.State[] T1L0 = new RandomVariable.State[]{T.getStates().get(1), L.getStates().get(0)};
        
        
        Float[] testConditional = 
                nA.getDistributionOverStates(T1L0);
        
        for(Float f : testConditional) {
            System.out.println(f + ", ");
        }
    }
}
