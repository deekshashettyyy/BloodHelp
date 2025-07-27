import javax.swing.*;
import java.awt.*;
import java.sql.*;

class Registration extends JFrame
{
    Registration()
    {
        Font f1 = new Font("Futura",Font.BOLD,40);
        Font f2 = new Font("Calibri",Font.PLAIN,22);

        JLabel title = new JLabel("User Registration", JLabel.CENTER);

        JLabel l1 = new JLabel("Name:");
        JTextField t1 = new JTextField(10);

        JLabel l2 = new JLabel("Email:");
        JTextField t2 = new JTextField(20);

        JLabel l3 = new JLabel("Phone:");
        JTextField t3 = new JTextField(10);

        JLabel l4 = new JLabel("City:");
        JTextField t4 = new JTextField(10);

        JLabel l5 = new JLabel("Area:");
        JTextField t5 = new JTextField(10);

        JLabel l6 = new JLabel("Date Of Birth:");
        JTextField t6 = new JTextField(20);

        JLabel l7 = new JLabel("Gender");
        JComboBox<String> t7 = new JComboBox<>(new String[]{"Select","Male","Female","Other"});

        JLabel l8 = new JLabel("Password:");
        JPasswordField t8 = new JPasswordField(10);

        JLabel l9 = new JLabel("Confirm Password:");
        JPasswordField t9 = new JPasswordField(10);

        JButton b1 = new JButton("Go to Login");
        JButton b2 = new JButton("Register");


        //removing border
        t1.setBorder(null);
        t2.setBorder(null);
        t3.setBorder(null);
        t4.setBorder(null);
        t5.setBorder(null);
        t6.setBorder(null);
        t7.setBorder(null);
        t8.setBorder(null);
        t9.setBorder(null);


        title.setFont(f1);
        l1.setFont(f2);
        t1.setFont(f2);
        l2.setFont(f2);
        t2.setFont(f2);
        l3.setFont(f2);
        t3.setFont(f2);
        l4.setFont(f2);
        t4.setFont(f2);
        l5.setFont(f2);
        t5.setFont(f2);
        l6.setFont(f2);
        t6.setFont(f2);
        l7.setFont(f2);
        t7.setFont(f2);
        l8.setFont(f2);
        t8.setFont(f2);
        l9.setFont(f2);
        t9.setFont(f2);
        b1.setFont(f2);
        b2.setFont(f2);

        Container c = getContentPane();
        c.setLayout(null);

//        bounds
        int labelX = 380 , fieldX = 580 , yStart = 110 , width = 280 , height = 30 , gap = 50;

        title.setBounds(380 , 30 , 500, 50);

        l1.setBounds(labelX, yStart, width, height);
        t1.setBounds(fieldX, yStart, width, height);

        l2.setBounds(labelX, yStart + gap, width, height);
        t2.setBounds(fieldX, yStart + gap, width, height);

        l3.setBounds(labelX, yStart + 2 * gap, width, height);
        t3.setBounds(fieldX, yStart + 2 * gap, width, height);

        l4.setBounds(labelX, yStart + 3 * gap, width, height);
        t4.setBounds(fieldX, yStart + 3 * gap, width, height);

        l5.setBounds(labelX, yStart + 4 * gap, width, height);
        t5.setBounds(fieldX, yStart + 4 * gap, width, height);

        l6.setBounds(labelX, yStart + 5 * gap, width, height);
        t6.setBounds(fieldX, yStart + 5 * gap, width, height);

        l7.setBounds(labelX, yStart + 6 * gap, width, height);
        t7.setBounds(fieldX, yStart + 6 * gap, width, height);

        l8.setBounds(labelX, yStart + 7 * gap, width, height);
        t8.setBounds(fieldX, yStart + 7 * gap, width, height);

        l9.setBounds(labelX, yStart + 8 * gap, width, height);
        t9.setBounds(fieldX, yStart + 8 * gap, width, height);

        b1.setBounds(450, (int)(yStart + 9.2 * gap), 150, 35);
        b2.setBounds(650, (int)(yStart + 9.2 * gap), 150, 35);


//        COLOR
        getContentPane().setBackground(Color.red);
        title.setForeground(Color.white);

        c.add(title);
        c.add(l1);
        c.add(t1);
        c.add(l2);
        c.add(t2);
        c.add(l3);
        c.add(t3);
        c.add(l4);
        c.add(t4);
        c.add(l5);
        c.add(t5);
        c.add(l6);
        c.add(t6);
        c.add(l7);
        c.add(t7);
        c.add(l8);
        c.add(t8);
        c.add(l9);
        c.add(t9);
        c.add(b1);
        c.add(b2);

//        b1 = Go to Login
        b1.addActionListener(
                a->{
                    new Login();
                    dispose();
                }
        );

//        b2 = Register
        b2.addActionListener(
                a->{
                    if(t1.getText().isEmpty() || t4.getText().isEmpty() || t6.getText().isEmpty())  //edge cases
                    {
                        JOptionPane.showMessageDialog(null,"Please enter name, city, dob");
                        return;
                    }
                    String url = "jdbc:mysql://localhost:3306/bloodHelp";
                    try(Connection con = DriverManager.getConnection(url, "root" , "Dee01$hetty"))
                    {
                        String sql = "INSERT INTO users(name, email, phone, city, area, dob, gender, password) VALUES(?,?,?,?,?,?,?,?)";
                        try(PreparedStatement pst = con.prepareStatement(sql))
                        {
                            pst.setString(1,t1.getText());
                            pst.setString(2,t2.getText());
                            pst.setString(3,t3.getText());
                            pst.setString(4,t4.getText());
                            pst.setString(5,t5.getText());
                            pst.setString(6,t6.getText());

                            Object role = t7.getSelectedItem();
                            if(role == null || role.toString().equals("Select"))  //Checking for Null pointer Exception
                            {
                                JOptionPane.showMessageDialog(null,"Please select gender");
                                return;
                            }
                            else{
                                pst.setString(7, role.toString());  // t7 is jumboBox converting it to string
                            }

                            if(t8.getPassword().length == 0)
                            {
                                JOptionPane.showMessageDialog(null, "Please enter password");
                                return;
                            }
                            else {
                                String s1 = new String(t8.getPassword());
                                String s2 = new String(t9.getPassword());

                                if(s1.equals(s2))
                                {
                                    pst.setString(8,s1);
                                }
                                else
                                {
                                    JOptionPane.showMessageDialog(null,"passwords do not match");
                                    return;
                                }
                            }

                            pst.executeUpdate();
                            JOptionPane.showMessageDialog(null,"Registration Successful");
                        }
                    }
                    catch(Exception e)
                    {
                        JOptionPane.showMessageDialog(null,e.getMessage());
                    }
                }
        );


        setVisible(true);
        setSize(1600,1000);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("BloodHelp");

    }

    public static void main(String[] args)
    {
        new Registration();
    }
}
