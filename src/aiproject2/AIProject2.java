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
    
    public static class MinWeightComparator implements Comparator<RandomVariable> {

        @Override
        public int compare(RandomVariable o1, RandomVariable o2) {
            int o1weight = 1;
            int o2weight = 1;
            
            for(RandomVariable child : o1.children) {
                o1weight *= child.numStates;
            }
            for(RandomVariable parent : o1.parents) {
                o1weight *= parent.numStates;
            }
            for(RandomVariable child : o2.children) {
                o2weight *= child.numStates;
            }
            for(RandomVariable parent : o2.children) {
                o2weight *= parent.numStates;
            }
            
            if(o1weight < o2weight) return -1;
            if(o1weight == o2weight) return 0;
            if(o1weight > o2weight) return 1;
            return 0;
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
        RandomVariable V = new RandomVariable("V", 2 , new RandomVariable[]{});
        RandomVariable S = new RandomVariable("S", 4, new RandomVariable[]{});
        RandomVariable T = new RandomVariable("T", 2, new RandomVariable[]{V});
        RandomVariable L = new RandomVariable("L", 2, new RandomVariable[]{S});
        RandomVariable A = new RandomVariable("A", 2, new RandomVariable[]{T, L});
        RandomVariable B = new RandomVariable("B", 4, new RandomVariable[]{S});
        RandomVariable X = new RandomVariable("X", 2, new RandomVariable[]{A});
        RandomVariable D = new RandomVariable("D", 2, new RandomVariable[]{B, A});
        
        Factor f1 = new Factor(
                new RandomVariable[]{V},
                new Double[]{
                    0.2, 0.8
                });
        Factor f2 = new Factor(
                new RandomVariable[]{S},
                new Double[]{
                    0.25, 0.2, 0.1, 0.45
                });
        Factor f3 = new Factor(
                new RandomVariable[]{V, T},
                new Double[]{
                    0.40, 0.60,
                    0.35, 0.65
                });
        Factor f4 = new Factor(
                new RandomVariable[]{S, L},
                new Double[]{
                    0.40, 0.60,
                    0.60, 0.40,
                    0.30, 0.70,
                    0.80, 0.20
                });
        
        Factor f5 = new Factor(
                new RandomVariable[]{T, L, A},
                new Double[] {
                   0.15, 0.85,
                   0.25, 0.75,
                   0.65, 0.35,
                   0.80, 0.20
                });
        Factor f6 = new Factor(
                new RandomVariable[]{S, B},
                new Double[] {
                   0.625, 0.375, 0.000, 0.000,
                   0.600, 0.000, 0.000, 0.400,
                   0.250, 0.050, 0.500, 0.200,
                   0.350, 0.150, 0.450, 0.050
                });
        
        Factor f7 = new Factor(
                new RandomVariable[]{A, X},
                new Double[]{
                    0.3, 0.7,
                    0.9, 0.1
                });
        Factor f8 = new Factor(
                new RandomVariable[]{B, A, D} ,
                new Double[] {
                   0.3, 0.7,
                   0.6, 0.4,
                   0.75, 0.25,
                   0.35, 0.65,
                   0.25, 0.75,
                   0.8, 0.2,
                   0.7, 0.3,
                   0.3, 0.7
                });
        
        RandomVariable[] vars1 = new RandomVariable[]{S, T, L, A, B, X, D};
        RandomVariable[] vars2 = new RandomVariable[]{V, S, T, L, A, B, X};
        RandomVariable[] vars3 = new RandomVariable[]{V, S, T, L, A, B, D};
        RandomVariable[] order1 = vars1.clone();
        RandomVariable[] order2 = vars2.clone();
        RandomVariable[] order3 = vars3.clone();
        
        Factor eX0 = new Factor(
                new RandomVariable[]{X},
                new Double[] {
                   1.0, 0.0 
                });
        Factor eD1 = new Factor(
                new RandomVariable[]{D},
                new Double[] {
                    0.0, 1.0
                });
        Factor eV0 = new Factor(
                new RandomVariable[]{V},
                new Double[] {
                    1.0, 0.0
                });
        Factor eS1 = new Factor(
                new RandomVariable[]{S},
                new Double[] {
                    0.0, 1.0, 0.0, 0.0
                });
        Factor eV1 = new Factor(
                new RandomVariable[]{V},
                new Double[]{
                    0.0, 1.0
                });
                
                
        Factor[] factors1 = new Factor[]{f1, f2, f3, f4, f5, f6, f7, f8, eX0, eD1};
        Factor[] factors2 = new Factor[]{f1, f2, f3, f4, f5, f6, f7, f8, eV0, eS1};
        Factor[] factors3 = new Factor[]{f1, f2, f3, f4, f5, f6, f7, f8, eV1};
        
        System.out.println("Inference task 1- Min-Neighbors Heuristic");
        Factor result1 = variableElimination(factors1, vars1, new MinNeighborsComparator()).normalize();
        Arrays.sort(order1, new MinNeighborsComparator());
        System.out.print("Variable ordering: ");
        for(RandomVariable rv : order1) {
            System.out.print(rv.name + " ");
        }
        System.out.println("");
        System.out.println("Number of multiply operations performed: " + result1.multiplies);
        System.out.print("{");
        for(double d : result1.distribution) {
            System.out.print(d + " ");
        }
        System.out.println("}\n");
        
        System.out.println("Inference task 2- Min-Neighbors Heuristic");
        Factor result2 = variableElimination(factors2, vars2, new MinNeighborsComparator()).normalize();
        Arrays.sort(order2, new MinNeighborsComparator());
        System.out.print("Variable ordering: ");
        for(RandomVariable rv : order2) {
            System.out.print(rv.name + " ");
        }
        System.out.println("");
        System.out.println("Number of multiply operations performed: " + result2.multiplies);
        System.out.print("{");
        for(double d : result2.distribution) {
            System.out.print(d + " ");
        }
        System.out.println("}\n");
        
        System.out.println("Inference task 3- Min-Neighbors Heuristic");
        Factor order3 = variableElimination(factors3, vars3, new MinNeighborsComparator()).normalize();
        Arrays.sort(order3, new MinNeighborsComparator());
        System.out.print("Variable ordering: ");
        for(RandomVariable rv : vars3) {
            System.out.print(rv.name + " ");
        }
        System.out.println("");
        System.out.println("Number of multiply operations performed: " + result3.multiplies);
        System.out.print("{");
        for(double d : result3.distribution) {
            System.out.print(d + " ");
        }
        System.out.println("}\n");
        
        RandomVariable[] order1 = vars1.clone();
        RandomVariable[] order2 = vars2.clone();
        RandomVariable[] order3 = vars3.clone();
        
        System.out.println("Inference task 1- Min-Weight Heuristic");
        Factor result4 = variableElimination(factors1, vars1, new MinWeightComparator()).normalize();
        Arrays.sort(order1, new MinWeightComparator());
        System.out.print("Variable ordering: ");
        for(RandomVariable rv : order1) {
            System.out.print(rv.name + " ");
        }
        System.out.println("");
        System.out.println("Number of multiply operations performed: " + result1.multiplies);
        System.out.print("{");
        for(double d : result1.distribution) {
            System.out.print(d + " ");
        }
        System.out.println("}\n");
        
        System.out.println("Inference task 2- Min-Weight Heuristic");
        Factor result5 = variableElimination(factors2, vars2, new MinWeightComparator()).normalize();
        Arrays.sort(order2, new MinWeightComparator());
        System.out.print("Variable ordering: ");
        for(RandomVariable rv : order2) {
            System.out.print(rv.name + " ");
        }
        System.out.println("Number of multiply operations performed: " + result2.multiplies);
        System.out.print("{");
        for(double d : result2.distribution) {
            System.out.print(d + " ");
        }
        System.out.println("}\n");
        
        System.out.println("Inference task 3- Min-Weight Heuristic");
        Factor result6 = variableElimination(factors3, vars3, new MinWeightComparator()).normalize();
        Arrays.sort(order3, new MinWeightComparator());
        System.out.print("Variable ordering: ");
        for(RandomVariable rv : order3) {
            System.out.print(rv.name + " ");
        }
        System.out.println("Number of multiply operations performed: " + result3.multiplies);
        System.out.print("{");
        for(double d : result3.distribution) {
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
            
            if(ordered.length == 0) {
                Factor toReturn = factorList.get(0).pointwiseMultiplyBy(factorList.get(1));
                return toReturn;
            }
            RandomVariable sumOver = ordered[0];
            ordered = Arrays.asList(ordered).subList(1, ordered.length).toArray(new RandomVariable[0]);
            
            List<Factor> variablesToMultiply = new ArrayList<Factor>();
            
            for(Factor factor : factorList) {
                if(Arrays.asList(factor.variables).contains(sumOver)) {
                    variablesToMultiply.add(factor);
                }
            }
            
            Factor product = null;
            
            for(Factor factor : variablesToMultiply) {
                if(product == null) product = factor;
                else product = product.pointwiseMultiplyBy(factor);
                factorList.remove(factor);
            }
            Factor marginalized = product.marginalize(sumOver);      
            factorList.add(marginalized);
        }
        
        return factorList.get(0);
    }
}
