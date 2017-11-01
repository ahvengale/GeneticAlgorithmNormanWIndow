package geneticalgorithm;
public class GeneFactory
{
    //GeneFactory object makes the genes
    private Gene gene;
    public GeneFactory()
    {
        //calls the setGene function
        setGene();
    }
    public void setGene()
    {
        //randomly generates the length and width
        double randomWidth = Math.random();
        double randomLength = Math.random();
        //creates gene from values
        Gene gene = new Gene(randomWidth, randomLength);
        //sets the gene to the GeneFactory Object
        this.gene = gene;
    }
    public Gene getGene()
    {
        return this.gene;
    }
}
