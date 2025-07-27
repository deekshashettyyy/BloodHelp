import java.awt.*;
import javax.swing.*;

public class donarDashboard extends JFrame
{
    String username;
    int id ;
  donarDashboard(int userID , String username)
  {
      this.username = username;
      id = userID ;

  //Background Image
      //load image from file into imageIcon(img in frame)
      ImageIcon imgIcon = new ImageIcon("./images/blood1.png");
      // img ko remove from frame to resize
      Image img = imgIcon.getImage().getScaledInstance(1300,700,Image.SCALE_SMOOTH);
      // again putting resized img in frame = imageIcon
      ImageIcon icon = new ImageIcon(img);
      // why becoz JLabel needs imageIcon not img
      JLabel imgLabel = new JLabel(icon);
      imgLabel.setBounds(0,0,1600,1000);
      setContentPane(imgLabel);
      setLayout(null);

      //Fonts
      Font f1 = new Font("Futura", Font.BOLD ,50);
      Font f2 = new Font("Calibri",Font.PLAIN,25);

      // Labels & Buttons
      JLabel title = new JLabel("Welcome, "+username);
      JButton b1 = new JButton("Donation Requests");
      JButton b2 = new JButton("Donation History");
      JButton b3 = new JButton("Personal Details");
      JButton b4 = new JButton("Medical History");
      JButton b5 = new JButton("Donation Guidelines");
      JButton b6 = new JButton("Back to Login");

      // setFont
      title.setFont(f1);
      b1.setFont(f2);
      b2.setFont(f2);
      b3.setFont(f2);
      b4.setFont(f2);
      b5.setFont(f2);
      b6.setFont(f2);

      //Color
      title.setForeground(new Color(245, 245, 245));
      b1.setBackground(new Color(225, 225, 225));
      b2.setBackground(new Color(225, 225, 225));
      b3.setBackground(new Color(225, 225, 225));
      b4.setBackground(new Color(225, 225, 225));
      b5.setBackground(new Color(225, 225, 225));
      b6.setBackground(new Color(225, 225, 225));

      //focus remove
      b1.setFocusPainted(false);
      b2.setFocusPainted(false);
      b3.setFocusPainted(false);
      b4.setFocusPainted(false);
      b5.setFocusPainted(false);
      b6.setFocusPainted(false);


      // Bounds
      title.setBounds(600, 40,500,55);
      b1.setBounds(600, 200 , 250 , 45);
      b3.setBounds(900, 200 , 250 , 45);
      b2.setBounds(600, 290 , 250 , 45);
      b4.setBounds(900, 290 , 250 , 45);
      b5.setBounds(900,380,250,45);
      b6.setBounds(600,380,250,45);

      Container c = getContentPane();
      c.add(title);
      c.add(b1);
      c.add(b2);
      c.add(b3);
      c.add(b4);
      c.add(b5);
      c.add(b6);

      // go to login
      b6.addActionListener(
              a->{
                  new Login();
                  dispose();
              }
      );

      // personal details
      b3.addActionListener(
              a->{
                  new donarDetails(id , username);
                  dispose();
              }
      );

      // medical history
      b4.addActionListener(
              a->{
                  new donarMedical(id, username);
                  dispose();
              }
      );

      // donation requests
      b1.addActionListener(
              a->{
                  new donationRequest(id, username);
                  dispose();
              }
      );


      setSize(1600,1000);
      setVisible(true);
      setLocationRelativeTo(null);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setTitle("BloodHelp");
  }

  public static void main(String[] args)
  {
      new donarDashboard(1, "deeksha" );
  }
}
