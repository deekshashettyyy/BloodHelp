import com.mysql.cj.x.protobuf.MysqlxPrepare;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrackRequest extends JFrame
{
    int userID;
    TrackRequest(int userID, String username)
    {
       this.userID = userID;

        Font f1 = new Font("Futura",Font.BOLD,40);
        Font f2 = new Font("Calibri",Font.PLAIN,22);

        JLabel title = new JLabel("Track Request" , JLabel.CENTER);

        title.setFont(f1);
        title.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        title.setForeground(Color.WHITE);
        title.setOpaque(true);
        title.setBackground(Color.RED);

        String[] columnNames = {"DecisionTime" ,"Patient Name", "Bloodgrp" ,"Address", "Donar Name" ,"Contact" ,"Status"};
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
                    new Receiver(this.userID , username);
                    dispose();
                }
        );

//        Fetch patientID for current ReceiverID from patient Table
//        Using List is better option then Array patientId is dynamic
        List<Integer> patientID = findPatientID(this.userID);


        // Fetch from donarDecision Table for that patient using patientID
        for(int i=0; i<patientID.size(); i++)
        {
            int currentPatientID = patientID.get(i);

            String url = "jdbc:mysql://localhost:3306/bloodHelp";
            try(Connection con = DriverManager.getConnection(url,"root","Dee01$hetty"))
            {
                String sql = "SELECT d.decisionTime, d.patientName, d.patientAddress, d.status, u.name, u.phone , p.bloodgrp " +
                        "FROM donardecision d " +
                        "JOIN users u ON d.userID = u.userID " +
                        "JOIN patient p ON p.patientID = d.patientID "+
                        "WHERE d.patientID = ?  AND d.status IN ('Active', 'Accepted') " +
                        "ORDER BY d.decisionTime";


                try(PreparedStatement pst = con.prepareStatement(sql))
                {
                    pst.setInt(1, currentPatientID);

                    ResultSet rs = pst.executeQuery();

                    if(rs.next())
                    {
                        String decisionTime = rs.getString("decisionTime");
                        String patientName = rs.getString("patientName");
                        String patientBloodgrp = rs.getString("bloodgrp");
                        String patientAddress = rs.getString("patientAddress");
                        String status = rs.getString("status");
                        String donarName = rs.getString("name");
                        String donarPhone = rs.getString("phone");

                      tableModel.addRow(new Object[]{decisionTime, patientName, patientBloodgrp ,patientAddress ,donarName, donarPhone , status});

                      // Delete this patient request from patient table as one donar has accepted to donate for that patient
//                        deletePatient(currentPatientID);
                    }
                }
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(null,e.getMessage());
            }
        }



        setSize(1600, 1000);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("BloodHelp");
    }

    List<Integer> findPatientID(int receiverID)
    {
        List<Integer> patientList = new ArrayList<>();

        String url = "jdbc:mysql://localhost:3306/bloodHelp";
        try(Connection con = DriverManager.getConnection(url,"root","Dee01$hetty"))
        {
            String sql = "SELECT * FROM patient where receiverID=? ORDER BY requestTime DESC";
            try(PreparedStatement pst = con.prepareStatement(sql))
            {
                pst.setInt(1, receiverID);

                ResultSet rs = pst.executeQuery();

                while(rs.next())
                {
                    patientList.add(rs.getInt("patientID"));
                }
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,e.getMessage());
        }

        return patientList;
    }

    void deletePatient(int patientID)
    {
        String url = "jdbc:mysql://localhost:3306/bloodHelp";
        try(Connection con = DriverManager.getConnection(url, "root" ,"Dee01$hetty"))
        {
            String sql = "DELETE FROM patient WHERE patientID=?";
            try(PreparedStatement pst = con.prepareStatement(sql))
            {
                pst.setInt(1, patientID);

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
        new TrackRequest(12 , "dee");
    }
}
