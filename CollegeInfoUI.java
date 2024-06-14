import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CollegeInfoUI {

    private static College selectedCollege1;
    private static College selectedCollege2;

    private static JTextArea comparisonTextArea = new JTextArea(10, 50);

    public static void main(String[] args) {
        // Create the frame for the application
        JFrame frame = new JFrame("Campus Connect");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Create a tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Read colleges from CSV file
        ArrayList<College> colleges = readCollegesFromCSV();

        // Create the undergraduate colleges tab
        JPanel undergradPanel = createCollegePanel(colleges);
        tabbedPane.addTab("School Data", undergradPanel);

        // Create the "Compare" tab
        JPanel comparePanel = createComparePanel(colleges);
        tabbedPane.addTab("Compare Tool", comparePanel);

        // Create the "Find Best Fit" tab
        JPanel findBestFitPanel = createFindBestFitPanel(colleges);
        tabbedPane.addTab("Student Fit Tool", findBestFitPanel);

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

    private static JPanel createComparePanel(ArrayList<College> colleges) {
        JPanel panel = new JPanel(new BorderLayout());

        // Create a sub-panel to hold the drop-downs
        JPanel comboPanel = new JPanel(new FlowLayout());

        // Add an empty item to the start of the college list
        colleges.add(0, new College("", 0, 0, 0.0, ""));

        // Create combo boxes
        JComboBox<College> collegeComboBox1 = new JComboBox<>(colleges.toArray(new College[0]));
        JComboBox<College> collegeComboBox2 = new JComboBox<>(colleges.toArray(new College[0]));

        // Set custom renderer to display college names
        collegeComboBox1.setRenderer(new CollegeListCellRenderer());
        collegeComboBox2.setRenderer(new CollegeListCellRenderer());

        // Add action listeners to update selected colleges
        collegeComboBox1.addActionListener(e -> updateCollegeInfo(collegeComboBox1, true));
        collegeComboBox2.addActionListener(e -> updateCollegeInfo(collegeComboBox2, false));

        // Add combo boxes to the sub-panel
        comboPanel.add(new JLabel("Select College 1:"));
        comboPanel.add(collegeComboBox1);
        comboPanel.add(new JLabel("Select College 2:"));
        comboPanel.add(collegeComboBox2);

        // Add the sub-panel to the main compare panel
        panel.add(comboPanel, BorderLayout.NORTH);

        // Add the comparison text area
        comparisonTextArea.setEditable(false);
        panel.add(new JScrollPane(comparisonTextArea), BorderLayout.CENTER);

        return panel;
    }

    private static void updateCollegeInfo(JComboBox<College> comboBox, boolean isCollege1) {
        College selectedCollege = (College) comboBox.getSelectedItem();
        if (isCollege1) {
            selectedCollege1 = selectedCollege;
        } else {
            selectedCollege2 = selectedCollege;
        }

        // Update the comparison text area
        if (selectedCollege1 != null && selectedCollege2 != null &&
            !selectedCollege1.getName().isEmpty() && !selectedCollege2.getName().isEmpty()) {
            comparisonTextArea.setText(selectedCollege1.compare(selectedCollege2));
        } else {
            comparisonTextArea.setText("");
        }
    }

    private static JPanel createFindBestFitPanel(ArrayList<College> colleges) {
        JPanel panel = new JPanel(new BorderLayout());

        // Create input fields for user criteria
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        JTextField populationField = new JTextField();
        JTextField gpaField = new JTextField();
        JTextField satField = new JTextField();
        JButton findButton = new JButton("Find Best Fit");

        inputPanel.add(new JLabel("Ideal Population:"));
        inputPanel.add(populationField);
        inputPanel.add(new JLabel("Your GPA:"));
        inputPanel.add(gpaField);
        inputPanel.add(new JLabel("Your SAT:"));
        inputPanel.add(satField);
        inputPanel.add(new JLabel("")); // empty cell
        inputPanel.add(findButton);

        // Text area to display the result
        JTextArea resultTextArea = new JTextArea(10, 50);
        resultTextArea.setEditable(false);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(resultTextArea), BorderLayout.CENTER);

        // Add action listener to the find button
        findButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String populationText = populationField.getText();
                String gpaText = gpaField.getText();
                String satText = satField.getText();

                try {
                    int idealPopulation = Integer.parseInt(populationText);
                    double userGPA = Double.parseDouble(gpaText);
                    int userSAT = Integer.parseInt(satText);

                    College bestFit = findBestFitCollege(colleges, idealPopulation, userGPA, userSAT);
                    if (bestFit != null) {
                        resultTextArea.setText("Best Fit College:\n" + bestFit.getName());
                    } else {
                        resultTextArea.setText("No matching college found.");
                    }
                } catch (NumberFormatException ex) {
                    resultTextArea.setText("Please enter valid numbers for population, GPA, and SAT.");
                }
            }
        });

        return panel;
    }

    private static College findBestFitCollege(ArrayList<College> colleges, int idealPopulation, double userGPA, int userSAT) {
        College bestFit = null;
        double closestScore = Double.MAX_VALUE;

        final double GPA_SCALAR = 5000;
        final double SAT_SCALAR = 10;

        for (College college : colleges) {
            double score = Math.abs(college.getNumberOfStudents() - idealPopulation)
                         + Math.abs(college.getAverageGPA() - userGPA)*GPA_SCALAR
                         + Math.abs(college.getAverageSAT() - userSAT)*SAT_SCALAR;
            if (score < closestScore) {
                closestScore = score;
                bestFit = college;
            }
        }

        return bestFit;
    }

    private static ArrayList<College> readCollegesFromCSV() {
        ArrayList<College> colleges = new ArrayList<>();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("CSV files", "csv"));

        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (BufferedReader br = new BufferedReader(new FileReader(selectedFile))) {
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
        }
        return colleges;
    }
}

class CollegeListCellRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value instanceof College) {
            setText(((College) value).getName());
        }
        return this;
    }
}
