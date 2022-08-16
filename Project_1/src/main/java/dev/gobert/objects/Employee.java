package dev.gobert.objects;

public class Employee {
    private int eid;
    private String namefl;
    private String email;
    private String jobtitle;

    @Override
    public String toString() {
        return "Employee Information " +
                "\n====================" +
                "\nEmployee ID:  " + eid +
                "\nFull Name:    " + namefl +
                "\nEmail:        " + email +
                "\nJobtitle:     " + jobtitle;
    }

    public Employee() {
    }

    public Employee(int eid, String namefl, String email, String jobtitle, int tuitionLeft) {
        this.eid = eid;
        this.namefl = namefl;
        this.email = email;
        this.jobtitle = jobtitle;
    }

    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    public String getNamefl() {
        return namefl;
    }

    public void setNamefl(String namefl) {
        this.namefl = namefl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJobtitle() {
        return jobtitle;
    }

    public void setJobtitle(String jobtitle) {
        this.jobtitle = jobtitle;
    }

}
