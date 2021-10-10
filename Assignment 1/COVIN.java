import java.util.*;

public class COVIN {
    private final HashMap<String, Vaccine> vaccineTracker;
    private final ArrayList<String> vaccineNames;
    private final HashMap<Integer, Hospital> hospitalTracker;
    private final HashMap<String, Citizen> citizenTracker;
    private int Hospital_Unique_ID;
    COVIN(){
        vaccineTracker = new HashMap<>();
        vaccineNames = new ArrayList<>();
        hospitalTracker = new HashMap<>();
        citizenTracker = new HashMap<>();
        Hospital_Unique_ID = 100000;
    }

    public static void main(String[] args) {
        COVIN covin = new COVIN();
        Scanner sc = new Scanner(System.in);
        System.out.println("CoWin Portal initialized....\n" +
                "---------------------------------");
        covin.printMenu();
        int option = sc.nextInt();
        while (option != 8){
            if (option == 1){
                sc.nextLine();
                System.out.print("Vaccine Name: ");
                String vaccine_name = sc.nextLine();
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
                covin.add_Vaccine(vaccine_name, number_of_doses, gap_between_doses);
                System.out.println("Vaccine Name: " + vaccine_name + ", Number of Doses: " + number_of_doses + ", Gap Between Doses: " + gap_between_doses);
                covin.printSectionBreaker();
            }
            else if (option == 2){
                sc.nextLine();
                System.out.print("Hospital Name: ");
                String hospital_name = sc.nextLine();
                System.out.print("PinCode: ");
                String PinCode = sc.nextLine();
                if (!covin.isPinCodeValid(PinCode)){
                    System.out.println("PinCode Entered is Invalid");
                }
                else{
                    int Unique_ID = covin.getHospital_Unique_ID();
                    covin.add_Hospital(hospital_name, PinCode, Unique_ID);
                    System.out.println("Hospital Name: " + hospital_name + ", PinCode: " + PinCode + ", Unique ID: " + Unique_ID);
                }
                covin.printSectionBreaker();
            }
            else if (option == 3){
                sc.nextLine();
                System.out.print("Citizen Name: ");
                String citizen_name = sc.nextLine();
                System.out.print("Age: ");
                int citizen_age = sc.nextInt();
                System.out.print("Unique ID: ");
                sc.nextLine();
                String citizen_unique_id = sc.nextLine();
                System.out.println("Citizen Name: " + citizen_name + ", Age: " + citizen_age + ", Unique ID: " + citizen_unique_id);
                if (!covin.isCitizenIDValid(citizen_unique_id)){
                    System.out.println("Invalid Unique ID: It must be a 12 Digit Number");
                }
                else{
                    if (!covin.isCitizenValid(citizen_age)){
                        System.out.println("Only above 18 are allowed");
                    }
                    else{
                        if (covin.citizenTracker.containsKey(citizen_unique_id)){
                            System.out.println("This Unique ID has already been taken");
                        }
                        else{
                            Citizen citizen = new Citizen(citizen_name, citizen_age, citizen_unique_id);
                            covin.citizenTracker.put(citizen_unique_id, citizen);
                        }
                    }
                }
                covin.printSectionBreaker();
            }
            else if (option == 4){
                System.out.print("Enter Hospital ID: ");
                int hospital_id = sc.nextInt();
                if (!covin.hospitalTracker.containsKey(hospital_id)){
                    System.out.println("Not a valid Hospital ID, Please Try Again");
                }
                else{
                    System.out.print("Enter number of Slots to be added: ");
                    int slot_count = sc.nextInt();
                    for (int slot_no = 0; slot_no < slot_count; slot_no++){
                        System.out.print("Enter Day Number: ");
                        int day_number = sc.nextInt();
                        System.out.print("Enter Quantity: ");
                        int quantity = sc.nextInt();
                        if (quantity < 1){
                            System.out.println("Please enter a quantity > 0");
                        }
                        else{
                            System.out.println("Select Vaccine: ");
                            for (int i = 0; i < covin.vaccineNames.size(); i++){
                                System.out.println(i + ". " + covin.vaccineNames.get(i));
                            }
                            int vaccine_number =  sc.nextInt();
                            String vaccine_Name = covin.vaccineNames.get(vaccine_number);
                            covin.add_Slot(day_number, vaccine_Name, quantity, hospital_id);
                            System.out.println("Slot added by Hospital " + hospital_id + " for Day: " + day_number + ", Available Quantity: " + quantity + " of Vaccine " + covin.vaccineNames.get(vaccine_number));
                        }
                    }
                }
                covin.printSectionBreaker();
            }
            else if (option == 5){
                System.out.print("Enter patient Unique ID: ");
                sc.nextLine();
                String patient_unique_id = sc.nextLine();
                System.out.println("""
                        1. Search by area
                        2. Search by Vaccine
                        3. Exit""");
                System.out.print("Enter option: ");
                int choice = sc.nextInt();
                if (choice == 1){
                    System.out.print("Enter Pincode: ");
                    sc.nextLine();
                    String pincode = sc.next();
                    for (Map.Entry<Integer, Hospital> entry: covin.hospitalTracker.entrySet()){
                        if (entry.getValue().getH_pincode().equals(pincode)) {
                            System.out.println(entry.getKey() + " " + entry.getValue().getH_name());
                        }
                    }
                    System.out.print("Enter hospital id: ");
                    int hosp_id = sc.nextInt();
                    boolean slotsPresent = false;
                    Citizen patient = covin.citizenTracker.get(patient_unique_id);
                    if (patient.isFullyVaccinated()){
                        System.out.println("Patient is already Fully Vaccinated");
                    }
                    else{
                        for (int i = 0; i < covin.hospitalTracker.get(hosp_id).getH_slots().size(); i++){
                            Slot temp = covin.hospitalTracker.get(hosp_id).getH_slots().get(i);
                            if (patient.isPartiallyVaccinated()){
                                if ((patient.getVaccine_name().equals(temp.getS_vaccine()) && (patient.nextDoseDate() <= temp.getS_day())))
                                {
                                    slotsPresent = true;
                                    System.out.println(i + "-> Day: " + temp.getS_day() + " Available Qty:" + temp.getS_quantity() + " Vaccine:" + temp.getS_vaccine());
                                }
                            }
                            else{
                                {
                                    slotsPresent = true;
                                    System.out.println(i + "-> Day: " + temp.getS_day() + " Available Qty:" + temp.getS_quantity() + " Vaccine:" + temp.getS_vaccine());
                                }
                            }
                        }
                        if (!slotsPresent){
                            System.out.println("No slots available");
                        }
                        else{
                            System.out.print("Choose Slot: ");
                            int chosenSlot = sc.nextInt();
                            Slot chosen = covin.hospitalTracker.get(hosp_id).getH_slots().get(chosenSlot);
                            chosen.reduceS_quantity();
                            String vaccine_chosen = chosen.getS_vaccine();

                            if (!covin.citizenTracker.get(patient_unique_id).isPartiallyVaccinated()){
                                covin.firstVaccination(patient_unique_id, vaccine_chosen, chosen);
                            }
                            else{
                                covin.citizenTracker.get(patient_unique_id).DoseDone(chosen.getS_day());
                            }

                            if (chosen.getS_quantity() == 0){
                                covin.hospitalTracker.get(hosp_id).getH_slots().remove(chosen);
                            }
                            System.out.println(covin.citizenTracker.get(patient_unique_id).getC_name() + " vaccinated with " + covin.citizenTracker.get(patient_unique_id).getVaccine_name());
                        }
                    }
                    covin.printSectionBreaker();
                }
                else if (choice == 2){
                    System.out.print("Enter Vaccine name: ");
                    sc.nextLine();
                    String Vaccine_name = sc.nextLine();
                    if (!covin.vaccineTracker.containsKey(Vaccine_name)){
                        System.out.println("No Such Vaccine Found");
                    }
                    else{
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
                        boolean slotsPresent = false;
                        Citizen patient = covin.citizenTracker.get(patient_unique_id);
                        if (patient.isFullyVaccinated()){
                            System.out.println("Patient is already Fully Vaccinated");
                        }
                        else{
                            for (int i = 0; i < covin.hospitalTracker.get(hosp_id).getH_slots().size(); i++){
                                Slot temp = covin.hospitalTracker.get(hosp_id).getH_slots().get(i);
                                if (patient.isPartiallyVaccinated()){
                                    if ((temp.getS_vaccine().equals(Vaccine_name)) && (patient.getVaccine_name().equals(temp.getS_vaccine()) && (patient.nextDoseDate() <= temp.getS_day())))
                                    {
                                        slotsPresent = true;
                                        System.out.println(i + "-> Day: " + temp.getS_day() + " Available Qty:" + temp.getS_quantity() + " Vaccine:" + temp.getS_vaccine());
                                    }
                                }
                                else{
                                    if (temp.getS_vaccine().equals(Vaccine_name))
                                    {
                                        slotsPresent = true;
                                        System.out.println(i + "-> Day: " + temp.getS_day() + " Available Qty:" + temp.getS_quantity() + " Vaccine:" + temp.getS_vaccine());
                                    }
                                }
                            }
                            if (!slotsPresent){
                                System.out.println("No slots available");
                            }
                            else{
                                System.out.print("Choose Slot: ");
                                int chosenSlot = sc.nextInt();
                                Slot chosen = covin.hospitalTracker.get(hosp_id).getH_slots().get(chosenSlot);
                                chosen.reduceS_quantity();
                                String vaccine_chosen = chosen.getS_vaccine();

                                if (!covin.citizenTracker.get(patient_unique_id).isPartiallyVaccinated()){
                                    covin.firstVaccination(patient_unique_id, vaccine_chosen, chosen);
                                }
                                else{
                                    covin.citizenTracker.get(patient_unique_id).DoseDone(chosen.getS_day());
                                }

                                if (chosen.getS_quantity() == 0){
                                    covin.hospitalTracker.get(hosp_id).getH_slots().remove(chosen);
                                }
                                System.out.println(covin.citizenTracker.get(patient_unique_id).getC_name() + " vaccinated with " + covin.citizenTracker.get(patient_unique_id).getVaccine_name());
                            }
                        }
                        covin.printSectionBreaker();
                    }
                }
                else if (choice == 3){
                    System.out.println("Exiting");
                }
                else{
                    System.out.println("Invalid Option Chosen");
                }
            }
            else if (option == 6){
                System.out.print("Enter Hospital Id: ");
                int hosp_id = sc.nextInt();
                if (!covin.hospitalTracker.containsKey(hosp_id)){
                    System.out.println("Not a Valid Hospital ID");
                }
                else{
                    ArrayList<Slot> hosp_slots = covin.hospitalTracker.get(hosp_id).getH_slots();
                    for (Slot hosp_slot : hosp_slots) {
                        System.out.println("Day: " + hosp_slot.getS_day() + " Vaccine: " + hosp_slot.getS_vaccine() + " Available Qty: " + hosp_slot.getS_quantity());
                    }
                }
                covin.printSectionBreaker();
            }
            else if (option == 7){
                System.out.print("Enter Patient ID: ");
                sc.nextLine();
                String patient_id = sc.nextLine();
                if (!covin.citizenTracker.containsKey(patient_id)){
                    System.out.println("No Patient with given Patient ID Exists");
                }
                else{
                    Citizen citizen = covin.citizenTracker.get(patient_id);
                    covin.printCitizenStatus(citizen);
                    if (citizen.isPartiallyVaccinated()){
                        System.out.println("Vaccine Given: " + citizen.getVaccine_name());
                        System.out.println("Number of Doses given: " + citizen.getDosesDone());
                        if (!citizen.isFullyVaccinated()){
                            System.out.println("Next Dose due date: " + citizen.nextDoseDate());
                        }
                    }
                }
                covin.printSectionBreaker();
            }
            covin.printMenu();
            option = sc.nextInt();
        }
        System.out.println("Thank You for Using our Program");
    }

    private void firstVaccination(String patient_unique_id, String vaccine_chosen, Slot chosen){
        citizenTracker.get(patient_unique_id).setDoses(vaccineTracker.get(vaccine_chosen).getV_dose_count());
        citizenTracker.get(patient_unique_id).setDosesPending(vaccineTracker.get(vaccine_chosen).getV_dose_count());
        citizenTracker.get(patient_unique_id).DoseDone(chosen.getS_day());
        citizenTracker.get(patient_unique_id).setVaccine_name(vaccine_chosen);
        citizenTracker.get(patient_unique_id).setVaccineGap(vaccineTracker.get(vaccine_chosen).getV_gap_between_doses());
    }

    private void add_Slot(int day_number, String vaccine_Name, int quantity, int hospital_id){
        Slot slot = new Slot(day_number, vaccine_Name, quantity, hospital_id);
        hospitalTracker.get(hospital_id).addH_slot(slot);
    }

    private void printCitizenStatus(Citizen citizen){
        if (!citizen.isPartiallyVaccinated()){
            System.out.println("Citizen REGISTERED");
        }
        else if (citizen.isFullyVaccinated()){
            System.out.println("FULLY VACCINATED");
        }
        else{
            System.out.println("PARTIALLY VACCINATED");
        }
    }
    private void printMenu(){
        System.out.println("""
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
    }

    private void printSectionBreaker(){
        System.out.println("---------------------------------");
    }

    private int getHospital_Unique_ID() {
        return Hospital_Unique_ID;
    }

    private void add_Vaccine(String vaccine_name, int number_of_doses, int gap_between_doses){
        Vaccine vaccine = new Vaccine(vaccine_name, number_of_doses, gap_between_doses);
        vaccineTracker.put(vaccine_name, vaccine);
        vaccineNames.add(vaccine_name);
    }

    private void add_Hospital(String hospital_name, String PinCode, int Unique_ID){
        Hospital hospital = new Hospital(hospital_name, PinCode, Unique_ID);
        hospitalTracker.put(Unique_ID, hospital);
        Hospital_Unique_ID++;
    }

    private boolean isCitizenIDValid(String citizen_unique_id){
        return (citizen_unique_id.length() == 12);
    }

    private boolean isCitizenValid(int age){
        return age >= 18;
    }

    private boolean isPinCodeValid(String pincode){
        return (pincode.length() == 6);
    }
}

class Citizen{
    private String c_name;
    private int c_age;
    private final String c_unique_id;
    private int doses;
    private int dosesPending;
    private String vaccine_name;
    private int vaccineGap;
    private boolean isPartiallyVaccinated = false;
    private boolean isFullyVaccinated = false;
    private int lastDoseDate;

    Citizen(String c_name, int c_age, String c_unique_id){
        this.c_name = c_name;
        this.c_age = c_age;
        this.c_unique_id = c_unique_id;
    }

    public int getDosesDone(){
        return this.doses - this.dosesPending;
    }

    public int nextDoseDate() {return this.lastDoseDate + this.vaccineGap;}

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

    public String getVaccine_name() {
        return vaccine_name;
    }
}

class Hospital{
    private final String h_name;
    private final String h_pincode;
    private final int h_unique_id;
    private final ArrayList<Slot> h_slots;

    Hospital(String h_name, String h_pincode, int h_unique_id){
        this.h_name = h_name;
        this.h_pincode = h_pincode;
        this.h_unique_id = h_unique_id;
        this.h_slots = new ArrayList<>();
    }

    public String getH_name() {
        return h_name;
    }

    public String getH_pincode() {
        return h_pincode;
    }

    public void addH_slot(Slot slot) { this.h_slots.add(slot); }

    public ArrayList<Slot> getH_slots() {
        return h_slots;
    }
}

class Vaccine{
    private final String v_name;
    private final int v_dose_count;
    private final int v_gap_between_doses;
    Vaccine(String v_name, int v_dose_count, int v_gap_between_doses){
        this.v_name = v_name;
        this.v_dose_count = v_dose_count;
        this.v_gap_between_doses = v_gap_between_doses;
    }

    public int getV_dose_count() {
        return v_dose_count;
    }

    public int getV_gap_between_doses() {
        return v_gap_between_doses;
    }
}

class Slot{
    private final int s_day;
    private final String s_vaccine;
    private int s_quantity;
    private final int s_hospital_id;
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
