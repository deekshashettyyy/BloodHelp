import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


public class donationHistory extends JFrame
{
    int userID;
    String username;

    donationHistory(int userID , String username)
    {
        this.userID = userID;
        this.username = username;

        // Fonts
        Font f1 = new Font("Futura",Font.BOLD,40);
        Font f2 = new Font("Calibri",Font.PLAIN,22);


        JLabel title = new JLabel("Donation History" , JLabel.CENTER);

        title.setFont(f1);
        title.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        title.setForeground(Color.WHITE);
        title.setOpaque(true);
        title.setBackground(Color.RED);


        String[] columnNames = {"Date & Time" , "Patient Name" , "Hospital Name / Address", "Status"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        table.setRowHeight(30);

        table.setFont(new Font("Calibri",Font.PLAIN,18));
        table.getTableHeader().setFont(new Font("Calibri", Font.BOLD , 18));
        table.getTableHeader().setBackground(Color.RED);
        table.getTableHeader().setForeground(Color.WHITE);
        table.setGridColor(new Color(224, 224, 224));

        JScrollPane scroll = new JScrollPane(table);

        JButton b1 = new JButton("Back");

        b1.setFont(f2);
        b1.setForeground(Color.WHITE);
        b1.setBackground(new Color(255, 51, 51));
        b1.setFocusPainted(false);
        b1.setBorder(BorderFactory.createEmptyBorder(5,20,5,20));


        // panels
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.RED);
        topPanel.add(title , BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(224,224,224));
        bottomPanel.add(b1);   // back button


        Container c = getContentPane();
        setLayout(new BorderLayout(20,20));

        c.add(topPanel, BorderLayout.NORTH);
        c.add(scroll, BorderLayout.CENTER);
        c.add(bottomPanel, BorderLayout.SOUTH);

        //back button
        b1.addActionListener(
                a->{
                    new donarDashboard(this.userID, this.username);
                    dispose();
                }
        );





        setSize(1600, 1000);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("BloodHelp");

    }

    public static void main(String[] args)
    {
        new donationHistory(1,"deeksha");
    }
}
