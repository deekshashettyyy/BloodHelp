import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Receiver extends JFrame
{
    int userID ;
    Receiver(int userID, String username)
    {
        this.userID = userID;

        Font f1 = new Font("Futura" , Font.PLAIN , 45);
        Font f2 = new Font("Futura" , Font.PLAIN , 24);
        Font f3 = new Font("Calibri" ,Font.PLAIN, 22);

        JLabel title1 = new JLabel("Welcome "+username);

        JLabel title2 = new JLabel("Enter Patient Details to request Blood Donation");

        JLabel l1 = new JLabel("1. Name: ");
        JTextField t1 = new JTextField(20);

        JLabel l2 = new JLabel("2. Select Blood Group :");
        JComboBox<String> t2 = new JComboBox<>(new String[]{"Select" , "A+" , "B+" , "AB+" , "O+" , "A-" , "B-", "AB-", "O-" });

        JLabel l3 = new JLabel("3. Contact : ");
        JTextField t3 = new JTextField(15);

        JLabel l4 = new JLabel("4. Hospital Name / Address : ");
        JTextField t4 = new JTextField(30);

        JLabel l5 = new JLabel("5. Required : ");
        JComboBox<String> t5 = new JComboBox<>(new String[]{ "Select" , "Immediate" , "1-2days" , "within a week"});

        JButton b1 = new JButton("Send Request");
        JButton b2 = new JButton("Back to login");
        JButton b3 = new JButton("Track Request");

        //setFont
        title1.setFont(f1);
        title2.setFont(f2);
        l1.setFont(f3);
        t1.setFont(f3);
        l2.setFont(f3);
        t2.setFont(f3);
        l3.setFont(f3);
        t3.setFont(f3);
        l4.setFont(f3);
        t4.setFont(f3);
        l5.setFont(f3);
        t5.setFont(f3);
        b1.setFont(f3);
        b2.setFont(f3);
        b3.setFont(f3);

        //setBorder
        t1.setBorder(null);
        t2.setBorder(null);
        t3.setBorder(null);
        t4.setBorder(null);
        t5.setBorder(null);


        Container c = getContentPane();

        //Color
        c.setBackground(Color.red);
        title1.setForeground(Color.white);
        title2.setForeground(Color.white);


        // Bounds
        setLayout(null);

        int labelX = 300 , yStart = 180 , width = 550 , height = 30 , gap = 62;

        title1.setBounds(390 , 10 , 550, 50);
        title2.setBounds(300 , 115, 700, 50);

        l1.setBounds(labelX, yStart, width, height);
        t1.setBounds(620,yStart,270,35);

        l2.setBounds(labelX, yStart + gap, width, height);
        t2.setBounds(620,yStart + gap,270,35);

        l3.setBounds(labelX, yStart + 2 * gap, width, height);
        t3.setBounds(620,yStart + 2 * gap,270,35);

        l4.setBounds(labelX, yStart + 3 * gap, width, height);
        t4.setBounds(620,yStart + 3 * gap,270,35);

        l5.setBounds(labelX, yStart + 4 * gap, width, height);
        t5.setBounds(620,yStart + 4 * gap,270,35);

        b2.setBounds(310,(int)(yStart + 5.5 * gap),150,40);
        b1.setBounds(510,(int)(yStart + 5.5 * gap),160,40);
        b3.setBounds(710,(int)(yStart + 5.5 * gap),160,40);


        //add in Content Pane
        c.add(title1);
        c.add(title2);
        c.add(l1);
        c.add(t1);
        c.add(l2);
        c.add(t2);
        c.add(t3);
        c.add(l3);
        c.add(t3);
        c.add(l4);
        c.add(t4);
        c.add(l5);
        c.add(t5);
        c.add(b1);
        c.add(b2);
        c.add(b3);

        //Back to Login
        b2.addActionListener(
                a->{
                    new Login();
                    dispose();
                }
        );

        // Track Request button
        b3.addActionListener(
                a->{
                    new TrackRequest(this.userID , username);
                    dispose();
                }
        );

        //SEND REQUEST
        b1.addActionListener(
                a->{
                    Object bloodBox = t2.getSelectedItem();
                    Object reqBox = t5.getSelectedItem();

                    if (bloodBox == null || reqBox == null || bloodBox.toString().equals("Select") || reqBox.toString().equals("Select") || t1.getText().isEmpty() || t3.getText().isEmpty() || t4.getText().isEmpty())
                    {
                        JOptionPane.showMessageDialog(null , "Please enter all values");
                        return;
                    }

                    String url = "jdbc:mysql://localhost:3306/bloodHelp";

                    try(Connection con = DriverManager.getConnection(url, "root", "Dee01$hetty") )
                    {
                        String sql = "INSERT INTO patient(pname, bloodgrp, contact, address, required , receiverID) VALUES(?,?,?,?,?,?)";
                        try(PreparedStatement pst = con.prepareStatement(sql))
                        {
                            pst.setString(1, t1.getText());
                            pst.setString(2,bloodBox.toString());
                            pst.setString(3, t3.getText());
                            pst.setString(4, t4.getText());
                            pst.setString(5, reqBox.toString());
                            pst.setInt(6, this.userID);

                            pst.executeUpdate();
                            JOptionPane.showMessageDialog(null , "Request Send Successfully");
                        }
                    }
                    catch(Exception e)
                    {
                        JOptionPane.showMessageDialog(null, e.getMessage());
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
        new Receiver(1,"deeksha");
    }
}
