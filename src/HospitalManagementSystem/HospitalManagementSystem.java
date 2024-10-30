package HospitalManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class HospitalManagementSystem {
    private static final String url="jdbc:mysql://localhost:3306/hospital";
    private static final String username="root";
    private static final String password="abcd";

    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        Scanner sc=new Scanner(System.in);
        try{
            Connection conn= DriverManager.getConnection(url,username,password);
            Patient patient=new Patient(conn,sc);
            Doctors doctors=new Doctors(conn);

            while (true){
                System.out.println("HOSPITAL MANAGEMENT SYSTEM ");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patient");
                System.out.println("3. View Doctors");
                System.out.println("4. Book Appointment");
                System.out.println("5. Exit");
                System.out.println("Enter your choice: ");
                int choice=sc.nextInt();

                switch (choice){
                    case 1:
                        //Add Patient
                        patient.addPatient();
                        System.out.println();
                        break;
                    case 2:
                        //View Patient
                        patient.viewPatient();
                        System.out.println();
                        break;
                    case 3:
                        //View Doctors
                        doctors.viewDoctors();
                        System.out.println();
                        break;
                    case 4:
                        //Book Appointments
                        bookAppointment(patient, doctors,conn,sc);
                        System.out.println();
                        break;
                    case 5:
                        //Exit
                        System.out.println("THANK YOU FOR USING HOSPITAL MANAGEMENT SYSTEM!");
                        return;
                    default:
                        System.out.println("Enter valid choice:!!");
                        System.out.println();
                        break;
                }
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void bookAppointment(Patient patient, Doctors doctors, Connection conn, Scanner sc){
        System.out.print("Enter Patient Id: ");
        int patientId=sc.nextInt();
        System.out.print("Enter Doctor Id: ");
        int doctorId=sc.nextInt();
        System.out.print("Enter Appointment Date (YYYY-MM-DD): ");
        String appointmentDate=sc.next();
        if (patient.getPatientById(patientId) && doctors.getDoctorById(doctorId)){
            if (checkDoctorAvailability(doctorId, appointmentDate, conn)) {
                String appointmentQuery = "INSERT INTO appointments(patient_id, doctor_id, appointment_date) VALUES(?,?,?)";
                try {
                    PreparedStatement ps = conn.prepareStatement(appointmentQuery);
                    ps.setInt(1, patientId);
                    ps.setInt(2,doctorId);
                    ps.setString(3,appointmentDate);
                    int affectedRows=ps.executeUpdate();
                    if(affectedRows>0){
                        System.out.println("Appointment Booked!");
                    }
                    else {
                        System.out.println("Failed to Book appointment!");
                    }
                }
                catch (SQLException e){
                    e.printStackTrace();
                }
            }
            else {
                System.out.println("Doctor not available on this date!!");
            }
        }
        else {
            System.out.println("Either doctor or patient does not exist!!!");
        }
    }

    public static boolean checkDoctorAvailability(int doctorId, String appointmentDate, Connection conn){
        String query="SELECT COUNT(*) FROM appointments WHERE doctor_id=? AND appointment_date=?";
        try {
            PreparedStatement ps= conn.prepareStatement(query);
            ps.setInt(1,doctorId);
            ps.setString(2,appointmentDate);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                int count=rs.getInt(1);
                if (count==0){
                    return true;
                }
                else {
                    return false;
                }
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
