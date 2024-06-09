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
    public String compare(College otherCollege) {
        StringBuilder comparison = new StringBuilder();
        comparison.append("Comparison between ").append(this.name).append(" and ").append(otherCollege.getName()).append(":\n\n");
        comparison.append("Number of Students:\n");
        comparison.append(this.name).append(": ").append(this.numberOfStudents).append("\n");
        comparison.append(otherCollege.getName()).append(": ").append(otherCollege.getNumberOfStudents()).append("\n\n");
        comparison.append("Average SAT:\n");
        comparison.append(this.name).append(": ").append(this.averageSAT).append("\n");
        comparison.append(otherCollege.getName()).append(": ").append(otherCollege.getAverageSAT()).append("\n\n");
        comparison.append("Average GPA:\n");
        comparison.append(this.name).append(": ").append(this.averageGPA).append("\n");
        comparison.append(otherCollege.getName()).append(": ").append(otherCollege.getAverageGPA()).append("\n\n");
        comparison.append("Social Life Description:\n");
        comparison.append(this.name).append(": ").append(this.socialLifeDescription).append("\n");
        comparison.append(otherCollege.getName()).append(": ").append(otherCollege.getSocialLifeDescription()).append("\n");
        return comparison.toString();
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
