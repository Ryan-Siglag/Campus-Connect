import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CollegeInfoUI {

    public static void main(String[] args) {
        // Create the frame for the application
        JFrame frame = new JFrame("College Information");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Create a tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Create data for undergraduate colleges
        College[] undergradColleges = {
            new College("Harvard University", 20000, 1500, 3.9, "Vibrant social life with numerous activities."),
            new College("Stanford University", 16000, 1480, 3.8, "Active campus with diverse social events."),
            new College("MIT", 11000, 1490, 3.85, "Engaging community with a focus on technology."),
            new College("Caltech", 1000, 1510, 3.87, "Close-knit community with a focus on research."),
            new College("Princeton University", 8000, 1520, 3.88, "Traditional campus life with many clubs and societies.")
        };

        // Create data for graduate schools
        College[] gradColleges = {
            new College("Harvard Graduate School", 10000, 1550, 3.95, "Dynamic graduate community."),
            new College("Stanford Graduate School", 8000, 1540, 3.92, "Innovative and collaborative environment."),
            new College("MIT Graduate School", 7000, 1530, 3.93, "Cutting-edge research opportunities."),
            new College("Caltech Graduate School", 500, 1560, 3.96, "Intensive research-focused programs."),
            new College("Princeton Graduate School", 3000, 1570, 3.94, "Supportive and resource-rich environment.")
        };

        // Create the undergraduate colleges tab
        JPanel undergradPanel = createCollegePanel(undergradColleges);
        tabbedPane.addTab("Undergraduate Colleges", undergradPanel);

        // Create the graduate schools tab
        JPanel gradPanel = createCollegePanel(gradColleges);
        tabbedPane.addTab("Graduate Schools", gradPanel);

        // Add the tabbed pane to the frame
        frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

        // Make the frame visible
        frame.setVisible(true);
    }

    private static JPanel createCollegePanel(College[] colleges) {
        JPanel panel = new JPanel(new BorderLayout());

        // Column names for the table
        String[] columnNames = {"College Name", "Number of Students", "Average SAT Score", "Average GPA", "Social Life Description"};

        // Create data array from College objects
        Object[][] data = new Object[colleges.length][5];
        for (int i = 0; i < colleges.length; i++) {
            data[i][0] = colleges[i].getName();
            data[i][1] = colleges[i].getNumberOfStudents();
            data[i][2] = colleges[i].getAverageSAT();
            data[i][3] = colleges[i].getAverageGPA();
            data[i][4] = colleges[i].getSocialLifeDescription();
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
}
