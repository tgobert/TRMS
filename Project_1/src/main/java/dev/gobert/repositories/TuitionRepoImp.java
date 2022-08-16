package dev.gobert.repositories;

import dev.gobert.objects.Discount;
import dev.gobert.objects.Employee;
import dev.gobert.objects.ReimbursementApplication;
import dev.gobert.util.JDBCConnection;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TuitionRepoImp implements TuitionRepo{

    public static Connection conn = JDBCConnection.getConnection();

    @Override
    public Employee getEmployee(int id) {
        String sql = "SELECT * FROM employees WHERE e_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return buildEmployee(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Employee getEmployeeByEmail(String input) {
        String sql = "SELECT * FROM employees WHERE email = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, input);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return buildEmployee(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ReimbursementApplication> getEmpRequests(Employee emp) {
        List<ReimbursementApplication> ral = new ArrayList<>();
        String sql = "SELECT * FROM reimburse WHERE e_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, emp.getEid());
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                ral.add(buildReApp(rs));
            }
            return ral;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ReimbursementApplication employeeRequestAdd(Employee emp, ReimbursementApplication ra) {
        if(emp.getJobtitle().equals("Cashier") || emp.getJobtitle().equals("Stocker")
                || emp.getJobtitle().equals("Human Resources")) {
            String sql = "INSERT INTO reimburse VALUES (default, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING *";
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, emp.getEid());
                ps.setLong(2, ra.getDatetime());
                ps.setString(3, ra.getLocal());
                ps.setString(4, ra.getCourse());
                ps.setInt(5, ra.getPrice());
                ps.setString(6, ra.getGradeForm());
                ps.setString(7, ra.getEventType());
                ps.setString(8, ra.getJustif());
                ps.setInt(9, ra.getMissedHours());
                ps.setString(10, "Awaiting Manager");
                ps.setString(11, "Pending");
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return buildReApp(rs);
                }
                return null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(emp.getJobtitle().equals("Retail Manager") || emp.getJobtitle().equals("Back Room Manager")
                || emp.getJobtitle().equals("Human Resource Manager")) {
            String sql = "INSERT INTO reimburse VALUES (default, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING *";
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, emp.getEid());
                ps.setLong(2, ra.getDatetime());
                ps.setString(3, ra.getLocal());
                ps.setString(4, ra.getCourse());
                ps.setInt(5, ra.getPrice());
                ps.setString(6, ra.getGradeForm());
                ps.setString(7, ra.getEventType());
                ps.setString(8, ra.getJustif());
                ps.setInt(9, ra.getMissedHours());
                ps.setString(10, "Awaiting Head");
                ps.setString(11, "Pending");
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return buildReApp(rs);
                }
                return null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(emp.getJobtitle().equals("Retail Head") || emp.getJobtitle().equals("Back Room Head")
                || emp.getJobtitle().equals("Human Resource Head") || emp.getJobtitle().equals("Owner")
                || emp.getJobtitle().equals("Store Manager")) {
            String sql = "INSERT INTO reimburse VALUES (default, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING *";
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, emp.getEid());
                ps.setLong(2, ra.getDatetime());
                ps.setString(3, ra.getLocal());
                ps.setString(4, ra.getCourse());
                ps.setInt(5, ra.getPrice());
                ps.setString(6, ra.getGradeForm());
                ps.setString(7, ra.getEventType());
                ps.setString(8, ra.getJustif());
                ps.setInt(9, ra.getMissedHours());
                ps.setString(10, "Awaiting Coordinator");
                ps.setString(11, "Pending");
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return buildReApp(rs);
                }
                return null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(emp.getJobtitle().equals("Benefits Coordinator")) {
            String sql = "INSERT INTO reimburse VALUES (default, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING *";
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, emp.getEid());
                ps.setLong(2, ra.getDatetime());
                ps.setString(3, ra.getLocal());
                ps.setString(4, ra.getCourse());
                ps.setInt(5, ra.getPrice());
                ps.setString(6, ra.getGradeForm());
                ps.setString(7, ra.getEventType());
                ps.setString(8, ra.getJustif());
                ps.setInt(9, ra.getMissedHours());
                ps.setString(10, "Awaiting Owner");
                ps.setString(11, "Pending");
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return buildReApp(rs);
                }
                return null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        return null;
    }

    @Override
    public double getTuition(Employee emp) {
        String sql = "SELECT price, datetime, event_type FROM reimburse WHERE e_id = ? " +
                "and final_decision in ('Pending', 'Approved');";
        String sql2 = "SELECT * FROM discounts";
        List<ReimbursementApplication> apps = new ArrayList<>();
        List<Discount> dis = new ArrayList<>();
        double left = 1000;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, emp.getEid());
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                apps.add(buildReApp2(rs));
            }
            PreparedStatement ps2 = conn.prepareStatement(sql2);
            ResultSet rs2 = ps2.executeQuery();
            while(rs2.next()) {
                dis.add(buildDis(rs2));
            }
            for(int i = 0; i <= apps.size() - 1; i++) {
                if(apps.get(i).getEventType().equals(dis.get(0).getEventType())) {
                    left -= apps.get(i).getPrice() * dis.get(0).getPercent();
                }
                if(apps.get(i).getEventType().equals(dis.get(1).getEventType())) {
                    left -= apps.get(i).getPrice() * dis.get(1).getPercent();
                }
                if(apps.get(i).getEventType().equals(dis.get(2).getEventType())) {
                    left -= apps.get(i).getPrice() * dis.get(2).getPercent();

                }
                if(apps.get(i).getEventType().equals(dis.get(3).getEventType())) {
                    left -= apps.get(i).getPrice() * dis.get(3).getPercent();
                }
                if(apps.get(i).getEventType().equals(dis.get(4).getEventType())) {
                    left -= apps.get(i).getPrice() * dis.get(4).getPercent();
                }
                if(apps.get(i).getEventType().equals(dis.get(5).getEventType())) {
                    left -= apps.get(i).getPrice() * dis.get(5).getPercent();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return left;
    }

    @Override
    public ReimbursementApplication postGrade(int reimnum, String grade) {
        if (grade.equals("A") || grade.equals("B") || grade.equals("C") || grade.equals("D")
                || grade.equals("F")) {
            if (grade.equals("D") || grade.equals("F")) {
                String sql = "UPDATE reimburse SET status = 'Complete', final_decision = 'Rejected' " +
                        "WHERE reim_num = ? RETURNING *;";
                try {
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setInt(1, reimnum);
                    ResultSet rs = ps.executeQuery();
                    if(rs.next()) {
                        return buildReApp(rs);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                String sql = "UPDATE reimburse SET status = 'Complete', final_decision = 'Approved' " +
                        "WHERE reim_num = ? RETURNING *;";
                try {
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setInt(1, reimnum);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        return buildReApp(rs);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        if(grade.equals("P") || grade.equals("F")) {
            if (grade.equals("F")) {
                String sql = "UPDATE reimburse SET status = 'Complete', final_decision = 'Rejected' " +
                        "WHERE reim_num = ? RETURNING *;";
                try {
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setInt(1, reimnum);
                    ResultSet rs = ps.executeQuery();
                    if(rs.next()) {
                        return buildReApp(rs);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                String sql = "UPDATE reimburse SET status = 'Complete', final_decision = 'Approved' " +
                        "WHERE reim_num = ? RETURNING *;";
                try {
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setInt(1, reimnum);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        return buildReApp(rs);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        if(grade.equals("Complete") || grade.equals("Incomplete")) {
            if (grade.equals("Incomplete")) {
                String sql = "UPDATE reimburse SET status = 'Complete', final_decision = 'Rejected' " +
                        "WHERE reim_num = ? RETURNING *;";
                try {
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setInt(1, reimnum);
                    ResultSet rs = ps.executeQuery();
                    if(rs.next()) {
                        return buildReApp(rs);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                String sql = "UPDATE reimburse SET status = 'Complete', final_decision = 'Approved' " +
                        "WHERE reim_num = ? RETURNING *;";
                try {
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setInt(1, reimnum);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        return buildReApp(rs);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    public List<ReimbursementApplication> getAwaiting(Employee emp) {
        if(emp.getJobtitle().equals("Retail Manager")) {
            List<ReimbursementApplication> ral = new ArrayList<>();
            String sql = "SELECT * FROM reimburse WHERE status = ?";
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, "Awaiting Manager");
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    ral.add(buildReApp(rs));
                }
                return ral;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(emp.getJobtitle().equals("Back Room Manager")) {
            List<ReimbursementApplication> ral = new ArrayList<>();
            String sql = "SELECT * FROM reimburse WHERE status = ?";
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, "Awaiting Manager");
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    ral.add(buildReApp(rs));
                }
                return ral;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(emp.getJobtitle().equals("Human Resource Manager")) {
            List<ReimbursementApplication> ral = new ArrayList<>();
            String sql = "SELECT * FROM reimburse WHERE status = ?";
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, "Awaiting Manager");
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    ral.add(buildReApp(rs));
                }
                return ral;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(emp.getJobtitle().equals("Retail Head")) {
            List<ReimbursementApplication> ral = new ArrayList<>();
            String sql = "SELECT * FROM reimburse WHERE status = ?";
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, "Awaiting Head");
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    ral.add(buildReApp(rs));
                }
                return ral;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(emp.getJobtitle().equals("Back Room Head")) {
            List<ReimbursementApplication> ral = new ArrayList<>();
            String sql = "SELECT * FROM reimburse WHERE status = ?";
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, "Awaiting Head");
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    ral.add(buildReApp(rs));
                }
                return ral;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(emp.getJobtitle().equals("Human Resource Head")) {
            List<ReimbursementApplication> ral = new ArrayList<>();
            String sql = "SELECT * FROM reimburse WHERE status = ?";
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, "Awaiting Manager");
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    ral.add(buildReApp(rs));
                }
                return ral;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(emp.getJobtitle().equals("Benefits Coordinator")) {
            List<ReimbursementApplication> ral = new ArrayList<>();
            String sql = "SELECT * FROM reimburse WHERE status = ?";
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, "Awaiting Coordinator");
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    ral.add(buildReApp(rs));
                }
                return ral;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(emp.getJobtitle().equals("Owner")) {
            List<ReimbursementApplication> ral = new ArrayList<>();
            String sql = "SELECT * FROM reimburse WHERE status = ?";
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, "Awaiting Owner");
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    ral.add(buildReApp(rs));
                }
                return ral;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public ReimbursementApplication approve(Employee emp, ReimbursementApplication rnum) {
        ReimbursementApplication ra = getRaById(rnum);
        if(ra.getStatus().equals("Awaiting Manager")) {
            String sql = "UPDATE reimburse SET status = 'Awaiting Head' WHERE reim_num = ? RETURNING *";
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, rnum.getrNum());
                ResultSet rs = ps.executeQuery();
                if(rs.next()) {
                    return buildReApp(rs);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(ra.getStatus().equals("Awaiting Head")) {
            String sql = "UPDATE reimburse SET status = 'Awaiting Coordinator' WHERE reim_num = ? RETURNING *";
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, rnum.getrNum());
                ResultSet rs = ps.executeQuery();
                if(rs.next()) {
                    return buildReApp(rs);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(ra.getStatus().equals("Awaiting Coordinator") || ra.getStatus().equals("Awaiting Owner")) {
            String sql = "UPDATE reimburse SET status = 'Awaiting Grade' WHERE reim_num = ? RETURNING *";
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, rnum.getrNum());
                ResultSet rs = ps.executeQuery();
                if(rs.next()) {
                    return buildReApp(rs);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public ReimbursementApplication deny(Employee emp, ReimbursementApplication rnum) {
        String sql = "UPDATE reimburse SET status = 'Complete', final_decision = 'Rejected' " +
                "WHERE reim_num = ? RETURNING *";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, rnum.getrNum());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return buildReApp(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ReimbursementApplication moreInfo(ReimbursementApplication rnum) {
        String sql = "UPDATE reimburse SET status = 'Awaiting Response', justification = ? WHERE reim_num = ? RETURNING *";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, rnum.getJustif());
            ps.setInt(2, rnum.getrNum());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return buildReApp(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ReimbursementApplication respond(Employee emp, ReimbursementApplication rnum) {
        String sql = "UPDATE reimburse SET status = 'Awaiting Manager', justification = ? WHERE reim_num = ? RETURNING *";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, rnum.getJustif());
            ps.setInt(2, rnum.getrNum());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return buildReApp(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    private ReimbursementApplication buildReApp(ResultSet rs) throws SQLException {
        ReimbursementApplication ra = new ReimbursementApplication();
        ra.setrNum(rs.getInt("reim_num"));
        ra.setEid(rs.getInt("e_id"));
        ra.setDatetime(rs.getLong("datetime"));
        ra.setLocal(rs.getString("loc"));
        ra.setCourse(rs.getString("course"));
        ra.setPrice(rs.getInt("price"));
        ra.setGradeForm(rs.getString("grade_format"));
        ra.setEventType(rs.getString("event_type"));
        ra.setJustif(rs.getString("justification"));
        ra.setMissedHours(rs.getInt("missed_hours"));
        ra.setStatus(rs.getString("status"));
        ra.setFinalDecision(rs.getString("final_decision"));
        return ra;
    }

    private ReimbursementApplication buildReApp2(ResultSet rs) throws SQLException {
        ReimbursementApplication ra = new ReimbursementApplication();
        ra.setDatetime(rs.getLong("datetime"));
        ra.setPrice(rs.getInt("price"));
        ra.setEventType(rs.getString("event_type"));
        return ra;
    }

    private Discount buildDis(ResultSet rs) throws SQLException {
        Discount dis = new Discount();
        dis.setEventType(rs.getString("event_type"));
        dis.setPercent(rs.getDouble("per"));
        return dis;
    }

    @NotNull
    private Employee buildEmployee(ResultSet rs) throws SQLException {
        Employee emp = new Employee();
        emp.setEid(rs.getInt("e_id"));
        emp.setNamefl(rs.getString("namefl"));
        emp.setEmail(rs.getString("email"));
        emp.setJobtitle(rs.getString("jobtitle"));
        return emp;
    }

    public ReimbursementApplication getRaById(ReimbursementApplication rnum) {
        String sql = "SELECT * FROM reimburse WHERE reim_num = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, rnum.getrNum());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return buildReApp(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
