public class Loan extends LoanApplet {
    double in, la;
    int mo;

    Loan(double interest, int year, double loanAmount) {
        in = interest;
        mo = year;
        la = loanAmount;
    }

    double getMonthlyPayment() {;
        return getTotalPayment()/(mo*12);
    }

    double getTotalPayment() {
        return ((la*mo*in)/100)+la;
    }
}