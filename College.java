public class College {
    private String name;
    private int numberOfStudents;
    private int averageSAT;
    private double averageGPA;
    private String socialLifeDescription;

    // Constructor
    public College(String name, int numberOfStudents, int averageSAT, double averageGPA, String socialLifeDescription) {
        this.name = name;
        this.numberOfStudents = numberOfStudents;
        this.averageSAT = averageSAT;
        this.averageGPA = averageGPA;
        this.socialLifeDescription = socialLifeDescription;
    }

    // Accessor methods
    public String getName() {
        return name;
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public int getAverageSAT() {
        return averageSAT;
    }

    public double getAverageGPA() {
        return averageGPA;
    }

    public String getSocialLifeDescription() {
        return socialLifeDescription;
    }

    // Display college information
    public void displayInformation() {
        System.out.println("College Name: " + name);
        System.out.println("Number of Students: " + numberOfStudents);
        System.out.println("Average SAT: " + averageSAT);
        System.out.println("Average GPA: " + averageGPA);
        System.out.println("Social Life Description: " + socialLifeDescription);
    }

    // Compare w/ another college
    public void compare(College otherCollege) {
        System.out.println("Comparison between " + this.name + " and " + otherCollege.getName() + ":");
        System.out.println("Number of Students:");
        System.out.println(this.name + ": " + this.numberOfStudents);
        System.out.println(otherCollege.getName() + ": " + otherCollege.getNumberOfStudents());

        System.out.println("Average SAT:");
        System.out.println(this.name + ": " + this.averageSAT);
        System.out.println(otherCollege.getName() + ": " + otherCollege.getAverageSAT());

        System.out.println("Average GPA:");
        System.out.println(this.name + ": " + this.averageGPA);
        System.out.println(otherCollege.getName() + ": " + otherCollege.getAverageGPA());

        System.out.println("Social Life Description:");
        System.out.println(this.name + ": " + this.socialLifeDescription);
        System.out.println(otherCollege.getName() + ": " + otherCollege.getSocialLifeDescription());
    }

    public static void main(String[] args) {
        //Ex:
        College harvard = new College("Harvard University", 21000, 1520, 4.18, "Vibrant social life with many activities");
        College yale = new College("Yale University", 14000, 1500, 4.15, "Rich environment and active student clubs");

        harvard.displayInformation();
        System.out.println();
        yale.displayInformation();
        System.out.println();

        harvard.compare(yale);
    }
}
