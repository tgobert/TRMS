package dev.gobert.repositories;

import dev.gobert.objects.Employee;
import dev.gobert.objects.ReimbursementApplication;

import java.util.List;

public interface TuitionRepo {

    Employee getEmployee(int id);

    Employee getEmployeeByEmail(String input);

    List<ReimbursementApplication> getEmpRequests(Employee e);

    ReimbursementApplication employeeRequestAdd(Employee emp, ReimbursementApplication ra);

    double getTuition(Employee emp);

    ReimbursementApplication postGrade(int reimnum, String grade);

    List<ReimbursementApplication> getAwaiting(Employee emp);

    ReimbursementApplication approve(Employee emp, ReimbursementApplication rnum);

    ReimbursementApplication deny(Employee emp, ReimbursementApplication rnum);

    ReimbursementApplication moreInfo(ReimbursementApplication rnum);

    ReimbursementApplication respond(Employee emp, ReimbursementApplication rnum);
}
