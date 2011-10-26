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
                    .9,
                    .05
                });
        Factor j = new Factor(
                new RandomVariable[]{J},
                new Double[]{
                    0.0, 1.0
                });
        Factor f5 = new Factor(
                new RandomVariable[]{A},
                new Double[]{
                    .7,
                    .01
                });
        Factor m = new Factor(
                new RandomVariable[]{M},
                new Double[] {
                    0.0, 1.0
                });
        
        RandomVariable[] nuisance = new RandomVariable[]{E, A};
        Factor[] factors = new Factor[]{f1, f2, f3, f4, f5};
        
        Factor result = variableElimination(factors, nuisance);
        
        System.out.println("Hello, World!");
    }
    
    public static RandomVariable[] variableSort(RandomVariable[] nuisance) {
        return nuisance.clone();
    }
    
    public static Factor variableElimination(Factor[] factors, RandomVariable[] nuisance) {
        List<Factor> factorList = new ArrayList<Factor>(Arrays.asList(factors));
        RandomVariable[] ordered = variableSort(nuisance);
        
        while(factorList.size() > 1) {
            System.out.println("Number of factors: " + factorList.size());
            
            if(ordered.length == 0) {
                Factor toReturn = factorList.get(0).pointwiseMultiplyBy(factorList.get(1));
                return toReturn;
            }
            RandomVariable sumOver = ordered[ordered.length - 1];
            ordered = Arrays.asList(ordered).subList(0, ordered.length - 1).toArray(new RandomVariable[0]);
            
            List<Factor> variablesToMultiply = new ArrayList<Factor>();
            
            System.out.print("Multiplying factors {");
            for(Factor factor : factorList) {
                if(Arrays.asList(factor.variables).contains(sumOver)) {
                    System.out.print(factorList.indexOf(factor) + " ");
                    variablesToMultiply.add(factor);
                }
            }
            System.out.println("}");
            
            Factor product = null;
            
            for(Factor factor : variablesToMultiply) {
                if(product == null) product = factor;
                else product = product.pointwiseMultiplyBy(factor);
                factorList.remove(factor);
            }
            System.out.println("Marginalizing over " + sumOver.name);
            Factor marginalized = product.marginalize(sumOver);      
            factorList.add(marginalized);
        }
        
        return factorList.get(0);
    }
}
