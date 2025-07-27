import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class donationRequest extends JFrame
{
    donationRequest(int id, String username)
    {
        // Fonts
        Font f1 = new Font("Futura",Font.BOLD,40);
        Font f2 = new Font("Calibri",Font.PLAIN,22);






        setSize(1600, 1000);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("BloodHelp");
    }

    public static void main(String[] args)
    {
        new donationRequest(10, "chai");
    }
}
