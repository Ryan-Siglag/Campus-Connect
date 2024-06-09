import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CollegeInfoUI {

    public static void main(String[] args) {
        // Create the frame for the application
        JFrame frame = new JFrame("College Information");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Create a tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Read colleges from CSV file
        ArrayList<College> colleges = readCollegesFromCSV("colleges.csv");

        // Separate undergraduate and graduate colleges (not used anymore, just separating for clarity)
        ArrayList<College> undergradColleges = new ArrayList<>();
        for (College college : colleges) {
            undergradColleges.add(college);
        }

        // Create the undergraduate colleges tab
        JPanel undergradPanel = createCollegePanel(undergradColleges);
        tabbedPane.addTab("Undergraduate Colleges", undergradPanel);

        // Create the empty "Compare" tab
        JPanel comparePanel = new JPanel(new BorderLayout());
        tabbedPane.addTab("Compare", comparePanel);

        // Add the tabbed pane to the frame
        frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

        // Make the frame visible
        frame.setVisible(true);
    }

    private static JPanel createCollegePanel(ArrayList<College> colleges) {
        JPanel panel = new JPanel(new BorderLayout());

        // Column names for the table
        String[] columnNames = {"College Name", "Number of Students", "Average SAT Score", "Average GPA", "Social Life Description"};

        // Create data array from College objects
        Object[][] data = new Object[colleges.size()][5];
        for (int i = 0; i < colleges.size(); i++) {
            College college = colleges.get(i);
            data[i][0] = college.getName();
            data[i][1] = college.getNumberOfStudents();
            data[i][2] = college.getAverageSAT();
            data[i][3] = college.getAverageGPA();
            data[i][4] = college.getSocialLifeDescription();
        }

        // Create a table model and set the data and column names
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model);

        // Put the table inside a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        // Add the scroll pane to the panel
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private static ArrayList<College> readCollegesFromCSV(String fileName) {
        ArrayList<College> colleges = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            br.readLine(); // Skip the header line
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == 5) {
                    String name = values[0];
                    int numberOfStudents = Integer.parseInt(values[1]);
                    int averageSAT = Integer.parseInt(values[2]);
                    double averageGPA = Double.parseDouble(values[3]);
                    String socialLifeDescription = values[4];
                    College college = new College(name, numberOfStudents, averageSAT, averageGPA, socialLifeDescription);
                    colleges.add(college);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return colleges;
    }
}