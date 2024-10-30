package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Doctors {
    private Connection conn;
    public Doctors(Connection conn){
        this.conn=conn;
    }
    //viewDoctor method start.
    public void viewDoctors(){
        String query="select * from doctors";
        try {
            PreparedStatement ps= conn.prepareStatement(query);
            ResultSet rs=ps.executeQuery();
            System.out.println("Doctors: ");
            System.out.println("+----------+-----------------------+----------------------+");
            System.out.println("|Id        |Name                   |Specialization        |");
            System.out.println("+----------+-----------------------+----------------------+");

            while (rs.next()){
                int id=rs.getInt("id");
                String name=rs.getString("name");
                String specialization=rs.getString("specialization");

                System.out.printf("|%-9s | %-21s | %-21s|\n",id,name,specialization);
                System.out.println("+----------+-----------------------+----------------------+");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    //viewDoctors method end.
    //checkDoctors method start.
    public boolean getDoctorById(int id){
        String query="SELECT * FROM Doctors WHERE id=?";
        try{
            PreparedStatement ps=conn.prepareStatement(query);
            ps.setInt(1,id);
            ResultSet rs=ps.executeQuery();
            if (rs.next()){
                return true;
            }
            else {
                return false;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
//checkDoctors method end.
}

