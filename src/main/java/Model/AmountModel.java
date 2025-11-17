package Model;

import java.math.BigDecimal;
import java.sql.Date;

public class AmountModel {
    private Long UserID;
    private String PhoneNo;
    private BigDecimal Amount;
    private BigDecimal Loan;
    private BigDecimal Interest;
    private BigDecimal TotalAmount;
    private BigDecimal BankTotalAmount;
    private String AccStatus;
    private Date RegDate;
    private int Fixed_month;

    public Long getUserID() {
        return UserID;
    }

    public void setUserID(Long UserID) {
        this.UserID = UserID;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String PhoneNo) {
        this.PhoneNo = PhoneNo;
    }

    public BigDecimal getAmount() {
        return Amount;
    }

    public void setAmount(BigDecimal Amount) {
        this.Amount = Amount;
    }

    public BigDecimal getLoan() {
        return Loan;
    }

    public void setLoan(BigDecimal Loan) {
        this.Loan = Loan;
    }

    public BigDecimal getInterest() {
        return Interest;
    }

    public void setInterest(BigDecimal Interest) {
        this.Interest = Interest;
    }

    public BigDecimal getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(BigDecimal TotalAmount) {
        this.TotalAmount = TotalAmount;
    }

    public BigDecimal getBankTotalAmount() {
        return BankTotalAmount;
    }

    public void setBankTotalAmount(BigDecimal BankTotalAmount) {
        this.BankTotalAmount = BankTotalAmount;
    }

    public String getAccStatus() {
        return AccStatus;
    }

    public void setAccStatus(String AccStatus) {
        this.AccStatus = AccStatus;
    }

    public Date getRegDate() {
        return RegDate;
    }

    public void setRegDate(Date RegDate) {
        this.RegDate = RegDate;
    }

    public int getFixed_month() {
        return Fixed_month;
    }

    public void setFixed_month(int Fixed_month) {
        this.Fixed_month = Fixed_month;
    }
}
