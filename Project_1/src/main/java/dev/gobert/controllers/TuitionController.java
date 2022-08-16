package dev.gobert.controllers;

import com.google.gson.Gson;
import dev.gobert.objects.Employee;
import dev.gobert.objects.ReimbursementApplication;
import dev.gobert.services.TuitionService;
import io.javalin.http.Handler;

import java.util.List;

import static java.lang.Integer.parseInt;

public class TuitionController {



    TuitionService ts;
    Gson gson = new Gson();

    public TuitionController(TuitionService ts) { this.ts = ts; }

    public Handler getEmployeeByEmail = ctx -> {
        String input = ctx.queryParam("email");
        Employee emp = ts.getEmployeeByEmail(input);
        if(emp != null) {
            ctx.result(gson.toJson(emp));
            ctx.status(200);
        } else {
            ctx.status(404);
        }
    };

    public Handler getEmployee = ctx -> {
        String input = ctx.pathParam("id");
        int id;
        try{
            id = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            id = -1;
        }
        Employee emp = ts.getEmployee(id);
        if(emp != null) {
            ctx.result(gson.toJson(emp));
            ctx.status(200);
        } else {
            ctx.status(404);
        }
    };

    public Handler employeeDash = ctx -> {

    };

    public Handler getEmpRequests = ctx -> {
        String input = ctx.pathParam("id");
        int id;
        try{
            id = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            id = -1;
        }
        Employee emp = ts.getEmployee(id);
        if(emp != null) {
            List<ReimbursementApplication> ral = ts.getEmpRequests(emp);
            if(ral != null){
                ctx.result(gson.toJson(ral));
                ctx.status(200);
            } else {
                ctx.status(404);
            }
        } else {
            ctx.status(404);
        }
    };

    public Handler employeeRequestAdd = ctx -> {
        String input = ctx.pathParam("id");
        ReimbursementApplication ra = gson.fromJson(ctx.body(), ReimbursementApplication.class);
        int id;
        try{
            id = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            id = -1;
        }
        Employee emp = ts.getEmployee(id);
        if(emp != null && ra != null) {
            ReimbursementApplication ra2 = ts.employeeRequestAdd(emp, ra);
            if(ra2 != null){
                ctx.result(gson.toJson(ra2));
                ctx.status(200);
            } else {
                ctx.status(404);
            }
        } else {
            ctx.status(404);
        }
    };

    public Handler getTuition = ctx -> {
        String input = ctx.pathParam("id");
        int id;
        try{
            id = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            id = -1;
        }
        Employee emp = ts.getEmployee(id);
        if(emp != null) {
            double left = ts.getTuition(emp);
            if(left < 0) {
                left = 0;
                ctx.result(gson.toJson(left));
                ctx.status(200);
            } else {
                ctx.result(gson.toJson(left));
                ctx.status(200);
            }
        } else {
            ctx.status(404);
        }
    };

    public Handler postGrade = ctx -> {
        String reim = ctx.queryParam("reimnum");
        String grade = ctx.queryParam("grade");
        if(reim != null && grade != null) {
            int reimnum = Integer.parseInt(reim);
            ReimbursementApplication ra = ts.postGrade(reimnum, grade);
            if(ra != null) {
                ctx.result(gson.toJson(ra));
                ctx.status(200);
            } else {
                ctx.status(404);
            }
        } else {
            ctx.status(404);
        }
    };

    public Handler getAwaiting = ctx -> {
        String input = ctx.pathParam("id");
        int id;
        try{
            id = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            id = -1;
        }
        Employee emp = ts.getEmployee(id);
        if(emp != null) {
            List<ReimbursementApplication> ra = ts.getAwaiting(emp);
            if (ra != null) {
                ctx.result(gson.toJson(ra));
                ctx.status(200);
            } else {
                ctx.status(404);
            }
        }
    };

    public Handler approve = ctx -> {
        String input = ctx.pathParam("id");
        ReimbursementApplication rnum = gson.fromJson(ctx.body(), ReimbursementApplication.class);
        int id;
        try{
            id = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            id = -1;
        }
        Employee emp = ts.getEmployee(id);
        if(emp != null && rnum != null) {
            ReimbursementApplication ra = ts.approve(emp, rnum);
            if(ra != null) {
                ctx.result(gson.toJson(ra));
                ctx.status(200);
            } else {
                ctx.status(404);
            }
        } else {
            ctx.status(404);
        }
    };

    public Handler deny = ctx -> {
        String input = ctx.pathParam("id");
        ReimbursementApplication rnum = gson.fromJson(ctx.body(), ReimbursementApplication.class);
        int id;
        try{
            id = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            id = -1;
        }
        Employee emp = ts.getEmployee(id);
        if(emp != null && rnum != null) {
            ReimbursementApplication ra = ts.deny(emp, rnum);
            if(ra != null) {
                ctx.result(gson.toJson(ra));
                ctx.status(200);
            } else {
                ctx.status(404);
            }
        } else {
            ctx.status(404);
        }
    };

    public Handler moreInfo = ctx -> {
        ReimbursementApplication rnum = gson.fromJson(ctx.body(), ReimbursementApplication.class);
        System.out.println(rnum);
        if(rnum.getJustif() != null) {
            ReimbursementApplication ra = ts.moreInfo(rnum);
            if(ra != null) {
                ctx.result(gson.toJson(ra));
                ctx.status(200);
            } else {
                ctx.status(404);
            }
        } else {
            ctx.status(404);
        }
    };

    public Handler respond = ctx -> {
        String input = ctx.pathParam("id");
        ReimbursementApplication rnum = gson.fromJson(ctx.body(), ReimbursementApplication.class);
        int id;
        try{
            id = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            id = -1;
        }
        Employee emp = ts.getEmployee(id);
        if(emp != null && rnum != null) {
            ReimbursementApplication ra = ts.respond(emp, rnum);
            if(ra != null) {
                System.out.println(ra);
                ctx.result(gson.toJson(ra));
                ctx.status(200);
            } else {
                ctx.status(404);
            }
        } else {
            ctx.status(404);
        }
    };

//    public Handler getEmpRequestsById = ctx -> {
//        String reim = ctx.queryParam("reimnum");
//    };
}
