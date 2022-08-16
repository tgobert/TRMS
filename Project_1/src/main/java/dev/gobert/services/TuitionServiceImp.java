package dev.gobert.services;

import dev.gobert.objects.Employee;
import dev.gobert.objects.ReimbursementApplication;
import dev.gobert.repositories.TuitionRepo;

import java.util.List;

public class TuitionServiceImp implements TuitionService{

    TuitionRepo tr;

    public TuitionServiceImp(TuitionRepo tr) { this.tr = tr; }

    @Override
    public Employee getEmployee(int id) {
        return tr.getEmployee(id);
    }

    @Override
    public Employee getEmployeeByEmail(String input) {
        return tr.getEmployeeByEmail(input);
    }

    @Override
    public List<ReimbursementApplication> getEmpRequests(Employee e) {
        return tr.getEmpRequests(e);
    }

    @Override
    public ReimbursementApplication employeeRequestAdd(Employee emp, ReimbursementApplication ra) {
        return tr.employeeRequestAdd(emp, ra); }

    @Override
    public double getTuition(Employee emp) {
        return tr.getTuition(emp);
    }

    @Override
    public ReimbursementApplication postGrade(int reimnum, String grade) {
        return tr.postGrade(reimnum, grade);
    }

    @Override
    public List<ReimbursementApplication> getAwaiting(Employee emp) { return tr.getAwaiting(emp); }

    @Override
    public ReimbursementApplication approve(Employee emp, ReimbursementApplication rnum) {
        return tr.approve(emp, rnum);
    }

    @Override
    public ReimbursementApplication deny(Employee emp, ReimbursementApplication rnum) {
        return tr.deny(emp, rnum);
    }

    @Override
    public ReimbursementApplication moreInfo(ReimbursementApplication rnum) {
        return tr.moreInfo(rnum);
    }

    @Override
    public ReimbursementApplication respond(Employee emp, ReimbursementApplication rnum) {
        return tr.respond(emp, rnum);
    }


}
