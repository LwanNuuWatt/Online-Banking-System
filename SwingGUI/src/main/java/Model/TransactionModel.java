package Model;

import java.math.BigDecimal;
import java.sql.Date;

public class TransactionModel {
    private long Transaction_ID;
    private long User_ID;
    private String Phone_No;
    private Date Transaction_date;
    private String Transaction_type;
    private BigDecimal Amount;
    private int compound_frequency;
    private BigDecimal Total_amount;
    private int tenure_years;
    private double transfer_fee;

    public long getTransaction_ID() {
        return Transaction_ID;
    }

    public void setTransaction_ID(long Transaction_ID) {
        this.Transaction_ID = Transaction_ID;
    }

    public long getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(long User_ID) {
        this.User_ID = User_ID;
    }

    public String getPhone_No() {
        return Phone_No;
    }

    public void setPhone_No(String Phone_No) {
        this.Phone_No = Phone_No;
    }

    public Date getTransaction_date() {
        return Transaction_date;
    }

    public void setTransaction_date(Date Transaction_date) {
        this.Transaction_date = Transaction_date;
    }

    public String getTransaction_type() {
        return Transaction_type;
    }

    public void setTransaction_type(String Transaction_type) {
        this.Transaction_type = Transaction_type;
    }

    public BigDecimal getAmount() {
        return Amount;
    }

    public void setAmount(BigDecimal Amount) {
        this.Amount = Amount;
    }

    public int getCompound_frequency() {
        return compound_frequency;
    }

    public void setCompound_frequency(int compound_frequency) {
        this.compound_frequency = compound_frequency;
    }

    public BigDecimal getTotal_amount() {
        return Total_amount;
    }

    public void setTotal_amount(BigDecimal Total_amount) {
        this.Total_amount = Total_amount;
    }

    public int getTenure_years() {
        return tenure_years;
    }

    public void setTenure_years(int tenure_years) {
        this.tenure_years = tenure_years;
    }

    public double getTransfer_fee() {
        return transfer_fee;
    }

    public void setTransfer_fee(double transfer_fee) {
        this.transfer_fee = transfer_fee;
    }
}
