import java.sql.*;
import java.time.LocalDate;

public class ReportGenerator {

    public String generateBIRReport() throws SQLException {
        StringBuilder report = new StringBuilder();
        report.append("BIR Report - Withholding Tax Summary\n");
        report.append("Generated on: ").append(LocalDate.now().toString()).append("\n");

        Connection conn = dbConnection.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT fname, lname, incomeTax FROM payroll");

        double totalTax = 0;
        while (rs.next()) {
            String fname = rs.getString("fname");
            String lname = rs.getString("lname");
            double incomeTax = rs.getDouble("incomeTax");
            totalTax += incomeTax;
            report.append(String.format("Employee: %-20s Tax Withheld: ₱%10.2f\n", fname + " " + lname, incomeTax));
        }

        report.append("----------------------------------------\n");
        report.append(String.format("Total Withholding Tax: ₱%10.2f\n", totalTax));

        conn.close();
        return report.toString();
    }

    public String generateSSSReport() throws SQLException {
        StringBuilder report = new StringBuilder();
        report.append("SSS Contribution Report\n");
        report.append("Generated on: ").append(LocalDate.now().toString()).append("\n");

        Connection conn = dbConnection.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT fname, lname, gross, sss FROM payroll");

        double totalEmployeeShare = 0;
        double totalEmployerShare = 0;

        while (rs.next()) {
            String fname = rs.getString("fname");
            String lname = rs.getString("lname");
            double gross = rs.getDouble("gross");
            double employeeShare = rs.getDouble("sss");
            double employerShare = Math.min(gross, 35000) * 0.10;

            totalEmployeeShare += employeeShare;
            totalEmployerShare += employerShare;

            report.append(String.format("Employee: %-20s Employee Share: ₱%10.2f Employer Share: ₱%10.2f\n",
                    fname + " " + lname, employeeShare, employerShare));
        }

        report.append("----------------------------------------\n");
        report.append(String.format("Total Employee Share: ₱%10.2f\n", totalEmployeeShare));
        report.append(String.format("Total Employer Share: ₱%10.2f\n", totalEmployerShare));
        report.append(String.format("Total SSS Contribution: ₱%10.2f\n", totalEmployeeShare + totalEmployerShare));

        conn.close();
        return report.toString();
    }

    public String generatePhilHealthReport() throws SQLException {
        StringBuilder report = new StringBuilder();
        report.append("PhilHealth Contribution Report\n");
        report.append("Generated on: ").append(LocalDate.now().toString()).append("\n");

        Connection conn = dbConnection.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT fname, lname, gross, philhealth FROM payroll");

        double totalEmployeeShare = 0;
        double totalEmployerShare = 0;

        while (rs.next()) {
            String fname = rs.getString("fname");
            String lname = rs.getString("lname");
            double gross = rs.getDouble("gross");
            double employeeShare = rs.getDouble("philhealth");
            double employerShare = employeeShare;

            totalEmployeeShare += employeeShare;
            totalEmployerShare += employerShare;

            report.append(String.format("Employee: %-20s Employee Share: ₱%10.2f Employer Share: ₱%10.2f\n",
                    fname + " " + lname, employeeShare, employerShare));
        }

        report.append("----------------------------------------\n");
        report.append(String.format("Total Employee Share: ₱%10.2f\n", totalEmployeeShare));
        report.append(String.format("Total Employer Share: ₱%10.2f\n", totalEmployerShare));
        report.append(String.format("Total PhilHealth Contribution: ₱%10.2f\n", totalEmployeeShare + totalEmployerShare));

        conn.close();
        return report.toString();
    }

    public String generatePagIBIGReport() throws SQLException {
        StringBuilder report = new StringBuilder();
        report.append("Pag-IBIG Contribution Report\n");
        report.append("Generated on: ").append(LocalDate.now().toString()).append("\n");

        Connection conn = dbConnection.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT fname, lname, pagibig FROM payroll");

        double totalEmployeeShare = 0;
        double totalEmployerShare = 0;

        while (rs.next()) {
            String fname = rs.getString("fname");
            String lname = rs.getString("lname");
            double employeeShare = rs.getDouble("pagibig");
            double employerShare = employeeShare;

            totalEmployeeShare += employeeShare;
            totalEmployerShare += employerShare;

            report.append(String.format("Employee: %-20s Employee Share: ₱%10.2f Employer Share: ₱%10.2f\n",
                    fname + " " + lname, employeeShare, employerShare));
        }

        report.append("----------------------------------------\n");
        report.append(String.format("Total Employee Share: ₱%10.2f\n", totalEmployeeShare));
        report.append(String.format("Total Employer Share: ₱%10.2f\n", totalEmployerShare));
        report.append(String.format("Total Pag-IBIG Contribution: ₱%10.2f\n", totalEmployeeShare + totalEmployerShare));

        conn.close();
        return report.toString();
    }

    public String generateBIRForm2316(String fname, String lname, String position, double gross,
                                      double sssEmployee, double philhealthEmployee, double pagibigEmployee,
                                      double incomeTax, double netPay) {
        StringBuilder form = new StringBuilder();
        form.append("========== BIR Form 2316 ==========\n");
        form.append("Employee: ").append(fname).append(" ").append(lname).append("\n");
        form.append("Position: ").append(position).append("\n");
        form.append("Generated on: ").append(LocalDate.now().toString()).append("\n");
        form.append("------------------------------------\n");
        form.append(String.format("%-20s ₱%10.2f\n", "Gross Compensation:", gross));
        form.append("Non-Taxable Deductions:\n");
        form.append(String.format("%-20s ₱%10.2f\n", "  SSS:", sssEmployee));
        form.append(String.format("%-20s ₱%10.2f\n", "  PhilHealth:", philhealthEmployee));
        form.append(String.format("%-20s ₱%10.2f\n", "  Pag-IBIG:", pagibigEmployee));
        form.append("------------------------------------\n");
        form.append(String.format("%-20s ₱%10.2f\n", "Taxable Income:", gross - (sssEmployee + philhealthEmployee + pagibigEmployee)));
        form.append(String.format("%-20s ₱%10.2f\n", "Withholding Tax:", incomeTax));
        form.append("------------------------------------\n");
        form.append(String.format("%-20s ₱%10.2f\n", "Net Pay:", netPay));
        form.append("====================================\n");
        return form.toString();
    }
}

