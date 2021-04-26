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
        Path candidate = generatePopulation.createCandidate(generatePopulation.selectParents(initialPopulationList));

        Path checkPath = initialPopulationList.get(0);

//        double totalOrderValue = checkPath.getTotalOrderValue();
//        double totalDistance = checkPath.getTotalDistance();
//        double totalPathTime = checkPath.getTotalTime();
//
//        System.out.println("Path: " + checkPath);
//        System.out.println("Total Order Value: Rs " +totalOrderValue);
//        System.out.println("Total Distance: " +totalDistance + " kms");
//        System.out.println("Total Time: " +totalPathTime/60 + " minutes");

        System.out.println("Candidate Path: " +candidate);
        System.out.println("Total Order Value: Rs " +candidate.getTotalOrderValue());
        System.out.println("Total Distance: " +candidate.getTotalDistance() + " kms");
        System.out.println("Total Time: " +candidate.getTotalTime()/60 + " minutes");

    }
}
