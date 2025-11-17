package Model;

import java.math.BigDecimal;
import java.sql.Date;

public class LoanModel {
    private long loanID;
    private String Name;
    private String email;
    private String phone;
    private Date DOB;
    private String address;
    private BigDecimal LoanAmount;
    private double InterestRate;
    private String NRC;
    private String Job;
    private String asset;
    private String collateral;
    private String LoanType;
    private String TimeFrame;
    private String income;
    private String maritalStatus;
    private BigDecimal EMI;
    private String Status;
    private int tenureYears;

    public long getLoanID() {
        return loanID;
    }

    public void setLoanID(long loanID) {
        this.loanID = loanID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getDOB() {
        return DOB;
    }

    public void setDOB(Date DOB) {
        this.DOB = DOB;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getLoanAmount() {
        return LoanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        LoanAmount = loanAmount;
    }

    public double getInterestRate() {
        return InterestRate;
    }

    public void setInterestRate(double interestRate) {
        InterestRate = interestRate;
    }

    public String getNRC() {
        return NRC;
    }

    public void setNRC(String NRC) {
        this.NRC = NRC;
    }

    public String getJob() {
        return Job;
    }

    public void setJob(String job) {
        Job = job;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public String getCollateral() {
        return collateral;
    }

    public void setCollateral(String collateral) {
        this.collateral = collateral;
    }

    public String getLoanType() {
        return LoanType;
    }

    public void setLoanType(String loanType) {
        LoanType = loanType;
    }

    public String getTimeFrame() {
        return TimeFrame;
    }

    public void setTimeFrame(String timeFrame) {
        TimeFrame = timeFrame;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public BigDecimal getEMI() {
        return EMI;
    }

    public void setEMI(BigDecimal EMI) {
        this.EMI = EMI;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public int getTenureYears() {
        return tenureYears;
    }

    public void setTenureYears(int tenureYears) {
        this.tenureYears = tenureYears;
    }
}
