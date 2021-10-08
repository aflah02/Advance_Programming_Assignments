import java.util.HashMap;
import java.util.Scanner;

public class COVIN {
    static HashMap<String, Vaccine> vaccineTracker = new HashMap<>();
    static HashMap<String, Hospital> hospitalTracker = new HashMap<>();
    static HashMap<String, Citizen> citizenTracker = new HashMap<>();
    private static int Hospital_Unique_ID = 100000;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("CoWin Portal initialized....\n" +
                "---------------------------------\n" +
                "1. Add Vaccine\n" +
                "2. Register Hospital\n" +
                "3. Register Citizen\n" +
                "4. Add Slot for Vaccination\n" +
                "5. Book Slot for Vaccination\n" +
                "6. List all slots for a hospital\n" +
                "7. Check Vaccination Status\n" +
                "8. Exit\n" +
                "---------------------------------");
        System.out.print("Please choose one of the tasks above: ");
        int option = sc.nextInt();
        System.out.println();
        while (option != 8){
            if (option == 1){
                System.out.print("Vaccine Name: ");
                String vaccine_name = sc.next();
                System.out.print("Number of Doses: ");
                int number_of_doses = sc.nextInt();
                System.out.print("Gap between Doses: ");
                int gap_between_doses = sc.nextInt();
                Vaccine vaccine = new Vaccine(vaccine_name, number_of_doses, gap_between_doses);
                vaccineTracker.put(vaccine_name, vaccine);
                System.out.println("Vaccine Name: " + vaccine_name + ", Number of Doses: " + number_of_doses + ", Gap Between Doses: " + gap_between_doses);
                System.out.println("---------------------------------");
            }
            else if (option == 2){
                System.out.print("Hospital Name: ");
                String hospital_name = sc.next();
                System.out.print("PinCode: ");
                int PinCode = sc.nextInt();
                int Unique_ID = Hospital_Unique_ID;
                Hospital_Unique_ID ++;
                Hospital hospital = new Hospital(hospital_name, PinCode, Unique_ID);
                hospitalTracker.put(hospital_name, hospital);
                System.out.println("Hospital Name: " + hospital_name + ", PinCode: " + PinCode + ", Unique ID: " + Unique_ID);
                System.out.println("---------------------------------");
            }
            else if (option == 3){
                System.out.print("Citizen Name: ");
                String citizen_name = sc.next();
                System.out.print("Age: ");
                int citizen_age = sc.nextInt();
                System.out.print("Unique ID: ");
                long citizen_unique_id = sc.nextLong();
                Citizen citizen = new Citizen(citizen_name, citizen_age, citizen_unique_id);
                citizenTracker.put(citizen_name, citizen);
                System.out.println("Citizen Name: " + citizen_name + ", Age: " + citizen_age + ", Unique ID: " + citizen_unique_id);
                System.out.println("---------------------------------");
            }
            System.out.print("Please choose one of the tasks above: ");
            option = sc.nextInt();
        }
        System.out.println("Thank You for Using our Program");
    }

    public static int getHospital_Unique_ID() {
        return Hospital_Unique_ID;
    }
}
class Citizen{
    private String c_name;
    private int c_age;
    private long c_unique_id;
    Citizen(String c_name, int c_age, long c_unique_id){
        this.c_name = c_name;
        this.c_age = c_age;
        this.c_unique_id = c_unique_id;
    }
}
class Hospital{
    private String h_name;
    private int h_pincode;
    private int h_unique_id;
    Hospital(String h_name, int h_pincode, int h_unique_id){
        this.h_name = h_name;
        this.h_pincode = h_pincode;
        this.h_unique_id = h_unique_id;
    }
}
class Vaccine{
    private String v_name;
    private int v_dose_count;
    private int v_gap_between_doses;
    Vaccine(String v_name, int v_dose_count, int v_gap_between_doses){
        this.v_name = v_name;
        this.v_dose_count = v_dose_count;
        this.v_gap_between_doses = v_gap_between_doses;
    }

    public String getV_name() {
        return v_name;
    }

    public int getV_dose_count() {
        return v_dose_count;
    }

    public int getV_gap_between_doses() {
        return v_gap_between_doses;
    }
}
class Slot{
    private int s_day;
    private Vaccine s_vaccine;
    private int s_quantity;
    private int s_hospital_id;
    Slot(int s_day, Vaccine s_vaccine, int s_quantity, int s_hospital_id){
        this.s_day = s_day;
        this.s_vaccine = s_vaccine;
        this.s_quantity = s_quantity;
        this.s_hospital_id = s_hospital_id;
    }
}
