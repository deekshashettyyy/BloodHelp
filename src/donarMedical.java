import javax.swing.*;
import java.awt.*;
import java.sql.*;


public class donarMedical extends JFrame
{
    int userID;

    donarMedical(int userID , String username)
    {
        this.userID = userID;

        //Fonts
        Font f1 = new Font("Futura" , Font.BOLD , 45);
        Font f2 = new Font("Calibri" , Font.BOLD , 23);

        //labels & textfields
        JLabel title = new JLabel("Medical Details");

        JLabel l1 = new JLabel("1. Is your weight above 50kg?");
        JRadioButton rb1 = new JRadioButton("Yes");
        JRadioButton rb2 = new JRadioButton("No");
        ButtonGroup g1 = new ButtonGroup();
        g1.add(rb1);
        g1.add(rb2);

        JLabel l2 = new JLabel("2. Do you have Diabetes?");
        JRadioButton rb3 = new JRadioButton("Yes");
        JRadioButton rb4  = new JRadioButton("No");
        ButtonGroup g2 = new ButtonGroup();
        g2.add(rb3);
        g2.add(rb4);

        JLabel l3 = new JLabel("3. Do you have any Chronic illness?");
        JRadioButton rb5 = new JRadioButton("Yes");
        JRadioButton rb6  = new JRadioButton("No");
        ButtonGroup g3 = new ButtonGroup();
        g3.add(rb5);
        g3.add(rb6);

        JLabel l4 = new JLabel("4. Have you had any tattoos/piercings in last 6 months?");
        JRadioButton rb7 = new JRadioButton("Yes");
        JRadioButton rb8  = new JRadioButton("No");
        ButtonGroup g4 = new ButtonGroup();
        g4.add(rb7);
        g4.add(rb8);

        JLabel l5 = new JLabel("5. Your Blood Group : ");
        JLabel l6 = new JLabel();

        JButton b1 = new JButton("Back");
        JButton b2 = new JButton("Update");

        //border on focus remove
        rb1.setFocusPainted(false);
        rb2.setFocusPainted(false);
        rb3.setFocusPainted(false);
        rb4.setFocusPainted(false);
        rb5.setFocusPainted(false);
        rb6.setFocusPainted(false);
        rb7.setFocusPainted(false);
        rb8.setFocusPainted(false);

        // Content Pane
        Container c = getContentPane();
        setLayout(null);

//        bounds
        int labelX = 150 , yStart = 150 , width = 550 , height = 30 , gap = 50;

        title.setBounds(430 , 30 , 550, 50);

        l1.setBounds(labelX, yStart, width, height);
        rb1.setBounds(750,150,100,40);
        rb2.setBounds(900,150,100,40);

        l2.setBounds(labelX, yStart + gap, width, height);
        rb3.setBounds(750,150 + gap,100,40);
        rb4.setBounds(900,150 + gap,100,40);

        l3.setBounds(labelX, yStart + 2 * gap, width, height);
        rb5.setBounds(750,150 + 2 * gap,100,40);
        rb6.setBounds(900,150 + 2 * gap,100,40);

        l4.setBounds(labelX, yStart + 3 * gap, width, height);
        rb7.setBounds(750,150 + 3 * gap,100,40);
        rb8.setBounds(900,150 + 3 * gap,100,40);

        l5.setBounds(labelX, yStart + 4 * gap, width, height);
        l6.setBounds(375,150 + 4 * gap,190,30);

        b1.setBounds(400,(int)(150 + 5.7 * gap),150,35);
        b2.setBounds(600,(int)(150 + 5.7 * gap) ,150 ,35);


        c.add(title);
        c.add(l1);
        c.add(rb1);
        c.add(rb2);
        c.add(l2);
        c.add(rb3);
        c.add(rb4);
        c.add(l3);
        c.add(rb5);
        c.add(rb6);
        c.add(l4);
        c.add(rb7);
        c.add(rb8);
        c.add(l5);
        c.add(l6);
        c.add(b1);
        c.add(b2);


        //Color
        getContentPane().setBackground(Color.RED);
        title.setForeground(Color.white);
        rb1.setBackground(Color.RED);
        rb2.setBackground(Color.RED);
        rb3.setBackground(Color.RED);
        rb4.setBackground(Color.RED);
        rb5.setBackground(Color.RED);
        rb6.setBackground(Color.RED);
        rb7.setBackground(Color.RED);
        rb8.setBackground(Color.RED);

//        Fonts
        title.setFont(f1);
        l1.setFont(f2);
        rb1.setFont(f2);
        rb2.setFont(f2);
        l2.setFont(f2);
        rb3.setFont(f2);
        rb4.setFont(f2);
        l3.setFont(f2);
        rb5.setFont(f2);
        rb6.setFont(f2);
        l4.setFont(f2);
        rb7.setFont(f2);
        rb8.setFont(f2);
        l5.setFont(f2);
        l6.setFont(f2);
        b2.setFont(f2);
        b1.setFont(f2);

        setVisible(true);
        setSize(1600,1000);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("BloodHelp");

        // SELECT = fetching medical details from db
        String url = "jdbc:mysql://localhost:3306/bloodHelp";
        try(Connection con = DriverManager.getConnection(url,"root" , "Dee01$hetty"))
        {
            String sql = "SELECT * FROM donarMedHist WHERE userID = ?";
            try(PreparedStatement pst = con.prepareStatement(sql))
            {
                pst.setInt(1,this.userID);
                ResultSet rs = pst.executeQuery();

                if(rs.next())
                {
                    if(rs.getString("above50kg").equals("Yes"))
                    {
                        rb1.setSelected(true);
                    }
                    if(rs.getString("diabetes").equals("No"))
                    {
                        rb4.setSelected(true);
                    }
                    if(rs.getString("illness").equals("No"))
                    {
                        rb6.setSelected(true);
                    }
                    if(rs.getString("tattoos").equals("No"))
                    {
                        rb8.setSelected(true);
                    }
                    l6.setText(rs.getString("bloodgrp"));
                }

            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,e.getMessage());
        }

        //Back
        b1.addActionListener(
                a->{
                    new donarDashboard(this.userID , username);
                    dispose();
                }
        );

        //UPDATE
        b2.addActionListener(
                a->{

                    // if person updated details means he is not eligible to be donar now
                    if(rb2.isSelected() || rb3.isSelected() || rb5.isSelected() || rb7.isSelected())
                    {
                        try(Connection con = DriverManager.getConnection(url , "root" , "Dee01$hetty"))
                        {
                            String sql = "UPDATE donarMedHist SET above50kg=? , diabetes=? , illness=? , tattoos=? , eligible=? WHERE userID=? ";
                            try(PreparedStatement pst = con.prepareStatement(sql))
                            {
                                pst.setString(1,"No");
                                pst.setString(2,"Yes");
                                pst.setString(3,"Yes");
                                pst.setString(4,"Yes");
                                pst.setString(5,"No");
                                pst.setInt(6,this.userID);

                                pst.executeUpdate();
                                JOptionPane.showMessageDialog(null , "Based on your Updated Medical Details , you are NOT  eligible to Donate. ThankYou!");
                                new Login();
                                dispose();
                            }
                        }
                        catch(Exception e)
                        {
                            JOptionPane.showMessageDialog(null,e.getMessage());
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "No Changes made");
                    }

                }
        );


    }

    public static void main(String[] args)
    {
        new donarMedical(4,"deeksha");
    }
}
