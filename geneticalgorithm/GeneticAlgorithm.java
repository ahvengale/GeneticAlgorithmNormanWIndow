//This program is a first attempt at a Genetic Algorithm
//It is uses the shape of a Norman Window (Recatangle with a Semicircular top)
//And compares the area to the perimeter based on length and width values stored
//as chromosomes in a Gene object.  The goal is for the program to find the
//optimum ratio of length to width that produces the largest area to perimeter.
//The optimum length and width for the rectangle of a Norman window is 1 as
//the most efficient base rectangle is a square.  This known value of 1 makes
//this a great first attempt as the results have a simple expected value
//that can be compared to, determining validity of the program.
package geneticalgorithm;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
public class GeneticAlgorithm
{
    public static void main(String[] args) throws IOException
    {
        //FileWriters and PrintWriters are here to have information to import
        //into Excel for some basic statistics.
        FileWriter wfw = new FileWriter("C:/Users/hirtq/Desktop/Widths.txt");
        FileWriter lfw = new FileWriter("C:/Users/hirtq/Desktop/Lengths.txt");
        FileWriter gfw = new FileWriter("C:/Users/hirtq/Desktop/Generations.txt");
        PrintWriter wpw = new PrintWriter(wfw);
        PrintWriter lpw = new PrintWriter(lfw);
        PrintWriter gpw = new PrintWriter(gfw);
        //Will run 1000 different populations
        for (int i = 0; i < 1; i++)
        {
            //creates the population
            Population population = new Population();
            //counter for the generations
            int generation = 0;
            //Runs mutations and crossovers until averageFitness is 100%
            while(population.getAverageFitness() != 1.0)
            {
                //increments generation counter
                generation++;
                //creates new gene population with reproduction pool
                population.crossover();
                //refresh the fitnessArray
                population.setFitnessArray();
                //refresh the averageFitness
                population.setAverageFitness();
                //refreshes reproductionPool
                population.setReproductionPool();
            }
            //Gets the length to width and width to length ratios
            double widthPerLength = population.getGene(0).getWidth() / population.getGene(0).getLength();
            double lengthPerWidth = population.getGene(0).getLength() / population.getGene(0).getWidth();
            //Writes to files
            wpw.println(widthPerLength);
            lpw.println(lengthPerWidth);
            gpw.println(generation);
        }
        //Saves the text to the file(s)
        wpw.close();
        lpw.close();
        gpw.close();
    }   
}
