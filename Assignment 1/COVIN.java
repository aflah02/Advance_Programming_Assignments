public class COVIN {
    public static void main(String[] args) {

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
