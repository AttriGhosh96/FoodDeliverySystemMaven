import com.xav.GeneratePopulation;

public class TestSingletonDesignPattern {

    public static void main(String[] args) {

        GeneratePopulation ob1 = GeneratePopulation.getGeneratePopulationInstance();

        GeneratePopulation ob2 = GeneratePopulation.getGeneratePopulationInstance();

        GeneratePopulation ob3 = GeneratePopulation.getGeneratePopulationInstance();

        System.out.println(ob1.equals(ob2) + "" + ob2.equals(ob3));

    }

}
