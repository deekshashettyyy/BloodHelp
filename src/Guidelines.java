import javax.swing.*;
import java.awt.*;

public class Guidelines extends JFrame
{
    Guidelines(int id , String username)
    {
        Font f2 = new Font("Calibri",Font.PLAIN,22);

        ImageIcon imgIcon = new ImageIcon("./images/guide.png");
        Image img = imgIcon.getImage().getScaledInstance(1200,600,Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(img);
        JLabel imgLabel = new JLabel(icon);
        imgLabel.setBounds(0,0,1600,1000);
        setContentPane(imgLabel);

        JButton back = new JButton("Back");

        back.setFont(f2);

        Container c = getContentPane();
        setLayout(null);

        back.setBounds(550,600,100,40);
        back.setForeground(Color.WHITE);
        back.setBackground(new Color(255, 51, 51));
        back.setFocusPainted(false);
        back.setBorder(BorderFactory.createEmptyBorder(5,20,5,20));

        c.add(back);


        back.addActionListener(
                a->{
                    new donarDashboard(id, username);
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
        new Guidelines(1, "deeksha");
    }
}
