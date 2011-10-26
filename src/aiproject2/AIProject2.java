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
public class AIProject2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        RandomVariable B = new RandomVariable("B", 2);
        RandomVariable E = new RandomVariable("E", 2);
        RandomVariable A = new RandomVariable("A", 2);
        RandomVariable J = new RandomVariable("J", 2);
        RandomVariable M = new RandomVariable("M", 2);
        
        Factor f1 = new Factor(
                new RandomVariable[]{B},
                new Double[]{
                   .001, .999 
                });
        Factor f2 = new Factor(
                new RandomVariable[]{E},
                new Double[]{
                    .002, .998
                });
        Factor f3 = new Factor(
                new RandomVariable[]{A, B, E},
                new Double[]{
                    .95, .05,
                    .94, .06,
                    .29, .71,
                    .001, .999
                });
        Factor f4 = new Factor(
                new RandomVariable[]{A},
                new Double[]{
                    .9, .1,
                    .05, .95
                });
        Factor j = new Factor(
                new RandomVariable[]{J},
                new Double[]{
                    0.0, 1.0
                });
        Factor f5 = new Factor(
                new RandomVariable[]{A},
                new Double[]{
                    .7, .3, 
                    .01, .99
                });
        Factor m = new Factor(
                new RandomVariable[]{M},
                new Double[] {
                    0.0, 1.0
                });
        
        RandomVariable[] nuisance = new RandomVariable[]{E, A};
        Factor[] factors = new Factor[]{f1, f2, f3, f4, f5, j, m};
        
        Factor result = variableElimination(factors, nuisance);
        
        System.out.println("Hello, World!");
    }
    
    public static RandomVariable[] variableSort(RandomVariable[] nuisance) {
        return nuisance.clone();
    }
    
    public static Factor variableElimination(Factor[] factors, RandomVariable[] nuisance) {
        List<Factor> factorList = new ArrayList<Factor>(Arrays.asList(factors));
        RandomVariable[] ordered = variableSort(nuisance);
        
        //Add
        for(int i = nuisance.length-1; i >= 0; i--) {
            Factor currentProduct = null;
            System.out.println("Multiplying factors containing: " + nuisance[i].name);
            List<Factor> nextList = new ArrayList<Factor>(factorList);
            for(Factor factor : factorList) {
                if(Arrays.asList(factor.variables).contains(nuisance[i])) {
                    System.out.print("Factor " + Arrays.asList(factors).indexOf(factor));
                    System.out.println(" is being multiplied");
                    if(currentProduct == null) {
                        currentProduct = factor;
                    }
                    else currentProduct = currentProduct.pointwiseMultiplyBy(factor);
                    
                    nextList.remove(factor);
                }
                factorList = nextList;
            }
            
            System.out.println("Summing out nuisance variable: " + nuisance[i].name);
            Factor marginalized = currentProduct.marginalize(nuisance[i]);
            factorList.add(marginalized);
        }
        
        return factorList.get(factorList.size()-1);
    }
}
