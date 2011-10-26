/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aiproject2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author Hink
 */
public class AIProject2 {
    
    public class MinWeightComparator implements Comparator<RandomVariable> {

        @Override
        public int compare(RandomVariable o1, RandomVariable o2) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
    }
    
    public static class MinNeighborsComparator implements Comparator<RandomVariable> {
        @Override
        public int compare(RandomVariable o1, RandomVariable o2) {
            if((o1.children.size()+o1.parents.size()) < (o2.children.size() + o2.parents.size())) return -1;
            if((o1.children.size()+o1.parents.size()) == (o2.children.size() + o2.parents.size())) return 0;
            if((o1.children.size()+o1.parents.size()) > (o2.children.size() + o2.parents.size())) return 1;
            return 0;
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        RandomVariable B = new RandomVariable("B", 2, new RandomVariable[]{});
        RandomVariable E = new RandomVariable("E", 2, new RandomVariable[]{});
        RandomVariable A = new RandomVariable("A", 2, new RandomVariable[]{B, E});
        RandomVariable J = new RandomVariable("J", 2, new RandomVariable[]{A});
        RandomVariable M = new RandomVariable("M", 2, new RandomVariable[]{A});
        
        Factor f1 = new Factor(
                new RandomVariable[]{B},
                new Double[]{
                   .999, .001 
                });
        Factor f2 = new Factor(
                new RandomVariable[]{E},
                new Double[]{
                    .998, .002
                });
        Factor f3 = new Factor(
                new RandomVariable[]{B, E, A},
                new Double[]{
                    .999, .001,
                    .71, .29,
                    .06, .94,
                    .05, .95
                });
        Factor f4 = new Factor(
                new RandomVariable[]{A, J},
                new Double[]{
                    .95, .05,
                    .1, .9
                });
        
        Factor j = new Factor(
                new RandomVariable[]{J},
                new Double[] {
                   0.0,
                   1.0 
                });
        Factor m = new Factor(
                new RandomVariable[]{M},
                new Double[] {
                   0.0,
                   1.0 
                });
        
        Factor f5 = new Factor(
                new RandomVariable[]{A, M},
                new Double[]{
                    .99, .01,
                    .3, .7
                });
        
        RandomVariable[] nuisance = new RandomVariable[]{E, A, J, M};
        Factor[] factors = new Factor[]{f1, f2, f3, f4, f5, j, m};
        
        Factor result = variableElimination(factors, nuisance, new MinNeighborsComparator()).normalize();
        
        System.out.print("{");
        for(double d : result.distribution) {
            System.out.print(d + " ");
        }
        System.out.println("}");
    }
    
    public static void minNeighborsSort(RandomVariable[] nuisance) {
        Arrays.sort(nuisance, new MinNeighborsComparator());
    }
    
    public static Factor variableElimination(Factor[] factors, RandomVariable[] nuisance, Comparator<RandomVariable> comp) {
        List<Factor> factorList = new ArrayList<Factor>(Arrays.asList(factors));
        RandomVariable[] ordered = nuisance.clone();
        Arrays.sort(ordered, comp);
        
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
