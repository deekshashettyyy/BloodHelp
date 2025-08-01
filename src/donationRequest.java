import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class donationRequest extends JFrame
{
    int userID ;
    String username ;
    JTable table;

    donationRequest(int id, String username)
    {
        userID = id;
        this.username = username;

        // Fonts
        Font f1 = new Font("Futura",Font.BOLD,40);
        Font f2 = new Font("Calibri",Font.PLAIN,22);


        JLabel title = new JLabel("Donation Requests" , JLabel.CENTER);

        title.setFont(f1);
        title.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        title.setForeground(Color.WHITE);
        title.setOpaque(true);
        title.setBackground(Color.RED);


        String[] columnNames = {"Date & Time", "PatientID" , "Patient Name", "BloodGroup" , "Contact", "Hospital Name / Address", "Required"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
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

        //Fetch donar's bloodgrp
        String donarBloodgrp = donarBlood(userID);


//         show details in table = fetch from patient table
        String url = "jdbc:mysql://localhost:3306/bloodHelp";
        try(Connection con = DriverManager.getConnection(url, "root", "Dee01$hetty"))
        {
//   way1         String sql = "SELECT * FROM patient ORDER BY requestTime DESC";
            String sql = "SELECT * FROM patient WHERE bloodgrp=? ORDER BY requestTime DESC";
            try(PreparedStatement pst = con.prepareStatement(sql) )
            {
                pst.setString(1,donarBloodgrp);
                ResultSet rs = pst.executeQuery();

                while(rs.next())
                {
                    String dateTime = rs.getString("requestTime");
                    String patientName = rs.getString("pname");
                    String patientbloodGroup = rs.getString("bloodgrp");
                    String contact = rs.getString("contact");
                    String patientAddress = rs.getString("address");
                    String required = rs.getString("required");
                    int patientID = rs.getInt("patientID");

                    tableModel.addRow(new Object[]{dateTime ,patientID ,  patientName , patientbloodGroup , contact , patientAddress, required });


// way1               if(donarBloodgrp.equals(patientbloodGroup))
//                    {
//                        tableModel.addRow(new Object[]{dateTime ,patientID ,  patientName , patientbloodGroup , contact , patientAddress, required });
//                    }
                }
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null , e.getMessage());
        }

        // when clicked on any row call  handleRowClick(row);
        table.addMouseListener(new MouseAdapter(){
           public void mouseClicked(MouseEvent e)
           {
               int row = table.rowAtPoint(e.getPoint());   // getting row on which user clicked
               if(row>=0)
               {
                   handleRowClick(row);
               }
           }
        });

        b1.addActionListener(
                a->{
                    new donarDashboard(userID, this.username);
                    dispose();
                }
        );

        setSize(1600, 1000);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("BloodHelp");
    }

    // Find donarBlood
    String donarBlood(int userID)
    {
        String bloodgrp="";
        String url = "jdbc:mysql://localhost:3306/bloodHelp";
        try(Connection con = DriverManager.getConnection(url , "root" , "Dee01$hetty"))
        {
            String sql = "SELECT * FROM donarMedHist WHERE userID=?";
            try(PreparedStatement pst = con.prepareStatement(sql))
            {
                pst.setInt(1, userID);

                ResultSet rs = pst.executeQuery();
                if(rs.next())
                {
                    bloodgrp = rs.getString("bloodgrp");
                }
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return bloodgrp;
    }


    void handleRowClick(int row)
    {
        //getting details of patient row
        int patientID = (int) table.getValueAt(row, 1);
        String patientName = (String) table.getValueAt(row,2);
        String patientAddress = (String) table.getValueAt(row, 5);

//        showing pop up of Accept reject
        int choice = JOptionPane.showOptionDialog(
                this,
                "Do you want to Accept OR Reject Donation Request from "+patientName+" ?",
                "Request",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[]{"Accept", "Reject"},
                "Accept"
        );

        JOptionPane.showMessageDialog(
                this,
                "Request successfully " + (choice == 0 ? "Accepted!" : "Rejected!")
        );

//        insert details of given row in donarDecision table
        String url = "jdbc:mysql://localhost:3306/bloodHelp";
        try(Connection con = DriverManager.getConnection(url , "root" , "Dee01$hetty"))
        {
            String sql = "INSERT INTO donarDecision(userID, patientID, patientName , patientAddress, status) VALUES(?,?,?,?,?)";
            try(PreparedStatement pst = con.prepareStatement(sql))
            {
                pst.setInt(1, userID);
                pst.setInt(2, patientID);
                pst.setString(3, patientName);
                pst.setString(4, patientAddress);

                if(choice == JOptionPane.YES_OPTION)  //choice = 0 --> Accepted
                {

                    prevActivePatient(userID);                    // purana jo Active hai osko find karo & make it Accepted
                    pst.setString(5, "Active");    // Now user is Active for this patient
                }
                else if(choice == JOptionPane.NO_OPTION)  //choice = 1 --> Rejected
                {
                    pst.setString(5,"Rejected");
                }

                pst.executeUpdate();
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    void prevActivePatient(int userID)
    {
        String url = "jdbc:mysql://localhost:3306/bloodHelp";
        try(Connection con = DriverManager.getConnection(url , "root" , "Dee01$hetty"))
        {
            // Find latest status = 'Active'
            String sql = "SELECT * FROM donarDecision WHERE userID=? AND status ='Active' ORDER BY decisionTime DESC LIMIT 1";
            try(PreparedStatement pst = con.prepareStatement(sql))
            {
                pst.setInt(1, userID);

                ResultSet rs = pst.executeQuery();
                if(rs.next())                         //  make it status = accepted as new one is active
                {
                    int patientID = rs.getInt("patientID");  // take out id for patient for whom user is active now
                    updateStatus(userID, patientID);                    // change that patient's status = Accepted  (from Active)
                }
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    void updateStatus(int userID, int patientID)
    {
        String url = "jdbc:mysql://localhost:3306/bloodHelp";
        try(Connection con = DriverManager.getConnection(url , "root" , "Dee01$hetty"))
        {
            String sql = "UPDATE donarDecision set status='Accepted' WHERE userID=? AND patientID=?";
            try(PreparedStatement pst = con.prepareStatement(sql))
            {
                pst.setInt(1, userID);
                pst.setInt(2,patientID);

                pst.executeUpdate();
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }


    public static void main(String[] args)
    {
        new donationRequest(11, "chai");
    }
}
