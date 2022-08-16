package dev.gobert.main;

import dev.gobert.controllers.TuitionController;
import dev.gobert.objects.Employee;
import dev.gobert.objects.ReimbursementApplication;
import dev.gobert.repositories.TuitionRepo;
import dev.gobert.repositories.TuitionRepoImp;
import dev.gobert.services.TuitionService;
import dev.gobert.services.TuitionServiceImp;
import io.javalin.Javalin;

public class App {

    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
            config.enableCorsForAllOrigins();
        });
        establishRoutes(app);
        app.start();
    }

    private static void establishRoutes(Javalin app) {
        TuitionRepo tr = new TuitionRepoImp();
        TuitionService ts = new TuitionServiceImp(tr);
        TuitionController tc = new TuitionController(ts);

        app.get("/", (ctx) -> {
            ctx.result("Placeholder.");
        });
        app.get("/login", tc.getEmployeeByEmail);
        app.get("/login/{id}", tc.getEmployee);
        app.get("/login/{id}/dashboard", tc.employeeDash);
        app.get("/login/{id}/getrequests", tc.getEmpRequests);
        //app.get("/login/{id}/getrequestsid", tc.getEmpRequestsById);
        app.post("/login/{id}/dashboard/request",tc.employeeRequestAdd);
        app.get("/login/{id}/dashboard/gettuition", tc.getTuition);
        app.post("/login/{id}/dashboard/postgrade", tc.postGrade);
        app.get("/login/{id}/dashboard/getawaiting", tc.getAwaiting);
        app.post("/login/{id}/dashboard/approve", tc.approve);
        app.post("/login/{id}/dashboard/deny", tc.deny);
        app.post("/login/{id}/dashboard/moreinfo", tc.moreInfo);
        app.patch("/login/{id}/dashboard/respond", tc.respond);
    }

}
