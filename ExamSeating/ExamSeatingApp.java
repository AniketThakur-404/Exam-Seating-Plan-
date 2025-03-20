import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

//Student Model Class
class Student{
    String name;
    int rollNumber;
    String course;
    String subject;
    String room;
    int seatNumber;

    public Student(String name, int rollNumber, String course,
    String subject, String room, int seatNumber){
        this.name = name;
        this.rollNumber = rollNumber;
        this.course = course;
        this.subject = subject;
        this.room = room;
        this.seatNumber = seatNumber;

    }
}

public class ExamSeatingApp  extends JFrame {
    private List<Student> students;
    private JTable table;
    private DefaultTableModel model;

    public ExamSeatingApp(){
        students = new ArrayList<>();
        setTitle("Exam Seating Arrangement");
        setSize(900, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Table Setup
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Roll No","Nmae","Course","Subject","Room","Seat No"});
        table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setBackground(Color.LIGHT_GRAY);
        table.setForeground(Color.BLACK);
        table.setFont(new Font("Arial", Font.BOLD, 14));
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Button panel
        JPanel panel = new JPanel();
        panel.setBackground(Color.DARK_GRAY);
        JButton addButton = createStyledButton("Add Student");
        JButton editButton = createStyledButton("Edit Student");
        JButton deleteButton = createStyledButton("Delete Student");
        JButton generateButton = createStyledButton("Generate Seating Arrangement");
        panel.add(addButton);
        panel.add(editButton);
        panel.add(deleteButton);
        panel.add(generateButton);
        add(panel, BorderLayout.SOUTH);

        // Button Actions
        addButton.addActionListener(e -> addStudent());
        editButton.addActionListener(e -> editStudent());
        deleteButton.addActionListener(e -> deleteStudent());
        generateButton.addActionListener(e -> generateSeating());
    
        }

    private JButton createStyledButton(String text){
        JButton button = new JButton(text);
        button.setBackground(Color.BLUE);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        return button;
        
    }

    private void addStudent(){
        String name = JOptionPane.showInputDialog("Enter Student Name:");
        if (name == null || name.trim().isEmpty()) return;
        String course = JOptionPane.showInputDialog("Enter Course:");
        if (course == null || course.trim().isEmpty()) return;
        String subject = JOptionPane.showInputDialog("Enter Subject:");
        if (subject == null || subject.trim().isEmpty()) return;
        int rollNumber = students.size() + 1;
        students.add(new Student(name, rollNumber, course,
        subject, "Room 1", rollNumber));
        updateTable();
    }
    
    private void editStudent(){
        int selectedRow = table.getSelectedRow();
        if(selectedRow == -1){
            JOptionPane.showMessageDialog(this, "Selete a student to edit");
            return;
        }
        String name = JOptionPane.showInputDialog("Enter New Name:");
        String course = JOptionPane.showInputDialog("Enter New Course:");
        String subject = JOptionPane.showInputDialog("Enter New Subject:", students.get(selectedRow).subject);
        if (name != null && course != null && subject != null){
            students.get(selectedRow).name = name;
            students.get(selectedRow).course = course;
            students.get(selectedRow).subject = subject;
            updateTable();
        } 
    }

    private  void deleteStudent(){
        int selectedRow = table.getSelectedRow();
        if(selectedRow == -1){
            JOptionPane.showMessageDialog(this, "Select a student to delete");
            return;
        }
        students.remove(selectedRow);
        updateTable();
    }

    private void generateSeating(){
        int seatNumber = 1;
        for(Student student : students){
            student.seatNumber = seatNumber++;
            student.room = (seatNumber % 2 == 0) ? "Room A": "Room B";
        }
        updateTable();
    }
    private void updateTable(){
        model.setRowCount(0);
        for(Student student : students){
            model.addRow(new Object[]{student.rollNumber, student.name, student.course, student.subject, student.room, student.seatNumber});
        }
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(()-> new ExamSeatingApp().setVisible(true));
    }
}