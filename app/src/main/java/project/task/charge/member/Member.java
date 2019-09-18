package project.task.charge.member;

public class Member {
    public String name;
    public String email;
    public String gender;
    public boolean hired;
    public String photo;
    public String address;
    public String location;
    public String official_phone;
    public String birthday;
    public String password;
    public String personal_phone;
    public String personal_email;
    public String designation;

    public Member(){

    }
    public Member(String name, String email, String gender, String photo, String birthday, String address, String location, String phone, String password){
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.photo = photo;
        this.address = address;
        this.location = location;
        this.official_phone = phone;
        this.birthday = birthday;
        this.password = password;
    }

    public String getName(){
        return this.name;
    }
    public String getEmail(){
        return this.email;
    }
    public String getGender(){
        return this.gender;
    }
    public boolean getStatus(){
        return this.hired;
    }
    public String getPhoto() {
        return photo;
    }
    public String getAddress() {
        return address;
    }
    public String getBirthday() {
        return birthday;
    }
    public String getLocation() {
        return location;
    }
    public String getOfficial_phone() {
        return official_phone;
    }
    public String getPassword() {
        return password;
    }
}
