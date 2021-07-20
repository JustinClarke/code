import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class LoanApplet extends JApplet {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private JTextField jftAnnualInterestRate = new JTextField();
    private JTextField jftNumberOfYears = new JTextField();
    private JTextField jftLoanAmount = new JTextField();
    private JTextField jftMonthlyPayment = new JTextField();
    private JTextField jftTotalPayment = new JTextField();
    private JButton jbtComputeLoan = new JButton("Compute Payment");

    public void init() {
        jftMonthlyPayment.setEditable(false);
        jftTotalPayment.setEditable(false);
        jftAnnualInterestRate.setHorizontalAlignment(JTextField.RIGHT);
        jftNumberOfYears.setHorizontalAlignment(JTextField.RIGHT);
        jftLoanAmount.setHorizontalAlignment(JTextField.RIGHT);
        jftMonthlyPayment.setHorizontalAlignment(JTextField.RIGHT);
        jftTotalPayment.setHorizontalAlignment(JTextField.RIGHT);
        JPanel p1 = new JPanel(new GridLayout(5, 2));
        p1.add(new JLabel("Annual Interest Rate"));
        p1.add(jftAnnualInterestRate);
        p1.add(new JLabel("Number of Years"));
        p1.add(jftNumberOfYears);
        p1.add(new JLabel("Loan Amount"));
        p1.add(jftLoanAmount);
        p1.add(new JLabel("Monthly Payment"));
        p1.add(jftMonthlyPayment);
        p1.add(new JLabel("Total Payment"));
        p1.add(jftTotalPayment);
        p1.setBorder(new TitledBorder("Enter interest rate, year and loan amount"));
        JPanel p2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        p2.add(jbtComputeLoan);
        add(p1, BorderLayout.CENTER);
        add(p2, BorderLayout.SOUTH);
        jbtComputeLoan.addActionListener(new ButtonListener());
    }

    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            double interest = Double.parseDouble(jftAnnualInterestRate.getText());
            int year = Integer.parseInt(jftNumberOfYears.getText());
            double loanAmount = Double.parseDouble(jftLoanAmount.getText());
            Loan loan = new Loan(interest, year, loanAmount);
            jftMonthlyPayment.setText(String.format("%.2f", loan.getMonthlyPayment()));
            jftTotalPayment.setText(String.format("%.2f", loan.getTotalPayment()));
        }
    }

    public static void main(String args[]) {
        JFrame frame = new JFrame("Applet is in the frame");
        LoanApplet applet = new LoanApplet();
        frame.add(applet, BorderLayout.CENTER);
        applet.init();
        frame.setSize(300, 300);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}