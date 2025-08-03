import javax.swing.*;
import java.awt.*;
import java.sql.*;

class Login extends JFrame
{
    Login()
    {
        //Fonts
        Font f1 = new Font("Futura" , Font.BOLD , 50);
        Font f2 = new Font("Calibri" ,Font.PLAIN, 22);


        // Labels $ Textfields
        JLabel title = new JLabel("Login" , JLabel.CENTER);
        JLabel l1 = new JLabel("Enter Name: ");
        JTextField t1 = new JTextField(20);
        JLabel l2 = new JLabel("Enter Password: ");
        JPasswordField t2 = new JPasswordField(20);
        JLabel l3 = new JLabel("Login as:");
        JComboBox<String> roleBox = new JComboBox<>(new String[]{"Select" , "Donar" , "Receiver"});
        JButton b1 = new JButton("New User?");
        JButton b2 = new JButton("Login");

        ImageIcon imgIcon = new ImageIcon("./images/blood1.png");
        Image img = imgIcon.getImage().getScaledInstance(1300,700,Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(img);
        JLabel imgLabel = new JLabel(icon);
        imgLabel.setBounds(0,0,1600,1000);
        setContentPane(imgLabel);


        // ContentPane
        Container c = getContentPane();
        setLayout(null);

        //Bounds
        title.setBounds(310 , 80, 600 ,80);
        int labelX = 420 , fieldX = 620 , yStart = 200 , width = 230 , height = 30 , gap = 60;

        l1.setBounds(labelX , yStart , width , height);
        t1.setBounds(fieldX , yStart , width , height);

        l2.setBounds(labelX , yStart + gap , width , height);
        t2.setBounds(fieldX , yStart + gap , width , height);

        l3.setBounds(labelX , yStart + 2 * gap , width , height);
        roleBox.setBounds(fieldX,yStart+ 2 * gap , width , height);

        b1.setBounds(500, (int)(yStart + 3.3 * gap), 140, 33);
        b2.setBounds(670, (int)(yStart + 3.3 * gap), 120, 33);

        //fonts
        title.setFont(f1);
        l1.setFont(f2);
        t1.setFont(f2);
        l2.setFont(f2);
        t2.setFont(f2);
        l3.setFont(f2);
        roleBox.setFont(f2);
        b1.setFont(f2);
        b2.setFont(f2);

        // Color
        title.setForeground(Color.WHITE);

        //remove border
        t1.setBorder(null);
        t2.setBorder(null);
        roleBox.setBorder(null);


        // add in content pane
        c.add(title);
        c.add(l1);
        c.add(t1);
        c.add(l2);
        c.add(t2);
        c.add(l3);
        c.add(roleBox);
        c.add(b1);
        c.add(b2);

        // New User button
        b1.addActionListener(
                a->{
                    new Registration();
                    dispose();
                }
        );

        // Login button
        b2.addActionListener(
                a->{

                    if(t1.getText().isEmpty() || t2.getPassword().length == 0)
                    {
                        JOptionPane.showMessageDialog(null , "Please Enter all values");
                        return;
                    }
                    Object role = roleBox.getSelectedItem();  // comboBox mai values are object

                    if(role==null || role.toString().equals("Select"))
                    {
                        JOptionPane.showMessageDialog(null, "Please Select Login Role Type");
                        return;

                    }

                    String login = role.toString();
                    String url = "jdbc:mysql://localhost:3306/bloodHelp";

                    try(Connection con = DriverManager.getConnection(url , "root" , "Dee01$hetty"))
                    {
                        String sql = "SELECT * FROM users WHERE name=? and password=?";
                        try(PreparedStatement pst = con.prepareStatement(sql))
                        {
                            pst.setString(1,t1.getText()); // first ? mai t1 ka input
                            String s = new String(t2.getPassword());
                            pst.setString(2, s);

                            ResultSet rs = pst.executeQuery();

                            if(rs.next())
                            {
                                JOptionPane.showMessageDialog(null , "Login Successful");

                                int userID = rs.getInt("userID");
                                String name = rs.getString("name");

                                if(login.equals("Donar"))
                                {
                                    String sqlCheck = "SELECT * FROM donarMedHist WHERE userID = ?";
                                    try(PreparedStatement ps = con.prepareStatement(sqlCheck))
                                    {
                                        ps.setInt(1,userID);
                                        ResultSet r = ps.executeQuery();

                                        //if user details exits in donarMedHist table means he has filled form once so directly go to dashboard
                                        if(r.next() && r.getString("eligible").equals("Yes"))
                                        {
                                            new donarDashboard(userID, name);
                                            dispose();
                                        }
                                        else  // if first time or is not eligible then again fill donar form
                                        {
                                            new DonarForm(userID, name);
                                            dispose();
                                        }
                                    }
                                }
                                else if(login.equals("Receiver"))
                                {
                                    new Receiver(userID, name);
                                    dispose();
                                }

                            }
                            else
                            {
                                JOptionPane.showMessageDialog(null , "User doesn't exist");
                            }
                        }
                    }
                    catch(Exception e)
                    {
                        JOptionPane.showMessageDialog(null , e.getMessage());
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
        new Login();
    }
}
