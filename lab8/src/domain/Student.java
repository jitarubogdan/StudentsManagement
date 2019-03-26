package domain;

public class Student implements HasID<String>{
    private String id;
    private String nume;
    private String grupa;
    private String email;


    public Student(String id, String nume, String grupa, String email) {
        this.id = id;
        this.nume = nume;
        this.grupa = grupa;
        this.email = email;
    }

    public Student(){
    }

    @Override
    public String toString() {
        return "id=" + id + "|" + "nume=" + nume + "|" + "grupa=" + grupa + "|" + "email=" + email;
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public void setID(String id) {
        this.id = id;
    }

    public void execute(){}

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getGrupa() {
        return grupa;
    }

    public void setGrupa(String grupa) {
        this.grupa = grupa;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
