import javax.swing.*;
import java.awt.*;
import java.sql.*;

class DonarForm extends JFrame
{
    String username;

    DonarForm(int userID , String name)
    {
        username = name;

        //Fonts
        Font f1 = new Font("Futura" , Font.BOLD , 30);
        Font f2 = new Font("Calibri" , Font.BOLD , 22);

        //labels & textfields
        JLabel title = new JLabel("Are you Eligible to be Donar?");

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

        JLabel l5 = new JLabel("5. Select your Blood Group");
        JComboBox<String> t9 = new JComboBox<>(new String[]{"Select" , "A+" , "B+" , "AB+" , "O+" , "A-" , "B-", "AB-", "O-" });

        JCheckBox l6 = new JCheckBox("I confirm that all the above information is true and I voluntarily agree to donate blood.");

        JButton b1 = new JButton("Check Eligibility");


//        removing radioButton mai jo text pe focus pe border ata hai voh
        rb1.setFocusPainted(false);
        rb2.setFocusPainted(false);
        rb3.setFocusPainted(false);
        rb4.setFocusPainted(false);
        rb5.setFocusPainted(false);
        rb6.setFocusPainted(false);
        rb7.setFocusPainted(false);
        rb8.setFocusPainted(false);
        l6.setFocusPainted(false);


        // Content Pane
        Container c = getContentPane();
        setLayout(null);

//        bounds
        int labelX = 150 , yStart = 150 , width = 550 , height = 30 , gap = 50;

        title.setBounds(400 , 30 , 550, 50);

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
        t9.setBounds(755,150 + 4 * gap,190,30);

        l6.setBounds(150,150 + 6 * gap,900,30);
        b1.setBounds(500,(int)(150 + 7.5 * gap) ,200 ,35);


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
        c.add(t9);
        c.add(l6);
        c.add(b1);


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
        l6.setBackground(Color.RED);

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
        t9.setFont(f2);
        l6.setFont(f2);
        b1.setFont(f2);

        b1.addActionListener(
                a->{
                    //edge cases = dono buttons not selected for any question
                    if(!rb1.isSelected() && !rb2.isSelected())
                    {
                        showError("Please Select for Question1");  //calling the function
                        return;
                    }
                    if(!rb3.isSelected() && !rb4.isSelected())
                    {
                        showError("Please Select for Question2");
                        return;
                    }
                    if(!rb5.isSelected() && !rb6.isSelected())
                    {
                        showError("Please Select for Question3");
                        return;
                    }
                    if(!rb7.isSelected() && !rb8.isSelected())
                    {
                        showError("Please Select for Question4");
                        return;
                    }
                    if(t9.getSelectedItem()==null ||t9.getSelectedItem().toString().equals("Select"))  // For ComboBox
                    {
                        showError("Please Select Your Blood Group");
                        return;
                    }
                    if(!l6.isSelected())
                    {
                        showError("Please give your Consent");
                        return;
                    }


                    // if all this is true = person eligible
                    if(rb1.isSelected() && rb4.isSelected() && rb6.isSelected() && rb8.isSelected() && l6.isSelected())
                    {

                        // Check if first time user = does not exist in donarMedHist table
                        String url = "jdbc:mysql://localhost:3306/bloodHelp";
                        try(Connection con = DriverManager.getConnection(url , "root" , "Dee01$hetty"))
                        {
                           String sql = "SELECT * FROM donarMedHist WHERE userID=?";
                           try(PreparedStatement pst = con.prepareStatement(sql))
                           {
                               pst.setInt(1,userID);

                               ResultSet rs = pst.executeQuery();
                               if(rs.next())                      //if user exists - UPDATE -> eligible = Yes
                               {
                                   update(userID);
                               }
                               else
                               {
                                   //user doesnt exists then add him - INSERT
                                   String sql3 = "INSERT INTO donarMedHist(userID , above50kg , diabetes , illness , tattoos , bloodgrp , eligible) VALUES(?,?,?,?,?,?,?)";
                                   try (PreparedStatement pst3 = con.prepareStatement(sql3))
                                   {
                                       pst3.setInt(1, userID);
                                       pst3.setString(2, "Yes");
                                       pst3.setString(3, "No");
                                       pst3.setString(4, "No");
                                       pst3.setString(5, "No");
                                       pst3.setString(6, t9.getSelectedItem().toString());
                                       pst3.setString(7, "Yes");

                                       pst3.executeUpdate();
                                       JOptionPane.showMessageDialog(null, "You are Eligible to be Donar");
                                       new donarDashboard(userID, username);
                                       dispose();
                                   }
                               }
                           }
                        }
                        catch(Exception e)
                        {
                            JOptionPane.showMessageDialog(null,e.getMessage());
                        }

                    }
                    else{
                        JOptionPane.showMessageDialog(null, "You are NOT Eligible to be Donar.ThankYou!");
//                        new Guidelines(); pg
                    }
                }
        );



        setVisible(true);
        setSize(1600,1000);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("BloodHelp");
    }

// instead of writing JOP.SMD for every Q we made a function
    void showError(String msg)
    {
        JOptionPane.showMessageDialog(null , msg);
    }

    void update(int userID)
    {
        String url = "jdbc:mysql://localhost:3306/bloodHelp";
        try(Connection con = DriverManager.getConnection(url , "root" , "Dee01$hetty"))
        {
            String sql2 = "UPDATE donarMedHist SET above50kg=? , diabetes=? , illness=? , tattoos=? , eligible=? WHERE userID=? ";
            try(PreparedStatement pst2 = con.prepareStatement(sql2))
            {
                pst2.setString(1, "Yes");
                pst2.setString(2, "No");
                pst2.setString(3, "No");
                pst2.setString(4, "No");
                pst2.setString(5, "Yes");
                pst2.setInt(6, userID);

                pst2.executeUpdate();
                JOptionPane.showMessageDialog(null,"You are now eligible to donate");
                new donarDashboard(userID,username);
                dispose();
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null , e.getMessage());
        }
    }


    public static void main(String[] args)
    {
        new DonarForm(1 , "demoUser");
    }
}
