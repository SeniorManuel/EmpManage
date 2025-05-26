import java.sql.*;
import java.time.LocalDate;

public class ReportGenerator {

    public String generateBIRForm2316(String fname, String lname, String position, double gross, double sss, double philhealth, double pagibig, double incomeTax, double netPay) {
        StringBuilder report = new StringBuilder();
        report.append("BIR Form 2316 - Certificate of Compensation Payment/Tax Withheld\n");
        report.append("Year: 2025\n");
        report.append("Generated on: ").append(LocalDate.now().toString()).append("\n\n");

        report.append("Employee: ").append(fname).append(" ").append(lname).append("\n");
        report.append("Position: ").append(position).append("\n");
        report.append(String.format("Total Compensation (Gross): ₱%.2f\n", gross));
        report.append(String.format("Less: Non-Taxable Contributions\n"));
        report.append(String.format("  SSS: ₱%.2f\n", sss));
        report.append(String.format("  PhilHealth: ₱%.2f\n", philhealth));
        report.append(String.format("  Pag-IBIG: ₱%.2f\n", pagibig));
        double totalNonTaxable = sss + philhealth + pagibig;
        report.append(String.format("Total Non-Taxable: ₱%.2f\n", totalNonTaxable));
        double taxableIncome = gross - totalNonTaxable;
        report.append(String.format("Taxable Income: ₱%.2f\n", taxableIncome));
        report.append(String.format("Tax Withheld: ₱%.2f\n", incomeTax));
        report.append(String.format("Net Pay After Tax: ₱%.2f\n", netPay));
        report.append("----------------------------------------\n");

        return report.toString();
    }


    public String generateBIRReport() throws SQLException {
        StringBuilder report = new StringBuilder();
        report.append("BIR Form 1604-C - Annual Information Return of Income Taxes Withheld on Compensation\n");
        report.append("Generated on: ").append(LocalDate.now().toString()).append("\n\n");

        double totalWithheldTax = 0;
        Connection conn = dbConnection.getConnection();
        String query = "SELECT fname, lname, incomeTax FROM payroll";
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String fname = rs.getString("fname");
            String lname = rs.getString("lname");
            double incomeTax = rs.getDouble("incomeTax");
            totalWithheldTax += incomeTax;
            report.append(String.format("Employee: %s %s, Withholding Tax: ₱%.2f\n", fname, lname, incomeTax));
        }

        report.append(String.format("\nTotal Withholding Tax: ₱%.2f", totalWithheldTax));
        conn.close();
        return report.toString();
    }


    public String generateSSSReport() throws SQLException {
        StringBuilder report = new StringBuilder();
        report.append("SSS Contribution Report\n");
        report.append("Generated on: ").append(LocalDate.now().toString()).append("\n\n");

        double totalEmployeeShare = 0;
        double totalEmployerShare = 0;
        Connection conn = dbConnection.getConnection();
        String query = "SELECT fname, lname, sss, gross FROM payroll";
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String fname = rs.getString("fname");
            String lname = rs.getString("lname");
            double sssEmployee = rs.getDouble("sss");
            double gross = rs.getDouble("gross");
            double sssEmployer = Math.min(gross, 35000) * 0.10;
            totalEmployeeShare += sssEmployee;
            totalEmployerShare += sssEmployer;
            report.append(String.format("Employee: %s %s, Employee Share: ₱%.2f, Employer Share: ₱%.2f\n", fname, lname, sssEmployee, sssEmployer));
        }

        report.append(String.format("\nTotal Employee Share: ₱%.2f", totalEmployeeShare));
        report.append(String.format("\nTotal Employer Share: ₱%.2f", totalEmployerShare));
        conn.close();
        return report.toString();
    }


    public String generatePhilHealthReport() throws SQLException {
        StringBuilder report = new StringBuilder();
        report.append("PhilHealth Contribution Report\n");
        report.append("Generated on: ").append(LocalDate.now().toString()).append("\n\n");

        double totalEmployeeShare = 0;
        double totalEmployerShare = 0;
        Connection conn = dbConnection.getConnection();
        String query = "SELECT fname, lname, philhealth, gross FROM payroll";
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String fname = rs.getString("fname");
            String lname = rs.getString("lname");
            double philhealthEmployee = rs.getDouble("philhealth");
            double gross = rs.getDouble("gross");
            double philhealthTotal = Math.min(gross * 0.05, 2500);
            double philhealthEmployer = philhealthTotal / 2;
            totalEmployeeShare += philhealthEmployee;
            totalEmployerShare += philhealthEmployer;
            report.append(String.format("Employee: %s %s, Employee Share: ₱%.2f, Employer Share: ₱%.2f\n", fname, lname, philhealthEmployee, philhealthEmployer));
        }

        report.append(String.format("\nTotal Employee Share: ₱%.2f", totalEmployeeShare));
        report.append(String.format("\nTotal Employer Share: ₱%.2f", totalEmployerShare));
        conn.close();
        return report.toString();
    }


    public String generatePagIBIGReport() throws SQLException {
        StringBuilder report = new StringBuilder();
        report.append("Pag-IBIG Contribution Report\n");
        report.append("Generated on: ").append(LocalDate.now().toString()).append("\n\n");

        double totalEmployeeShare = 0;
        double totalEmployerShare = 0;
        Connection conn = dbConnection.getConnection();
        String query = "SELECT fname, lname, pagibig, gross FROM payroll";
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String fname = rs.getString("fname");
            String lname = rs.getString("lname");
            double pagibigEmployee = rs.getDouble("pagibig");
            double gross = rs.getDouble("gross");
            double pagibigEmployer = Math.min(gross * 0.02, 200);
            totalEmployeeShare += pagibigEmployee;
            totalEmployerShare += pagibigEmployer;
            report.append(String.format("Employee: %s %s, Employee Share: ₱%.2f, Employer Share: ₱%.2f\n", fname, lname, pagibigEmployee, pagibigEmployer));
        }

        report.append(String.format("\nTotal Employee Share: ₱%.2f", totalEmployeeShare));
        report.append(String.format("\nTotal Employer Share: ₱%.2f", totalEmployerShare));
        conn.close();
        return report.toString();
    }
}