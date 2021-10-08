import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class COVIN {
    static HashMap<String, Vaccine> vaccineTracker = new HashMap<>();
    static ArrayList<String> vaccineNames = new ArrayList<>();
    static HashMap<Integer, Hospital> hospitalTracker = new HashMap<>();
    static HashMap<Long, Citizen> citizenTracker = new HashMap<>();
    static HashMap<Integer, ArrayList<Slot>> slotTracker = new HashMap<Integer, ArrayList<Slot>>();
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
                vaccineNames.add(vaccine_name);
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
                hospitalTracker.put(Unique_ID, hospital);
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
                citizenTracker.put(citizen_unique_id, citizen);
                System.out.println("Citizen Name: " + citizen_name + ", Age: " + citizen_age + ", Unique ID: " + citizen_unique_id);
                System.out.println("---------------------------------");
            }
            else if (option == 4){
                System.out.print("Enter Hospital ID: ");
                int hospital_id = sc.nextInt();
                System.out.print("Enter number of Slots to be added: ");
                int slot_count = sc.nextInt();
                System.out.print("Enter Day Number: ");
                int day_number = sc.nextInt();
                System.out.print("Enter Quantity: ");
                int quantity = sc.nextInt();
                System.out.println("Select Vaccine: ");
                for (int i = 0; i < vaccineNames.size(); i++){
                    System.out.println(i + ". " + vaccineNames.get(i));
                }
                int vaccine_number =  sc.nextInt();
                String vaccine_Name = vaccineNames.get(vaccine_number);
                Slot slot = new Slot(day_number, vaccine_Name, quantity, hospital_id);
                boolean isHospIDPresent = slotTracker.containsKey(hospital_id);
                if (isHospIDPresent){
                    slotTracker.get(hospital_id).add(slot);
                }
                else{
                    slotTracker.put(hospital_id, new ArrayList<Slot>());
                    slotTracker.get(hospital_id).add(slot);
                }
                Hospital hosp = hospitalTracker.get(hospital_id);
                System.out.println("Slot added by Hospital " + hospital_id + " for Day: " + day_number + ", Available Quantity: " + quantity + " of Vaccine " + vaccineNames.get(vaccine_number));
                System.out.println("---------------------------------");
            }
            else if (option == 5){
                System.out.println("Enter patient Unique ID: ");
                long patient_unique_id = sc.nextLong();
                System.out.println("1. Search by area\n" +
                        "2. Search by Vaccine\n" +
                        "3. Exit");
                System.out.print("Enter option: ");
                int choice = sc.nextInt();
                if (choice == 1){
                    System.out.print("Enter Pincode: ");
                    int pincode = sc.nextInt();
                    for (Map.Entry<Integer, ArrayList<Slot>> entry: slotTracker.entrySet()){
                        for (int i = 0; i < entry.getValue().size(); i++){
                            if (hospitalTracker.get(entry.getValue().get(i).getS_hospital_id()).getH_pincode() == pincode){
                                System.out.println(entry.getValue().get(i).getS_hospital_id() + " " + hospitalTracker.get(entry.getValue().get(i).getS_hospital_id()).getH_name());
                            }
                        }
                    }
                    System.out.print("Enter hospital id: ");
                    int hosp_id = sc.nextInt();
                    for (int i = 0; i < slotTracker.get(hosp_id).size(); i++){
                        Slot temp = slotTracker.get(hosp_id).get(i);
                        System.out.println(i + "-> Day: " + temp.getS_day() + " Available Qty:" + temp.getS_quantity() + " Vaccine:" + temp.getS_vaccine());
                    }
                    System.out.print("Choose Slot: ");
                    int chosenSlot = sc.nextInt();
                    for (int i = 0; i < slotTracker.get(hosp_id).size(); i++){
                        if (i == chosenSlot){
                            slotTracker.get(hosp_id).get(i).reduceS_quantity();
                            String vaccine_chosen = slotTracker.get(hosp_id).get(i).getS_vaccine();
                            citizenTracker.get(patient_unique_id).setDoses(vaccineTracker.get(vaccine_chosen).getV_dose_count());
                            citizenTracker.get(patient_unique_id).setDosesPending(vaccineTracker.get(vaccine_chosen).getV_dose_count());
                            citizenTracker.get(patient_unique_id).DoseDone();
                            citizenTracker.get(patient_unique_id).setVaccine_name(vaccine_chosen);
                            citizenTracker.get(patient_unique_id).setVaccineGap(vaccineTracker.get(vaccine_chosen).getV_gap_between_doses());
                            if (slotTracker.get(hosp_id).get(i).getS_quantity() == 0){
                                slotTracker.get(hosp_id).remove(i);
                            }
                            break;
                        }
                    }
                    System.out.println(citizenTracker.get(patient_unique_id) + " vaccinated with Covax");
                    System.out.println("---------------------------------");
                }
                else if (choice == 2){
                    System.out.print("Enter Vaccine name: ");
                    String Vaccine_name = sc.next();
                    for (Map.Entry<Integer, ArrayList<Slot>> entry: slotTracker.entrySet()){
                        for (int i = 0; i < entry.getValue().size(); i++){
                            if (entry.getValue().get(i).getS_vaccine().equals(Vaccine_name)){
                                System.out.println(entry.getValue().get(i).getS_hospital_id() + " " + hospitalTracker.get(entry.getValue().get(i).getS_hospital_id()).getH_name());
                            }
                        }
                    }
                    System.out.print("Enter hospital id: ");
                    int hosp_id = sc.nextInt();
                    for (int i = 0; i < slotTracker.get(hosp_id).size(); i++){
                        Slot temp = slotTracker.get(hosp_id).get(i);
                        System.out.println(i + "-> Day: " + temp.getS_day() + " Available Qty:" + temp.getS_quantity() + " Vaccine:" + temp.getS_vaccine());
                    }
                    System.out.print("Choose Slot: ");
                    int chosenSlot = sc.nextInt();
                    for (int i = 0; i < slotTracker.get(hosp_id).size(); i++){
                        if (i == chosenSlot){
                            slotTracker.get(hosp_id).get(i).reduceS_quantity();
                            String vaccine_chosen = slotTracker.get(hosp_id).get(i).getS_vaccine();
                            citizenTracker.get(patient_unique_id).setDoses(vaccineTracker.get(vaccine_chosen).getV_dose_count());
                            citizenTracker.get(patient_unique_id).setDosesPending(vaccineTracker.get(vaccine_chosen).getV_dose_count());
                            citizenTracker.get(patient_unique_id).DoseDone();
                            citizenTracker.get(patient_unique_id).setVaccine_name(vaccine_chosen);
                            citizenTracker.get(patient_unique_id).setVaccineGap(vaccineTracker.get(vaccine_chosen).getV_gap_between_doses());
                            if (slotTracker.get(hosp_id).get(i).getS_quantity() == 0){
                                slotTracker.get(hosp_id).remove(i);
                            }
                            break;
                        }
                    }
                    System.out.println(citizenTracker.get(patient_unique_id) + " vaccinated with Covax");
                    System.out.println("---------------------------------");
                    System.out.println(citizenTracker.get(patient_unique_id) + " vaccinated with Covax");
                    System.out.println("---------------------------------");
                }

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
    private int doses;
    private int dosesPending;
    private String vaccine_name;
    private int vaccineGap;

    public void setVaccineGap(int vaccineGap) {
        this.vaccineGap = vaccineGap;
    }

    public void setVaccine_name(String vaccine_name) {
        this.vaccine_name = vaccine_name;
    }

    Citizen(String c_name, int c_age, long c_unique_id){
        this.c_name = c_name;
        this.c_age = c_age;
        this.c_unique_id = c_unique_id;
    }

    public void setDoses(int doses) {
        this.doses = doses;
    }

    public void setDosesPending(int doses) {
        this.dosesPending = doses;
    }

    public void DoseDone(){
        this.dosesPending--;
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

    public String getH_name() {
        return h_name;
    }

    public int getH_pincode() {
        return h_pincode;
    }

    public int getH_unique_id() {
        return h_unique_id;
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
    private String s_vaccine;
    private int s_quantity;
    private int s_hospital_id;
    Slot(int s_day, String s_vaccine, int s_quantity, int s_hospital_id){
        this.s_day = s_day;
        this.s_vaccine = s_vaccine;
        this.s_quantity = s_quantity;
        this.s_hospital_id = s_hospital_id;
    }

    public int getS_day() {
        return s_day;
    }

    public String getS_vaccine() {
        return s_vaccine;
    }

    public int getS_quantity() {
        return s_quantity;
    }

    public int getS_hospital_id() {
        return s_hospital_id;
    }

    public void reduceS_quantity() {
        this.s_quantity--;
    }
}
