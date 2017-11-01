package geneticalgorithm;
public class Gene
{
    //Two 'Chromosomes'
    private double width;
    private double length;
    //Gene object constructor
    public Gene(double width, double length)
    {
        this.width = width;
        this.length = length;
    }

    /**
     * @return the width
     */
    public double getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(double width) {
        this.width = width;
    }

    /**
     * @return the length
     */
    public double getLength() {
        return length;
    }

    /**
     * @param length the length to set
     */
    public void setLength(double length) {
        this.length = length;
    }
    //Overrides the toString() from the Object superclass
    @Override
    public String toString()
    {
        String output = ("Width: " + this.width + "\nLength: " + this.length);
        return output;
    }
}
