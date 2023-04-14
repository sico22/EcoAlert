package hugsforbugs.ecoalert;

public class SponsorInformation {

    String name, phone;


    public SponsorInformation(){

    }


    public SponsorInformation(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName(){
        return name;
    }
    public String getPhone(){
        return phone;
    }
    private void setName(String name){
        this.name = name;
    }
    private void setPhone(String phone){
        this.phone = phone;
    }
}
