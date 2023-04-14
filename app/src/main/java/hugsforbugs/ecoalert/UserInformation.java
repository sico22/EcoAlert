package hugsforbugs.ecoalert;

public class UserInformation {
    String name, phone;


    public UserInformation(){

    }


    public UserInformation(String name, String phone) {
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
