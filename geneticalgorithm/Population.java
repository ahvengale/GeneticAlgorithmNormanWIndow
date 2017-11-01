package geneticalgorithm;
import java.util.Arrays;
public class Population
{
    //Population Object primarily is a Gene[]
    private Gene[] genes;
    //Parameter for Population size
    private int maxPopulation = 1000;
    //Used to store the fitness of the gene and stores at an index
    //equal to the gene it represents
    private int[] fitnessArray;
    //Gene[] that is created from the fitnessArray and genes array
    private Gene[] reproductionPool;
    //value for average fitness
    private double averageFitness;
    //control the rate of mutation where 1 is 100% chance of mutation
    private double mutationRate = .01;
    public Population()
    {
        //Object values
        setGenes();
        this.maxPopulation = maxPopulation;
        this.mutationRate = mutationRate;
        setFitnessArray();
        this.averageFitness = averageFitness;
        setReproductionPool();
    }
    public void setGenes()
    {
        //Creates the genes[] of size maxPopulation
        Gene[] genes = new Gene[maxPopulation];
        //Loops for maxPopulation times
        for (int i = 0; i < maxPopulation; i++)
        {
            //creates the GeneFactory object, which is random
            GeneFactory factory = new GeneFactory();
            //creates a gene object from the random gene of the GeneFactory
            Gene gene = factory.getGene();
            //Stores the gene in the genes[]
            genes[i] = gene;
        }
        //sets the Gene[]
        this.genes = genes;
    }
    public Gene getGene(int geneIndex)
    {
        return genes[geneIndex];
    }
    public void displayGenes()
    {
        //iterates through the genes[] and uses the overridden toString() in Gene
        for (int i = 0; i < maxPopulation; i++)
        {
            System.out.println(genes[i].toString());
        }
    }
    public void setFitnessArray()
    {
        //creates the fitnessArray
        int[] fitnessArray = new int[maxPopulation];
        //iterates through the genes[] and fitnessArray[]
        for (int i = 0; i < maxPopulation; i++)
        {
            //gene values gotten
            double width = genes[i].getWidth();
            double length = genes[i].getLength();
            //values used to make perimeter and area
            double perimeter = ((2 * length) + width) + (.5 * width * Math.PI);
            double area = ((length * width) + (.5 * (width * .5) * (width * .5) * Math.PI));
            //the fitness for this program is the ratio of area to perimeter
            double fitness = area / perimeter;
            //fitness * 100 is to give a higher value to the fitness so that the
            //setReproductionArray has more genes to pool from as the area / per
            //value can be exceedingly low to the point it will have a value of
            //0 when cast to a int
            fitness = fitness * 100;
            //stores fitness to the array
            fitnessArray[i] = (int)fitness;
        }
        this.fitnessArray = fitnessArray;
    }
    public int[] getFitnessArray()
    {
            return this.fitnessArray;
    }
    public void setAverageFitness()
    {
        //Finds the max fitness value and compares all other values to it
        //to generate a average fitness.  Fairly simple and will not go into
        //great length about the process, but the average fitness allows the
        //program to have an exit condition when the average fitness reaches
        //a ceratin value, in this case it is 100%, controled by the while loop
        //in the main method
        double averageFitness = 0;
        int maxFitness = fitnessArray[0];
        for (int i = 0; i < maxPopulation; i++)
        {
            if(maxFitness < fitnessArray[i])
            {
                maxFitness = fitnessArray[i];
            }
        }
        for (int i = 0; i < maxPopulation; i++)
        {
            averageFitness += (fitnessArray[i] / maxFitness);
        }
        averageFitness = averageFitness / maxPopulation;
        this.averageFitness = averageFitness;
    }
    public double getAverageFitness()
    {
        return this.averageFitness;
    }
    public void setReproductionPool()
    {
        //determines the length that the reproduction pool needs to be
        //which is the sum of the fitnessArray
        int poolLength = 0;
        for (int i = 0; i < fitnessArray.length; i++)
        {
            poolLength += fitnessArray[i];
        }
        //creates the reproductionPool Gene[]
        Gene[] reproductionPool = new Gene[poolLength];
        //creates a counter for the index of the reproductionPool that we
        //will be storing Gene Objects in.  This will handle the probability
        //that a gene will used in crossover based around its occurance in the
        //reproductionPool, which is proportionate to its fitness value
        int counter = 0;
        //for the length of the fitnessArray = maxPopulation
        for (int i = 0; i < maxPopulation; i++)
        {
            //for the value of the fitnessArray of index i
            for (int j = 0; j < fitnessArray[i]; j++)
            {
                //stores the gene at index i to the counted index of the counter
                //this counter will reach a max value of poolLength - 1
                reproductionPool[counter] = genes[i];
                counter++;
            }
        }
        this.reproductionPool = reproductionPool;
    }
    public Gene[] getReproductionPool()
    {
        return this.reproductionPool;
    }
    public void crossover()
    {
        //creates the crossover Gene[]
        Gene[] crossoverArray = new Gene[maxPopulation];
        //iterates maxPopulation / 2 times as i is incremented by 2
        //this is due to the for loop createing 2 genes that are stored
        //therefor the Gene[] will reach maxPopulation in half the iterations
        //of the value of maxPopulation
        for (int i = 0; i < maxPopulation; i += 2)
        {
            //set parent1 to a random index of the reproductionPool
            Gene parent1 = reproductionPool[(int)(Math.random()*reproductionPool.length)];
            //set parent2 to a random index of the reproductionPool
            Gene parent2 = reproductionPool[(int)(Math.random()*reproductionPool.length)];
            //retrieve values from the paretn genes
            double width1 = parent1.getWidth();
            double length1 = parent1.getLength();
            double width2 = parent2.getWidth();
            double length2 = parent2.getLength();
            //create 2 child genes that have swapped 'Chromosomes'
            Gene child1 = new Gene(width1, length2);
            Gene child2 = new Gene(width2, length1);
            //add the child genes to the crossover Gene[]
            crossoverArray[i] = child1;
            crossoverArray[i+1] = child2;
        }
        this.genes = crossoverArray;
    }
    public void mutate()
    {
        //adds a level of randomness to combat local maxim and lack of variation
        //in the initial population and future generations
        //Iterates through the genes Gene[]
        for (int i = 0; i < maxPopulation; i++)
        {
            //randomly determines if either chromosome is to be mutated based on
            //the mutationRate value.
            //If mutated, the chromosome is randomly reassigned to the gene.
            double mutateWidth = Math.random();
            if(mutateWidth < mutationRate)
            {
                genes[i].setWidth(Math.random());
            }
            double mutateLength = Math.random();
            if(mutateLength < mutationRate)
            {
                genes[i].setLength(Math.random());
            }
        }
    }
}
