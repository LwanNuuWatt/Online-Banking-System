package Controller;

import Connection.connectionStrings;
import Model.AmountModel;
import Model.LoanModel;
import Model.TransactionModel;
import Model.UserModel;

import javax.swing.*;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static CheckPassword.passwordCheck.verifyPassword;
import static Form.LoanTable.refreshData;

public class userController {

    public List<UserModel> userModelList()
    {
        String query = "SELECT * FROM tbl_customer";
        List<UserModel> lists = new ArrayList<>();

        try (Connection connection = connectionStrings.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                UserModel user = new UserModel();
                getUser(resultSet, user);

                lists.add(user);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return lists;
    }

    public static boolean CreateUser(UserModel model)
    {
        String query = "INSERT INTO tbl_customer " +
                "(User_ID, Name, Age, Address, PhoneNo, Password, Amount, Salt, Reg_Date, Status, tenure_years)" +
                " VALUES(?,?,?,?,?,?,?,?,?,?,?)";

        try (Connection connection = connectionStrings.getConnection();
             PreparedStatement statement = connection.prepareStatement(query))
        {
            Date currentDate = new Date(System.currentTimeMillis());
            long userID = ThreadLocalRandom.current().nextLong(100000L, 9999999L);

            statement.setLong(1, userID);
            statement.setString(2, model.getName());
            statement.setInt(3, model.getAge());
            statement.setString(4, model.getAddress());
            statement.setString(5, model.getPhoneNo());
            statement.setString(6, model.getPassword());
            statement.setBigDecimal(7, model.getAmount());
            statement.setString(8, model.getSalt());
            statement.setDate(9, currentDate);
            statement.setString(10, model.getStatus());
            statement.setInt(11, model.getTenure_years());

            int result = statement.executeUpdate();

            String message = result > 0 ? "Sign Up Successful" : "Sign Up Failed";
            JOptionPane.showMessageDialog(null, message);
            return result > 0;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static UserModel UserLogin(String PhoneNo)
    {
        String query = "SELECT Password,Salt,Status FROM tbl_customer WHERE PhoneNo = ?";

        try (Connection connection = connectionStrings.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, PhoneNo);
            ResultSet rs = statement.executeQuery();
            UserModel user = new UserModel();
            if (!rs.next())
            {
                JOptionPane.showMessageDialog(null, "User not found!");
                return null;
            }
            user.setPassword(rs.getString("Password"));
            user.setSalt(rs.getString("Salt"));
            user.setStatus(rs.getString("Status"));

            return user;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static boolean deleteUser(String PhoneNo)
    {
        String deleteQuery = "delete from tbl_customer where PhoneNo=?";
        String deleteTransactionQuery = "delete from transaction_tbl where Phone_No=?";
        String deleteTransferQuery = "delete from tbl_transfer where SenderPhone=?";
        String deleteFixDepositQuery = "delete from tbl_fixed_deposit where Phone_No=?";

        try(Connection connection = connectionStrings.getConnection();
            PreparedStatement stmtDelete = connection.prepareStatement(deleteQuery);
            PreparedStatement stmtDeleteTransaction = connection.prepareStatement(deleteTransactionQuery);
            PreparedStatement stmtDeleteTransfer = connection.prepareStatement(deleteTransferQuery);
            PreparedStatement stmtDeleteFixDeposit = connection.prepareStatement(deleteFixDepositQuery)
        ){
            stmtDelete.setString(1, PhoneNo);
            int result1 = stmtDelete.executeUpdate();
            stmtDeleteTransaction.setString(1, PhoneNo);
            int result4;
            stmtDeleteTransaction.executeUpdate();
            stmtDeleteTransfer.setString(1, PhoneNo);
            stmtDeleteFixDeposit.setString(1, PhoneNo);
            boolean check = checkUserFromFixed(PhoneNo);
            result4 = stmtDeleteFixDeposit.executeUpdate();
            if (!check)
            {
                result4 = 1;
            }
            return result1 > 0 && result4 > 0;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static UserModel userVerifyForUpdate(String PhoneNo, String Password)
    {
        String query = "SELECT * FROM tbl_customer WHERE PhoneNo = ?";

        try(Connection connection = connectionStrings.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)
        ){
            statement.setString(1, PhoneNo);
            ResultSet rs = statement.executeQuery();
            UserModel user = new UserModel();
            if(rs.next()) {
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                user.setPhoneNo(rs.getString("PhoneNo"));
                user.setAmount(rs.getBigDecimal("Amount"));
                user.setSalt(rs.getString("Salt"));
            }
            byte[] salt = Base64.getDecoder().decode(user.getSalt());

            boolean result = verifyPassword(Password, user.getPassword(), salt);
            if(result)
            {
                return user;
            }
            else
            {
                JOptionPane.showMessageDialog(null,"Invalid password!");
                return null;
            }
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null,e.getMessage());
            return null;
        }
    }

    public static String updateUser(int id, String Name, String PhoneNo)
    {
        String query = "update tbl_customer set Name=?,PhoneNo=? where Id=?";

        try(Connection connection = connectionStrings.getConnection();
        PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setString(1, Name);
            statement.setString(2, PhoneNo);
            statement.setInt(3, id);
            int result = statement.executeUpdate();
            return result > 0 ? "Update successful" : "Update failed";
        }
        catch (Exception e)
        {
            return e.getMessage();
        }
    }

    public static void updatePassword(String PhoneNo, String Password)
    {
        String query = "update tbl_customer set Password=? where PhoneNo=?";

        try(Connection connection = connectionStrings.getConnection();
            PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setString(1, Password);
            statement.setString(2, PhoneNo);
            int result = statement.executeUpdate();
            String message = result > 0 ? "Update successful" : "Update failed";
            JOptionPane.showMessageDialog(null, message);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static boolean CheckUser(String PhoneNo)
    {
        String query = "select * from tbl_customer where PhoneNo = ?";

        return connection(PhoneNo, query);
    }

    public static UserModel getCustomer(String PhoneNo)
    {
        String query = "select * from tbl_customer where PhoneNo=?";

        try(Connection connection = connectionStrings.getConnection();
            PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setString(1,PhoneNo);
            ResultSet rs = statement.executeQuery();
            UserModel user = new UserModel();
            if(rs.next())
            {
                getUser(rs, user);
            }
            return user;
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private static void getUser(ResultSet rs, UserModel user) throws SQLException
    {
        user.setId(rs.getInt("User_ID"));
        user.setName(rs.getString("name"));
        user.setAge(rs.getInt("Age"));
        user.setAddress(rs.getString("Address"));
        user.setPassword(rs.getString("password"));
        user.setPhoneNo(rs.getString("PhoneNo"));
        user.setAmount(rs.getBigDecimal("Amount"));
        boolean check =  checkUserFromFixed(user.getPhoneNo());
        if(check)
        {
            user.setAmount(getMaturityAmount(user.getPhoneNo()));
        }
        user.setSalt(rs.getString("Salt"));
        user.setStatus(rs.getString("Status"));
        user.setDate(rs.getDate("Reg_Date"));
        user.setTenure_years(rs.getInt("tenure_years"));
    }

    public static boolean updateAmount(String PhoneNo, BigDecimal Amount)
    {
        String query = "update tbl_customer set Amount=? where PhoneNo=?";

        try(Connection connection = connectionStrings.getConnection();
            PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setBigDecimal(1,Amount);
            statement.setString(2,PhoneNo);

            int result = statement.executeUpdate();
            return result > 0;
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static  boolean restrictUser(String PhoneNo, String status)
    {
        String query = "update tbl_customer set Status=? where PhoneNo=?";

        try(Connection connection = connectionStrings.getConnection();
        PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setString(1, status);
            statement.setString(2, PhoneNo);

            int result = statement.executeUpdate();
            return result > 0;
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static List<TransactionModel> transactionLists(String PhoneNo)
    {
        String query = "select * from transaction_tbl where Phone_No=?";
        List<TransactionModel> lists = new ArrayList<>();
        try(Connection connection = connectionStrings.getConnection();
        PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setString(1, PhoneNo);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                TransactionModel transactionModel = new TransactionModel();
                transaction(transactionModel, resultSet);
                lists.add(transactionModel);
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return lists;
    }

    public static boolean setTransaction(TransactionModel model)
    {
        String query = "insert into transaction_tbl (Transaction_ID, User_ID, Phone_No," +
                " Transaction_Date, Transaction_Type, Amount, Total_amount) values(?,?,?,?,?,?,?)";

        try(Connection connection = connectionStrings.getConnection();
        PreparedStatement statement = connection.prepareStatement(query))
        {
            return setTransferTransaction(model, statement);
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static TransactionModel getTransaction(String PhoneNo, Long ID)
    {
        String query = "select * from transaction_tbl where Phone_No=? and Transaction_ID=?";
        TransactionModel transactionModel = new TransactionModel();
        try(Connection connection = connectionStrings.getConnection();
            PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setString(1, PhoneNo);
            statement.setLong(2, ID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
            {
                transaction(transactionModel, resultSet);
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return transactionModel;
    }

    private static void transaction(TransactionModel transactionModel, ResultSet resultSet) throws SQLException
    {
        transactionModel.setTransaction_ID(resultSet.getLong("Transaction_ID"));
        transactionModel.setUser_ID(resultSet.getLong("User_ID"));
        transactionModel.setPhone_No(resultSet.getString("Phone_No"));
        transactionModel.setTransaction_date(resultSet.getDate("Transaction_Date"));
        transactionModel.setTransaction_type(resultSet.getString("Transaction_Type"));
        transactionModel.setAmount(resultSet.getBigDecimal("Amount"));
        transactionModel.setTotal_amount(resultSet.getBigDecimal("Total_amount"));
    }

    public static boolean transferTransaction(TransactionModel model, UserModel user)
    {
        String query = "insert into transaction_tbl (Transaction_ID, User_ID, Phone_No," +
                " Transaction_Date, Transaction_Type, Amount, Total_amount) values(?,?,?,?,?,?,?)";
        String transferQuery = "insert into tbl_transfer " +
                "(User_ID,Transaction_ID, UserName, SenderPhone, ReceiverPhone, Transfer_fee) values(?,?,?,?,?,?)";

        try(Connection connection = connectionStrings.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            PreparedStatement stmtTransfer = connection.prepareStatement(transferQuery))
        {
            stmtTransfer.setLong(1, user.getId());
            stmtTransfer.setLong(2, model.getTransaction_ID());
            stmtTransfer.setString(3, user.getName());
            stmtTransfer.setString(4, model.getPhone_No());
            stmtTransfer.setString(5, user.getPhoneNo());
            stmtTransfer.setDouble(6, model.getTransfer_fee());
            int result = stmtTransfer.executeUpdate();
            return setTransferTransaction(model, statement) && result > 0;
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private static boolean setTransferTransaction(TransactionModel model, PreparedStatement statement)
            throws SQLException
    {
        statement.setLong(1, model.getTransaction_ID());
        statement.setLong(2, model.getUser_ID());
        statement.setString(3, model.getPhone_No());
        statement.setDate(4, model.getTransaction_date());
        statement.setString(5, model.getTransaction_type());
        statement.setBigDecimal(6, model.getAmount());
        statement.setBigDecimal(7, model.getTotal_amount());
        int result = statement.executeUpdate();
        return result > 0;
    }

    public static UserModel getTransferTransaction(String Phone_no, Long ID)
    {
        String query = "select * from tbl_transfer where SenderPhone=? AND Transaction_ID=?";
        UserModel user = new UserModel();

        try(Connection connection = connectionStrings.getConnection();
        PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setString(1, Phone_no);
            statement.setLong(2, ID);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next())
            {
                user.setId(resultSet.getLong("User_ID"));
                user.setName(resultSet.getString("UserName"));
                user.setPhoneNo(resultSet.getString("ReceiverPhone"));
            }

        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return user;
    }

    public static AmountModel amount()
    {
        String query = "select * from tbl_balance where PhoneNo=?";
        AmountModel Amount = new AmountModel();

        try(Connection connection = connectionStrings.getConnection();
        PreparedStatement statement = connection.prepareStatement(query))
        {
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next())
            {
                Amount.setUserID(resultSet.getLong("UserID"));
                Amount.setPhoneNo(resultSet.getString("PhoneNo"));
                Amount.setAmount(resultSet.getBigDecimal("Amount"));
                Amount.setLoan(resultSet.getBigDecimal("Loan"));
                Amount.setInterest(resultSet.getBigDecimal("Interest"));
                Amount.setTotalAmount(resultSet.getBigDecimal("TotalAmount"));
                Amount.setBankTotalAmount(resultSet.getBigDecimal("BankTotalAmount"));
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return Amount;
    }

    public static int calculateYears(LocalDate startDate, LocalDate endDate)
    {
        Period period = Period.between(startDate, endDate);
        return period.getYears();
    }

    public static void setFixedDeposit(TransactionModel model)
    {
        String query = "INSERT INTO tbl_fixed_deposit " +
                "(Deposit_ID,Phone_No, Principal, interest_rate, start_date, tenure_years,compound_frequency, maturity_amount)" +
                " values(?,?,?,?,?,?,?,?)";

        try(Connection connection = connectionStrings.getConnection();
        PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setLong(1, model.getTransaction_ID());
            statement.setString(2, model.getPhone_No());
            statement.setBigDecimal(3, model.getTotal_amount());
            statement.setDouble(4, model.getCompound_frequency());
            statement.setDate(5, model.getTransaction_date());
            statement.setInt(6, model.getTenure_years());
            statement.setInt(7, 1);
            statement.setBigDecimal(8, model.getTotal_amount());
            int result = statement.executeUpdate();
            String message = result > 0 ? "Saving successful." : "Saving failed.";
            JOptionPane.showMessageDialog(null, message);
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static void updateFixedDeposit(Long ID)
    {
        String query = "SELECT * FROM tbl_fixed_deposit WHERE Deposit_ID = ?";

        try(Connection connection = connectionStrings.getConnection();
        PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setLong(1, ID);
            ResultSet rs = statement.executeQuery();
             if(rs.next())
            {
                BigDecimal principal = rs.getBigDecimal("Principal");
                double Principal = principal.doubleValue();
                double annualRate = rs.getDouble("interest_rate");
                LocalDate startDate = rs.getDate("start_date").toLocalDate();
                int tenureYears = rs.getInt("tenure_years");
                int compoundFrequency = rs.getInt("compound_frequency");
                double maturityAmount = calculateMaturityAmount(Principal, annualRate, startDate, tenureYears, compoundFrequency);
                System.out.println("Maturity Amount: " + maturityAmount);

                Date currentDate = new Date(System.currentTimeMillis());
                int years = calculateYears(startDate, currentDate.toLocalDate());
                if (years == tenureYears)
                    updateMaturityAmount(ID, maturityAmount);

            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static double calculateMaturityAmount(double principal, double annualRate, LocalDate startDate,
                                                 int tenureYears, int compoundFrequency)
    {
        double rate = annualRate / 100;
        LocalDate endDate = startDate.plusYears(tenureYears);
        long totalPeriods = ChronoUnit.MONTHS.between(startDate, endDate) / (12 / compoundFrequency);
        System.out.println(totalPeriods);
        return principal * Math.pow(1 + rate / compoundFrequency, compoundFrequency * tenureYears);
    }

    public static void updateMaturityAmount(Long depositId, double maturityAmount)
    {
        String query = "update tbl_fixed_deposit set maturity_amount=? where Deposit_ID=?";

        try(Connection connection = connectionStrings.getConnection();
        PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setBigDecimal(1, BigDecimal.valueOf(maturityAmount));
            statement.setLong(2, depositId);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Maturity amount updated successfully.");
            } else {
                System.out.println("No rows updated. Please check the deposit ID.");
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static List<LoanModel> getLoans()
    {
        String query = "select * from tbl_loan";
        List<LoanModel> loanLists = new ArrayList<>();

        try(Connection connection = connectionStrings.getConnection();
        PreparedStatement statement = connection.prepareStatement(query))
        {
            ResultSet rs = statement.executeQuery();
            while (rs.next())
            {
                LoanModel loan = new LoanModel();
                getLoanDetails(loan, rs);
                loanLists.add(loan);
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return loanLists;
    }

    public static boolean setLoan(LoanModel loan)
    {
        String query = "insert into tbl_loan " +
                "(LoanID, Name, email, Phone_No, DOB, address, LoanAmount, interestRate," +
                "NRC, Job, assets, collateral, LoanType, timeFrame, income, maritalStatus,tenure_years, EMI, Status)" +
                "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try(Connection connection = connectionStrings.getConnection();
        PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setLong(1, loan.getLoanID());
            statement.setString(2, loan.getName());
            statement.setString(3, loan.getEmail());
            statement.setString(4, loan.getPhone());
            statement.setDate(5, loan.getDOB());
            statement.setString(6, loan.getAddress());
            statement.setBigDecimal(7, loan.getLoanAmount());
            statement.setDouble(8, loan.getInterestRate());
            statement.setString(9, loan.getNRC());
            statement.setString(10, loan.getJob());
            statement.setString(11, loan.getAsset());
            statement.setString(12, loan.getCollateral());
            statement.setString(13, loan.getLoanType());
            statement.setString(14, loan.getTimeFrame());
            statement.setString(15, loan.getIncome());
            statement.setString(16, loan.getMaritalStatus());
            statement.setInt(17, loan.getTenureYears());
            statement.setBigDecimal(18, loan.getEMI());
            statement.setString(19, loan.getStatus());
            int result = statement.executeUpdate();
            String message = result > 0 ? "Saving successful." : "Saving failed.";
            JOptionPane.showMessageDialog(null, message);
            return result > 0;
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static LoanModel getLoan(Long ID)
    {
        String query = "select * from tbl_loan where LoanID = ?";
        LoanModel loan = new LoanModel();

        try(Connection connection = connectionStrings.getConnection();
        PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setLong(1, ID);
            ResultSet rs = statement.executeQuery();
            if(rs.next())
            {
                getLoanDetails(loan, rs);
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return loan;
    }

    private static void getLoanDetails(LoanModel loan, ResultSet rs) throws SQLException
    {
        loan.setLoanID(rs.getLong("LoanID"));
        loan.setName(rs.getString("Name"));
        loan.setEmail(rs.getString("email"));
        loan.setPhone(rs.getString("Phone_No"));
        loan.setDOB(rs.getDate("DOB"));
        loan.setAddress(rs.getString("address"));
        loan.setLoanAmount(rs.getBigDecimal("LoanAmount"));
        loan.setInterestRate(rs.getDouble("interestRate"));
        loan.setNRC(rs.getString("NRC"));
        loan.setJob(rs.getString("Job"));
        loan.setAsset(rs.getString("assets"));
        loan.setCollateral(rs.getString("collateral"));
        loan.setLoanType(rs.getString("LoanType"));
        loan.setTimeFrame(rs.getString("timeFrame"));
        loan.setIncome(rs.getString("income"));
        loan.setMaritalStatus(rs.getString("maritalStatus"));
        loan.setEMI(rs.getBigDecimal("EMI"));
        loan.setTenureYears(rs.getInt("tenure_years"));
        loan.setStatus(rs.getString("Status"));
    }

    public static void deleteLoan(Long ID)
    {
        String query = "delete from tbl_loan where LoanID = ?";

        try(Connection connection = connectionStrings.getConnection();
        PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setLong(1, ID);
            int result = statement.executeUpdate();
            String message = result > 0 ? "Deleting successful." : "Deleting failed.";
            JOptionPane.showMessageDialog(null, message);
            refreshData();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static boolean checkUserFromFixed(String PhoneNo)
    {
        String query = "select * from tbl_fixed_deposit where Phone_No = ?";

        return connection(PhoneNo, query);
    }

    private static boolean connection(String PhoneNo, String query) {
        try(Connection connection = connectionStrings.getConnection();
            PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setString(1, PhoneNo);
            ResultSet rs = statement.executeQuery();
            return rs.next();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private static BigDecimal getMaturityAmount(String PhoneNo)
    {
        String query = "select maturity_amount from tbl_fixed_deposit where Phone_No=?";
        BigDecimal totalAmount = BigDecimal.valueOf(0.0);

        try (Connection connection = connectionStrings.getConnection();
        PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setString(1, PhoneNo);
            ResultSet rs = statement.executeQuery();
            if (rs.next())
            {
                totalAmount = rs.getBigDecimal("maturity_amount");
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return totalAmount;
    }

    public static int getYears(String PhoneNo)
    {
        String query = "select * from tbl_fixed_deposit where Phone_No=?";
        int Years = 0;

        try (Connection connection = connectionStrings.getConnection();
        PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setString(1, PhoneNo);
            ResultSet rs = statement.executeQuery();
            LocalDate startDate = rs.getDate("start_date").toLocalDate();
            Date currentDate = new Date(System.currentTimeMillis());
            Years = calculateYears(startDate, currentDate.toLocalDate());
            if (rs.next())
            {
                Years = calculateYears(startDate, currentDate.toLocalDate());
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return Years;
    }

    public static int getTenureYears(String PhoneNo)
    {
        String query = "select tenure_years from tbl_fixed_deposit where Phone_No=?";
        int tenureYears = 0;

        try (Connection connection = connectionStrings.getConnection();
             PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setString(1, PhoneNo);
            ResultSet rs = statement.executeQuery();
            if (rs.next())
            {
                tenureYears = rs.getInt("tenure_years");
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return tenureYears;
    }
}