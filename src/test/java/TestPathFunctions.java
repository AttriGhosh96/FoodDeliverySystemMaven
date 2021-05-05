import com.xav.GeneratePopulation;
import com.xav.mapQuest.routeMatrix.PostRequestCheck;
import com.xav.pojo.Path;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TestPathFunctions {
    public static void main(String[] args) throws IOException {

        GeneratePopulation generatePopulation = GeneratePopulation.getGeneratePopulationInstance();

        Set<Path> initialPopulation = generatePopulation.createInitialPopulation();

        List<Path> initialPopulationList = initialPopulation.stream().collect(Collectors.toList());

        //checked for candidate
        for(int i=0; i<10; i++) {
            Path candidate = generatePopulation.createCandidate(generatePopulation.selectParents(initialPopulationList));
            System.out.println("Candidate Path: " + candidate);
        }
//        Path checkPath = initialPopulationList.get(0);
//
//        double totalOrderValue = checkPath.getTotalOrderValue();
//        double totalDistance = checkPath.getTotalDistance();
//        double totalPathTime = checkPath.getTotalTime();
//
//        System.out.println("Path: " + checkPath);
//        System.out.println("Total Order Value: Rs " + totalOrderValue);
//        System.out.println("Total Distance: " + totalDistance + " kms");
//        System.out.println("Total Time: " + totalPathTime / 60 + " minutes");


//          System.out.println("Candidate Path: " +candidate);
//        System.out.println("Total Order Value: Rs " +candidate.getTotalOrderValue());
//        System.out.println("Total Distance: " +candidate.getTotalDistance() + " kms");
//        System.out.println("Total Time: " +candidate.getTotalTime()/60 + " minutes");

//        System.out.println("final ans = ");

//        for (int i = -1; i <= 1; i++) {
//
//            for (int j = -1; j <= 1; j++) {
//
//                for (int k = -1; k <= 1; k++) {
//
//                    System.out.println("{\t"+ i +"\t"+ j +"\t"+ k +"\t} = \t" + checkPath.dominates(new int[]{i, j, k}));
//
//                }
//            }
//        }
//        int dominates = checkPath.dominates(checkPath);
    }
}