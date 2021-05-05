package com.xav.differentialEvolution;

import com.xav.GeneratePopulation;
import com.xav.pojo.Path;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DifferentialEvolutionPareto {

    public static final int MAX_ITERATION_FOR_NEW_POPULATION_GENERATION = 2000;

    public static final int MAX_CONSECUTIVE_POPULATION_REPETITION_ALLOWED = 50;

    public static void startAlgorithm() throws IOException {
        //singleton object of GeneratePopulation class
        GeneratePopulation generatePopulationInstance = GeneratePopulation.getGeneratePopulationInstance();

        //creating initial population
        Set<Path> initialPopulation = generatePopulationInstance.createInitialPopulation();

        //converting Set<Path> to List<Path>
        List<Path> initialPopulationList = initialPopulation.stream().collect(Collectors.toList());
        List<Path> currentPopulation = initialPopulationList;

        //generating multiple generations
        for(int iterator = 1; iterator<=20; iterator++) {
            //Printing current population
            System.out.println("Current Population\t Generation Number: " + iterator + "\t size: " + currentPopulation.size());
//        displayPopulation(currentPopulation);

            //creation of next population
            List<Path> nextPopulation = new ArrayList<Path>();
            List<Path> selectedParents;
            int counterForPopulationGeneration = 0;
            int counterForConsecutivePopulationRepetition = 0;
            while (nextPopulation.size() <= (0.85 * currentPopulation.size()) && counterForPopulationGeneration < MAX_ITERATION_FOR_NEW_POPULATION_GENERATION) {
                counterForPopulationGeneration++;
                counterForConsecutivePopulationRepetition++;
                //selecting parents for candidate creation
                selectedParents = generatePopulationInstance.selectParents(currentPopulation);
                //creating candidate
                Path candidate = generatePopulationInstance.createCandidate(selectedParents);

                //pareto checking for candidate and parents
                if (candidate != null ) {

                    for (Path parent : selectedParents) {
                        int candidateParentDominanceCheck = candidate.dominates(parent);
                        if ((candidateParentDominanceCheck == 1) && (!nextPopulation.contains(candidate))) //candidate dominates parent
                            nextPopulation.add(candidate);
                        else if (candidateParentDominanceCheck == -1  && (!nextPopulation.contains(parent))) //parent dominates candidate
                            nextPopulation.add(parent);

                    }
                }
            }

            if (nextPopulation.size() != 0 ) {
                currentPopulation = nextPopulation;
                counterForConsecutivePopulationRepetition = 0;
            }
            else if( counterForConsecutivePopulationRepetition > MAX_CONSECUTIVE_POPULATION_REPETITION_ALLOWED )
                throw  new RuntimeException("Same Population being reused more than "+MAX_CONSECUTIVE_POPULATION_REPETITION_ALLOWED+"  times ");
        }
        displayPopulation(currentPopulation);
        displayPaths(currentPopulation);
        //printing next population
//        System.out.println("Next Population\t size: "+nextPopulation.size());
//        displayPopulation(nextPopulation);

    }

    //for displaying populations
    public static void displayPopulation(List<Path> population)
    {
        System.out.println("Population consists of following paths:");
        for (Path eachPath : population)
        {
            System.out.println(eachPath);
        }
    }

    //for displaying paths with properties
    public static void displayPaths(List<Path> Paths) throws IOException {
        int count = 1;
        System.out.println("Path Number\t\t\t Total Distance(in Km)\t\t\t Total Time(in minutes)\t\t\t Total Order Value(in Rs)");
        for(Path path : Paths)
        {
            System.out.println(count+"\t\t\t" +path.getTotalDistance()+ "\t\t\t" +path.getTotalTime()/60+ "\t\t\t"+ path.getTotalOrderValue());
            count ++;
        }

    }



}
