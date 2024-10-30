package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
    private Connection conn;
    private Scanner sc;

    public Patient(Connection conn, Scanner sc){
        this.conn=conn;
        this.sc=sc;
    }
    //addPatient method start
    public void addPatient(){
        System.out.print("Enter Patient name: ");
        String name=sc.next();
        name=sc.nextLine();
        System.out.print("Enter Patient age: ");
        int age=sc.nextInt();
        System.out.print("Enter Patient Gender: ");
        String gender=sc.next();

        try {
            String query="INSERT INTO patients(name, age, gender) VALUES(?,?,?)";
            PreparedStatement ps=conn.prepareStatement(query);
            ps.setString(1,name);
            ps.setInt(2,age);
            ps.setString(3,gender);

            int affectedRows=ps.executeUpdate();
            if (affectedRows>0){
                System.out.println("Patient add successfully!!");
            }
            else {
                System.out.println("Failed to add Patient!!");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    //addPatient method end.

    //viewPatient method start.
    public void viewPatient(){
        String query="select * from patients";
        try {
            PreparedStatement ps= conn.prepareStatement(query);
            ResultSet rs=ps.executeQuery();
            System.out.println("Patients: ");
            System.out.println("+----------+-----------------------+----------+------------+");
            System.out.println("|Id        |Name                   |Age       |Gender      |");
            System.out.println("+----------+-----------------------+----------+------------+");

            while (rs.next()){
                int id=rs.getInt("id");
                String name=rs.getString("name");
                int age=rs.getInt("age");
                String gender=rs.getString("gender");

                System.out.printf("|%-9s | %-21s | %-8s | %-10s |\n",id,name,age,gender);
                System.out.println("+----------+-----------------------+----------+------------+");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    //viewPatient method end.
    //checkPatient method start.
    public boolean getPatientById(int id){
        String query="SELECT * FROM patients WHERE id=?";
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
//checkPatient method end.
}
