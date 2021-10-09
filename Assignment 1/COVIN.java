import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class COVIN {
    private HashMap<String, Vaccine> vaccineTracker;
    private ArrayList<String> vaccineNames;
    private HashMap<Integer, Hospital> hospitalTracker;
    private HashMap<Long, Citizen> citizenTracker;
//    private HashMap<Integer, ArrayList<Slot>> slotTracker;
    private int Hospital_Unique_ID;

    COVIN(){
        vaccineTracker = new HashMap<>();
        vaccineNames = new ArrayList<>();
        hospitalTracker = new HashMap<>();
        citizenTracker = new HashMap<>();
//        slotTracker = new HashMap<>();
        Hospital_Unique_ID = 100000;
    }

    public static void main(String[] args) {
        COVIN covin = new COVIN();
        Scanner sc = new Scanner(System.in);
        System.out.println("""
                CoWin Portal initialized....
                ---------------------------------
                1. Add Vaccine
                2. Register Hospital
                3. Register Citizen
                4. Add Slot for Vaccination
                5. Book Slot for Vaccination
                6. List all slots for a hospital
                7. Check Vaccination Status
                8. Exit
                ---------------------------------""");
        System.out.print("Please choose one of the tasks above: ");
        int option = sc.nextInt();
        System.out.println();
        while (option != 8){
            if (option == 1){
                System.out.print("Vaccine Name: ");
                String vaccine_name = sc.next();
                System.out.print("Number of Doses: ");
                int number_of_doses = sc.nextInt();
                int gap_between_doses;
                if (number_of_doses >= 2){
                    System.out.print("Gap between Doses: ");
                    gap_between_doses = sc.nextInt();
                }
                else {
                    gap_between_doses = 0;
                }
                Vaccine vaccine = new Vaccine(vaccine_name, number_of_doses, gap_between_doses);
                covin.vaccineTracker.put(vaccine_name, vaccine);
                covin.vaccineNames.add(vaccine_name);
                System.out.println("Vaccine Name: " + vaccine_name + ", Number of Doses: " + number_of_doses + ", Gap Between Doses: " + gap_between_doses);
                System.out.println("---------------------------------");
            }
            else if (option == 2){
                System.out.print("Hospital Name: ");
                String hospital_name = sc.next();
                System.out.print("PinCode: ");
                int PinCode = sc.nextInt();
                int Unique_ID = covin.getHospital_Unique_ID();
                covin.New_Hospital_Added();
                Hospital hospital = new Hospital(hospital_name, PinCode, Unique_ID);
                covin.hospitalTracker.put(Unique_ID, hospital);
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
                System.out.println("Citizen Name: " + citizen_name + ", Age: " + citizen_age + ", Unique ID: " + citizen_unique_id);
                if (citizen_age < 18){
                    System.out.println("Only above 18 are allowed");
                }
                else{
                    Citizen citizen = new Citizen(citizen_name, citizen_age, citizen_unique_id);
                    covin.citizenTracker.put(citizen_unique_id, citizen);
                }
                System.out.println("---------------------------------");
            }
            else if (option == 4){
                System.out.print("Enter Hospital ID: ");
                int hospital_id = sc.nextInt();
                System.out.print("Enter number of Slots to be added: ");
                int slot_count = sc.nextInt();
                for (int slot_no = 0; slot_no < slot_count; slot_no++){
                    System.out.print("Enter Day Number: ");
                    int day_number = sc.nextInt();
                    System.out.print("Enter Quantity: ");
                    int quantity = sc.nextInt();
                    System.out.println("Select Vaccine: ");
                    for (int i = 0; i < covin.vaccineNames.size(); i++){
                        System.out.println(i + ". " + covin.vaccineNames.get(i));
                    }
                    int vaccine_number =  sc.nextInt();
                    String vaccine_Name = covin.vaccineNames.get(vaccine_number);
                    Slot slot = new Slot(day_number, vaccine_Name, quantity, hospital_id);
                    covin.hospitalTracker.get(hospital_id).addH_slot(slot);
//                    boolean isHospIDPresent = covin.slotTracker.containsKey(hospital_id);
//                    if (!isHospIDPresent) {
//                        covin.slotTracker.put(hospital_id, new ArrayList<Slot>());
//                    }
//                    covin.slotTracker.get(hospital_id).add(slot);
                    Hospital hosp = covin.hospitalTracker.get(hospital_id);
                    System.out.println("Slot added by Hospital " + hospital_id + " for Day: " + day_number + ", Available Quantity: " + quantity + " of Vaccine " + covin.vaccineNames.get(vaccine_number));
                }
                System.out.println("---------------------------------");
            }
            else if (option == 5){
                System.out.println("Enter patient Unique ID: ");
                long patient_unique_id = sc.nextLong();
                // handle exit
                System.out.println("""
                        1. Search by area
                        2. Search by Vaccine
                        3. Exit""");
                System.out.print("Enter option: ");
                int choice = sc.nextInt();
                if (choice == 1){
                    System.out.print("Enter Pincode: ");
                    int pincode = sc.nextInt();
                    for (Map.Entry<Integer, Hospital> entry: covin.hospitalTracker.entrySet()){
                        if (entry.getValue().getH_pincode() == pincode) {
                            System.out.println(entry.getKey() + " " + entry.getValue().getH_name());
                        }
                    }
                    System.out.print("Enter hospital id: ");
                    int hosp_id = sc.nextInt();
//                    for (int i = 0; i < covin.slotTracker.get(hosp_id).size(); i++){
//                        Slot temp = covin.slotTracker.get(hosp_id).get(i);
//                        System.out.println(i + "-> Day: " + temp.getS_day() + " Available Qty:" + temp.getS_quantity() + " Vaccine:" + temp.getS_vaccine());
//                    }
                    boolean slotsPresent = false;
                    for (int i = 0; i < covin.hospitalTracker.get(hosp_id).getH_slots().size(); i++){
                        slotsPresent = true;
                        Slot temp = covin.hospitalTracker.get(hosp_id).getH_slots().get(i);
                        System.out.println(i + "-> Day: " + temp.getS_day() + " Available Qty:" + temp.getS_quantity() + " Vaccine:" + temp.getS_vaccine());
                    }
                    if (!slotsPresent){
                        System.out.println("No slots available");
                    }
                    System.out.print("Choose Slot: ");
                    int chosenSlot = sc.nextInt();
//                    for (int i = 0; i < covin.slotTracker.get(hosp_id).size(); i++){
//                        Slot temp = covin.slotTracker.get(hosp_id).get(i);
//                    }
                    Slot chosen = covin.hospitalTracker.get(hosp_id).getH_slots().get(chosenSlot);
                    chosen.reduceS_quantity();
                    String vaccine_chosen = chosen.getS_vaccine();
                    covin.citizenTracker.get(patient_unique_id).setDoses(covin.vaccineTracker.get(vaccine_chosen).getV_dose_count());
                    covin.citizenTracker.get(patient_unique_id).setDosesPending(covin.vaccineTracker.get(vaccine_chosen).getV_dose_count());
                    covin.citizenTracker.get(patient_unique_id).DoseDone(chosen.getS_day());
                    covin.citizenTracker.get(patient_unique_id).setVaccine_name(vaccine_chosen);
                    covin.citizenTracker.get(patient_unique_id).setVaccineGap(covin.vaccineTracker.get(vaccine_chosen).getV_gap_between_doses());
                    if (chosen.getS_quantity() == 0){
                        covin.hospitalTracker.get(hosp_id).getH_slots().remove(chosen);
                    }
//                    for (int i = 0; i < covin.slotTracker.get(hosp_id).size(); i++){
//                        if (i == chosenSlot){
//                            covin.slotTracker.get(hosp_id).get(i).reduceS_quantity();
//                            covin.hospitalTracker.get(hosp_id)
//                            String vaccine_chosen = covin.slotTracker.get(hosp_id).get(i).getS_vaccine();
//                            covin.citizenTracker.get(patient_unique_id).setDoses(covin.vaccineTracker.get(vaccine_chosen).getV_dose_count());
//                            covin.citizenTracker.get(patient_unique_id).setDosesPending(covin.vaccineTracker.get(vaccine_chosen).getV_dose_count());
//                            covin.citizenTracker.get(patient_unique_id).DoseDone();
//                            covin.citizenTracker.get(patient_unique_id).setVaccine_name(vaccine_chosen);
//                            covin.citizenTracker.get(patient_unique_id).setVaccineGap(covin.vaccineTracker.get(vaccine_chosen).getV_gap_between_doses());
//                            if (covin.slotTracker.get(hosp_id).get(i).getS_quantity() == 0){
//                                covin.slotTracker.get(hosp_id).remove(i);
//                            }
//                            break;
//                        }
//                    }
                    System.out.println(covin.citizenTracker.get(patient_unique_id).getC_name() + " vaccinated with " + covin.citizenTracker.get(patient_unique_id).getVaccine_name());
                    System.out.println("---------------------------------");
                }
                else if (choice == 2){
                    System.out.print("Enter Vaccine name: ");
                    String Vaccine_name = sc.next();
//                    for (Map.Entry<Integer, ArrayList<Slot>> entry: covin.slotTracker.entrySet()){
//                        for (int i = 0; i < entry.getValue().size(); i++){
//                            if (entry.getValue().get(i).getS_vaccine().equals(Vaccine_name)){
//                                System.out.println(entry.getValue().get(i).getS_hospital_id() + " " + covin.hospitalTracker.get(entry.getValue().get(i).getS_hospital_id()).getH_name());
//                            }
//                        }
//                    }
                    for (Map.Entry<Integer, Hospital> entry: covin.hospitalTracker.entrySet()){
                        for (int i = 0; i < entry.getValue().getH_slots().size(); i++){
                            if (entry.getValue().getH_slots().get(i).getS_vaccine().equals(Vaccine_name)){
                                System.out.println(entry.getValue().getH_slots().get(i).getS_hospital_id() + " " + covin.hospitalTracker.get(entry.getValue().getH_slots().get(i).getS_hospital_id()).getH_name());
                                break;
                            }
                        }
                    }
                    System.out.print("Enter hospital id: ");
                    int hosp_id = sc.nextInt();
                    // PRINT SLOTS FOR THE HOSPITAL ID GIVEN
//                    for (int i = 0; i < covin.slotTracker.get(hosp_id).size(); i++){
//                        Slot temp = covin.slotTracker.get(hosp_id).get(i);
//                        System.out.println(i + "-> Day: " + temp.getS_day() + " Available Qty:" + temp.getS_quantity() + " Vaccine:" + temp.getS_vaccine());
//                    }
                    System.out.print("Choose Slot: ");
                    int chosenSlot = sc.nextInt();
                    Slot chosen = covin.hospitalTracker.get(hosp_id).getH_slots().get(chosenSlot);
                    chosen.reduceS_quantity();
                    String vaccine_chosen = chosen.getS_vaccine();
                    covin.citizenTracker.get(patient_unique_id).setDoses(covin.vaccineTracker.get(vaccine_chosen).getV_dose_count());
                    covin.citizenTracker.get(patient_unique_id).setDosesPending(covin.vaccineTracker.get(vaccine_chosen).getV_dose_count());
                    covin.citizenTracker.get(patient_unique_id).DoseDone(chosen.getS_day());
                    covin.citizenTracker.get(patient_unique_id).setVaccine_name(vaccine_chosen);
                    covin.citizenTracker.get(patient_unique_id).setVaccineGap(covin.vaccineTracker.get(vaccine_chosen).getV_gap_between_doses());
                    if (chosen.getS_quantity() == 0){
                        covin.hospitalTracker.get(hosp_id).getH_slots().remove(chosen);
                    }
//                    for (int i = 0; i < covin.slotTracker.get(hosp_id).size(); i++){
//                        if (i == chosenSlot){
//                            covin.slotTracker.get(hosp_id).get(i).reduceS_quantity();
//                            String vaccine_chosen = covin.slotTracker.get(hosp_id).get(i).getS_vaccine();
//                            covin.citizenTracker.get(patient_unique_id).setDoses(covin.vaccineTracker.get(vaccine_chosen).getV_dose_count());
//                            covin.citizenTracker.get(patient_unique_id).setDosesPending(covin.vaccineTracker.get(vaccine_chosen).getV_dose_count());
//                            covin.citizenTracker.get(patient_unique_id).DoseDone();
//                            covin.citizenTracker.get(patient_unique_id).setVaccine_name(vaccine_chosen);
//                            covin.citizenTracker.get(patient_unique_id).setVaccineGap(covin.vaccineTracker.get(vaccine_chosen).getV_gap_between_doses());
//                            if (covin.slotTracker.get(hosp_id).get(i).getS_quantity() == 0){
//                                covin.slotTracker.get(hosp_id).remove(i);
//                            }
//                            break;
//                        }
//                    }
                    System.out.println(covin.citizenTracker.get(patient_unique_id).getC_name() + " vaccinated with " + covin.citizenTracker.get(patient_unique_id).getVaccine_name());
                    System.out.println("---------------------------------");
                }
            }
            else if (option == 6){
                System.out.print("Enter Hospital Id: ");
                int hosp_id = sc.nextInt();
                ArrayList<Slot> hosp_slots = covin.hospitalTracker.get(hosp_id).getH_slots();
                for (int i = 0; i < hosp_slots.size(); i++){
                    System.out.println("Day: " + hosp_slots.get(i).getS_day() + " Vaccine: " + hosp_slots.get(i).getS_vaccine() + " Available Qty: " + hosp_slots.get(i).getS_quantity());
                }
                System.out.println("---------------------------------");
             }
            else if (option == 7){
                System.out.println("Enter Patient ID: ");
                long patient_id = sc.nextLong();
                Citizen citizen = covin.citizenTracker.get(patient_id);
                if (!citizen.isPartiallyVaccinated()){
                    System.out.println("REGISTERED");
                }
                else if (citizen.isFullyVaccinated()){
                    System.out.println("FULLY VACCINATED");
                }
                else{
                    System.out.println("PARTIALLY VACCINATED");
                }
                if (citizen.isPartiallyVaccinated()){
                    System.out.println("Vaccine Given: " + citizen.getVaccine_name());
                    System.out.println("Number of Doses given: " + citizen.getDosesDone());
                    if (!citizen.isFullyVaccinated()){
                        System.out.println("Next Dose due date: " + citizen.getLastDoseDate() + citizen.getVaccineGap());
                    }
                }
            }
            System.out.print("Please choose one of the tasks above: ");
            option = sc.nextInt();
        }
        System.out.println("Thank You for Using our Program");
    }

    private int getHospital_Unique_ID() {
        return Hospital_Unique_ID;
    }

    private void New_Hospital_Added() {
        Hospital_Unique_ID++;
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
    private boolean isPartiallyVaccinated = false;
    private boolean isFullyVaccinated = false;
    private int lastDoseDate;

    Citizen(String c_name, int c_age, long c_unique_id){
        this.c_name = c_name;
        this.c_age = c_age;
        this.c_unique_id = c_unique_id;
    }

    public int getDosesDone(){
        return this.doses - this.dosesPending;
    }

    public void setVaccineGap(int vaccineGap) {
        this.vaccineGap = vaccineGap;
    }

    public void setVaccine_name(String vaccine_name) {
        this.vaccine_name = vaccine_name;
    }

    public void setDoses(int doses) {
        this.doses = doses;
    }

    public void setDosesPending(int doses) {
        this.dosesPending = doses;
    }

    public void DoseDone(int day){
        this.dosesPending--;
        this.lastDoseDate = day;
        this.isPartiallyVaccinated = true;
        if (this.dosesPending == 0){
            this.isFullyVaccinated = true;
        }
    }

    public boolean isPartiallyVaccinated(){
        return this.isPartiallyVaccinated;
    }

    public boolean isFullyVaccinated(){
        return this.isFullyVaccinated;
    }

    public String getC_name() {
        return c_name;
    }

    public int getC_age() {
        return c_age;
    }

    public long getC_unique_id() {
        return c_unique_id;
    }

    public String getVaccine_name() {
        return vaccine_name;
    }

    public int getVaccineGap() { return vaccineGap; }

    public int getLastDoseDate() { return lastDoseDate; }
}

class Hospital{
    private String h_name;
    private int h_pincode;
    private int h_unique_id;
    private ArrayList<Slot> h_slots;

    Hospital(String h_name, int h_pincode, int h_unique_id){
        this.h_name = h_name;
        this.h_pincode = h_pincode;
        this.h_unique_id = h_unique_id;
        this.h_slots = new ArrayList<>();
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

    public void addH_slot(Slot slot) { this.h_slots.add(slot); }

    public ArrayList<Slot> getH_slots() {
        return h_slots;
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
