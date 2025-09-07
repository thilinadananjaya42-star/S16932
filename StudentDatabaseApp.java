import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class StudentDatabaseApp extends JFrame {
    Connection con;
    PreparedStatement pst;
    ResultSet rs;

    JTextField txtName, txtRoll, txtCourse, txtMarks;
    JButton btnAdd, btnView;

    public StudentDatabaseApp() {
        setTitle("Student Database ");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 2));

        add(new JLabel("Student Name:"));
        txtName = new JTextField();
        add(txtName);

        add(new JLabel("Roll No:"));
        txtRoll = new JTextField();
        add(txtRoll);

        add(new JLabel("Course:"));
        txtCourse = new JTextField();
        add(txtCourse);

        add(new JLabel("Marks:"));
        txtMarks = new JTextField();
        add(txtMarks);

        btnAdd = new JButton("Add Student");
        btnView = new JButton("View Students");
        add(btnAdd);
        add(btnView);

        connect();

        btnAdd.addActionListener(e -> insertStudent());
        btnView.addActionListener(e -> viewStudents());

        setVisible(true);
    }

    public void connect() {
        try {
            con = DriverManager.getConnection("jdbc:sqlite:students.db");
            Statement st = con.createStatement();
            st.execute("CREATE TABLE IF NOT EXISTS students (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, roll_no TEXT, course TEXT, marks INTEGER)");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void insertStudent() {
        try {
            pst = con.prepareStatement("INSERT INTO students(name, roll_no, course, marks) VALUES(?,?,?,?)");
            pst.setString(1, txtName.getText());
            pst.setString(2, txtRoll.getText());
            pst.setString(3, txtCourse.getText());
            pst.setInt(4, Integer.parseInt(txtMarks.getText()));
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Student Added!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void viewStudents() {
        try {
            rs = con.createStatement().executeQuery("SELECT * FROM students");
            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                sb.append(rs.getInt("id")).append(" - ")
                  .append(rs.getString("name")).append(" - ")
                  .append(rs.getString("roll_no")).append(" - ")
                  .append(rs.getString("course")).append(" - ")
                  .append(rs.getInt("marks")).append("\n");
            }
            JOptionPane.showMessageDialog(this, sb.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new StudentDatabaseApp();
    }
}
