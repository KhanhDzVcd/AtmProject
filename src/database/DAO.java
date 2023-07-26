package database;
import java.text.SimpleDateFormat;
import java.sql.*;
import java.util.*;
public class DAO {
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
    Scanner sc = new Scanner(System.in);
    private final Connection connection;
    public DAO(){
        connection = DbConn.getInstance().getConnection();
    }
    //Basic CRUD
    public boolean checkAccount(String account_number){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM account WHERE account_number = ?");
            preparedStatement.setString(1,account_number);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                preparedStatement.close();
                sc.close();
                return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean insertUser(){
        try {
            String account_number, account_holder, account_passcode;
            System.out.print("Enter account number: ");
            account_number = sc.nextLine();
            System.out.print("Enter account holder: ");
            account_holder = sc.nextLine();
            System.out.print("Enter account passcode: ");
            account_passcode = sc.next();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO account(account_number,account_holder,account_balance,account_passcode) VALUES (?,?,?,?)");
            preparedStatement.setString(1,account_number);
            preparedStatement.setString(2,account_holder);
            preparedStatement.setDouble(3,0);
            preparedStatement.setString(4,account_passcode);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            sc.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return false;
    }
    public void viewUser(){
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM account");
            while (resultSet.next()){
                System.out.print("ID: " + resultSet.getInt("account_id")+
                        ", Number: " + resultSet.getString("account_number")+
                        ", Holder: " + resultSet.getString("account_holder")+
                        ", Balance: " + resultSet.getDouble("account_balance")+
                        ", Passcode: " + resultSet.getString("account_passcode")+
                        ", Admin: "+resultSet.getBoolean("is_admin")+"\n");
            }
            statement.close();
            sc.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewTransaction(){
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM transaction");
            while (resultSet.next()){

                System.out.print("ID: " + resultSet.getInt("trans_id")+
                        ", Type: " + resultSet.getString("trans_type")+
                        ", Amount: $" + resultSet.getDouble("trans_amount")+
                        ", Time: " + sdf.format(resultSet.getTimestamp("trans_date")) +
                        ", From account ID: " + resultSet.getInt("from_account_id")+
                        ", To account ID: "+resultSet.getInt("to_account_id")+
                        ", Status: "+resultSet.getString("trans_status")+"\n");
            }
            statement.close();
            sc.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    //Advance Retrieve
    public void findAccount(){
        try{
            String account_number;
            System.out.print("Enter account number: ");
            account_number = sc.nextLine();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM account WHERE account_number = ?");
            preparedStatement.setString(1,account_number);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                System.out.print("ID: " + resultSet.getInt("account_id")+
                        ", Number: " + resultSet.getString("account_number")+
                        ", Holder: " + resultSet.getString("account_holder")+
                        ", Balance: " + resultSet.getDouble("account_balance")+
                        ", Passcode: " + resultSet.getString("account_passcode")+
                        ", Admin: "+resultSet.getBoolean("is_admin")+"\n");
            }
            preparedStatement.close();
            sc.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void findTransaction(){
        try {
            int trans_id;
            System.out.println("Enter trans_id: ");
            trans_id = sc.nextInt();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM transaction WHERE trans_id =?");
            preparedStatement.setInt(1,trans_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                System.out.print("ID: "+resultSet.getInt("trans_id")+
                        ", Time: "+sdf.format(resultSet.getTimestamp("trans_date"))+
                        ", From account ID: "+resultSet.getInt("from_account_id")+
                        ", To account ID: "+resultSet.getInt("to_account_id")+
                        ", Status: "+resultSet.getString("trans_status")+"\n");
            }
            preparedStatement.close();
            sc.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    //Statistics
    public void totalAccount(){
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*),SUM(account_balance) FROM account");
            while (resultSet.next()){
                System.out.println("Total account: "+resultSet.getInt("count"));
                System.out.println("Total balance: $"+resultSet.getDouble("sum"));
            }
            statement.close();
            sc.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void totalTransaction(){
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*),SUM(trans_amount) FROM transaction");
            while (resultSet.next()){
                System.out.println("Total transaction: "+resultSet.getInt("count"));
                System.out.println("Total amount: $"+resultSet.getDouble("sum"));
            }
            statement.close();
            sc.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }


}