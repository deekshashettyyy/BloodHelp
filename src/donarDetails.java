import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class donarDetails extends JFrame
{
    int userID;
    String username;

    donarDetails(int userID , String username)
    {
        this.userID = userID;
        this.username = username;

        //fonts
        Font f1 = new Font("Futura",Font.BOLD,40);
        Font f2 = new Font("Calibri",Font.PLAIN,22);

        //labels
        JLabel title = new JLabel("Personal Details", JLabel.CENTER);

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

        JButton b1 = new JButton("Back");
        JButton b2 = new JButton("Update");

        ImageIcon imgIcon = new ImageIcon("./images/blood6.jpg");
        Image img = imgIcon.getImage().getScaledInstance(1300,700,Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(img);
        JLabel imgLabel = new JLabel(icon);
        imgLabel.setBounds(0,0,1600,1000);
        setContentPane(imgLabel);

        //removing border
        t1.setBorder(null);
        t2.setBorder(null);
        t3.setBorder(null);
        t4.setBorder(null);
        t5.setBorder(null);
        t6.setBorder(null);
        t7.setBorder(null);
        t8.setBorder(null);

        //setFont
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
        b1.setFont(f2);
        b2.setFont(f2);

        //Container
        Container c = getContentPane();
        c.setLayout(null);

//        bounds
        int labelX = 380 , fieldX = 580 , yStart = 130 , width = 280 , height = 30 , gap = 50;

        title.setBounds(380 , 35 , 500, 50);

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

        b1.setBounds(450, (int)(yStart + 8.5 * gap), 150, 35);
        b2.setBounds(650, (int)(yStart + 8.5 * gap), 150, 35);

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
        c.add(b1);
        c.add(b2);

        // SELECT = filling the form by fetching user's details from db
        String url = "jdbc:mysql://localhost:3306/bloodHelp";
        try(Connection con = DriverManager.getConnection(url,"root" , "Dee01$hetty"))
        {
            String sql = "SELECT * FROM users WHERE userID = ?";
            try(PreparedStatement pst = con.prepareStatement(sql))
            {
                pst.setInt(1,userID);
                ResultSet rs = pst.executeQuery();

                if(rs.next())
                {
                    t1.setText(rs.getString("name"));
                    t2.setText(rs.getString("email"));
                    t3.setText(rs.getString("phone"));
                    t4.setText(rs.getString("city"));
                    t5.setText(rs.getString("area"));
                    t6.setText(rs.getString("dob"));
                    t7.setSelectedItem(rs.getString("gender"));
                    t8.setText(rs.getString("password"));
                }
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null , e.getMessage());
        }


        // UPDATE button
        b2.addActionListener(
                a->{
                    //EDGE CASES
                    Object combo = t7.getSelectedItem();
                    if(t1.getText().isEmpty() || combo == null || combo.toString().equals("Select") || t8.getPassword().length == 0)
                    {
                        JOptionPane.showMessageDialog(null, "Please enter All values");
                        return;
                    }


                    try(Connection con = DriverManager.getConnection(url , "root" , "Dee01$hetty"))
                    {
                     String sql = "UPDATE users SET name=?, email=?, phone=?, city=?, area=?, dob=?, gender=?, password=? WHERE userID=?";
                        try(PreparedStatement pst = con.prepareStatement(sql))
                        {
                            this.username = t1.getText();
                            pst.setString(1, this.username);
                            pst.setString(2, t2.getText());
                            pst.setString(3, t3.getText());
                            pst.setString(4, t4.getText());
                            pst.setString(5, t5.getText());
                            pst.setString(6, t6.getText());
                            pst.setString(7, combo.toString());
                            pst.setString(8, new String(t8.getPassword()));
                            pst.setInt(9, userID);


                            pst.executeUpdate();
                            JOptionPane.showMessageDialog(null , "Details Updated Successfully");
                        }
                    }
                    catch(Exception e)
                    {
                        JOptionPane.showMessageDialog(null,e.getMessage());
                    }
                }
        );


        b1.addActionListener(
                a->{
                    new donarDashboard(userID ,this.username );
                    dispose();
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
        new donarDetails(2 , "deeksha");
    }
}
