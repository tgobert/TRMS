package dev.gobert.main;

public class CommandJson {

    private String command;

    private int reimnum;

    private String rNum;

    public CommandJson() {    }

    public CommandJson(int reim_num) { this.reimnum = reim_num; }

    public CommandJson(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public int getReim_num() { return reimnum; }

    public void setReim_num(int reim_num) { this.reimnum = reim_num; }

    public String getrNum() {
        return rNum;
    }

    public void setrNum(String rNum) {
        this.rNum = rNum;
    }


}
