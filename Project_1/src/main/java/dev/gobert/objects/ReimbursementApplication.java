package dev.gobert.objects;

public class ReimbursementApplication {
    private int rNum;
    private int eid;
    private long datetime;
    private String local;
    private String course;
    private int price;
    private String gradeForm;
    private String eventType;
    private String justif;
    private int missedHours;
    private String status;
    private String finalDecision;

    @Override
    public String toString() {
        return "Reimbursement Application" +
                "\n=========================" +
                "\nApplication Number: " + rNum +
                "\nEmployee Number:    " + eid +
                "\nDate/Time:          " + datetime +
                "\nLocation:           " + local +
                "\nCourse:             " + course +
                "\nPrice:              " + price +
                "\nGrade Format:       " + gradeForm +
                "\nEvent Type:         " + eventType +
                "\nJustification:      " + justif +
                "\nHours Missed:       " + missedHours +
                "\nStatus:             " + status +
                "\nFinal Decision:     " + finalDecision;
    }

    public ReimbursementApplication() {
    }

    public ReimbursementApplication(int rNum, int eid, long datetime, String local,
                                    String course, int price, String gradeForm, String eventType,
                                    String justif, int missedHours, String status, String finalDecision) {
        this.rNum = rNum;
        this.eid = eid;
        this.datetime = datetime;
        this.local = local;
        this.course = course;
        this.price = price;
        this.gradeForm = gradeForm;
        this.eventType = eventType;
        this.justif = justif;
        this.missedHours = missedHours;
        this.status = status;
        this.finalDecision = finalDecision;
    }

    public int getrNum() {
        return rNum;
    }

    public void setrNum(int rNum) {
        this.rNum = rNum;
    }

    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getGradeForm() {
        return gradeForm;
    }

    public void setGradeForm(String gradeForm) {
        this.gradeForm = gradeForm;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getJustif() {
        return justif;
    }

    public void setJustif(String justif) {
        this.justif = justif;
    }

    public int getMissedHours() {
        return missedHours;
    }

    public void setMissedHours(int missedHours) {
        this.missedHours = missedHours;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFinalDecision() { return finalDecision; }

    public void setFinalDecision(String finalDecision) { this.finalDecision = finalDecision; }
}
