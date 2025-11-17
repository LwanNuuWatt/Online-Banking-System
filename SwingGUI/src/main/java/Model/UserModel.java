package Model;

import java.math.BigDecimal;
import java.sql.Date;

public class UserModel {
    private long Id;
    private String name;
    private int Age;
    private String Address;
    private String PhoneNo;
    private String password;
    private BigDecimal Amount;
    private String Salt;
    private String Status;
    private Date date;
    private int compound_frequency;
    private int tenure_years;

    public long getId() {
        return Id;
    }

    public void setId(long Id) {
        this.Id = Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String PhoneNo) {
        this.PhoneNo = PhoneNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigDecimal getAmount() {
        return Amount;
    }

    public void setAmount(BigDecimal Amount) {
        this.Amount = Amount;
    }

    public String getSalt() {
        return Salt;
    }

    public void setSalt(String Salt) {
        this.Salt = Salt;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int Age) {
        this.Age = Age;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCompound_frequency() {
        return compound_frequency;
    }

    public void setCompound_frequency(int compound_frequency) {
        this.compound_frequency = compound_frequency;
    }

    public int getTenure_years() {
        return tenure_years;
    }

    public void setTenure_years(int tenure_years) {
        this.tenure_years = tenure_years;
    }
}
