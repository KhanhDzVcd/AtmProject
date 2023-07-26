package service_impl;
import database.*;
import Object.UserOBJ;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class UserService_impl implements service.UserService{
    public String sdf;
    Connection connection = DbConn.getInstance().getConnection();
    UserOBJ userOBJ = new UserOBJ();
    DAO dao = new DAO();
    Scanner sc = new Scanner(System.in);
    String account_number, account_passcode, account_holder;
    double account_balance;
    int account_id;
//    TransService_impl transService_impl = new TransService_impl();

    @Override
    public boolean login() {
        try {
            String account_number, account_passcode;
            System.out.print("Enter account number: ");
            account_number = sc.nextLine();
            System.out.print("Enter account passcode: ");
            account_passcode = sc.nextLine();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM account WHERE account_number = ? AND account_passcode = ?");
            preparedStatement.setString(1,account_number);
            preparedStatement.setString(2,account_passcode);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                account_id = resultSet.getInt("account_id");
                account_number = resultSet.getString("account_number");
                account_holder = resultSet.getString("account_holder");
                account_balance = resultSet.getDouble("account_balance");
                account_passcode = resultSet.getString("account_passcode");
                System.out.println("name "+account_holder);
            }
            if(resultSet == null){
                System.out.println("Account not found!");
                return false;
            }else {
                setUser(userOBJ);
                preparedStatement.close();
                sc.close();
                return true;
            }



        }catch (Exception e){
            e.printStackTrace();
        }return false;
    }
    @Override
    public void setUser(UserOBJ user) {
        this.userOBJ = user;
        user.setAccount_id(account_id);
        user.setAccount_number(account_number);
        user.setAccount_holder(account_holder);
        user.setAccount_balance(account_balance);
        user.setAccount_passcode(account_passcode);
    }

    @Override
    public boolean register() {
        return dao.insertUser();
    }
    public void viewInfo(){
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM account WHERE account_number = ?");
            preparedStatement.setString(1,userOBJ.getAccount_number());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                System.out.println("Account number: " + resultSet.getString("account_number"));
                System.out.println("Account holder: " + resultSet.getString("account_holder"));
                System.out.println("Account balance: $" + resultSet.getDouble("account_balance"));
            }
            preparedStatement.close();
            sc.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void viewTransactionHistory(){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM transaction WHERE from_account_id = ?");
            preparedStatement.setInt(1,userOBJ.getAccount_id());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                System.out.println("Transaction ID: "+ resultSet.getInt("trans_id")
                        + ", From account number: " + resultSet.getString("from_account_number")
                        + ", To account number: " + resultSet.getString("to_account_number")
                        + ", Type: " + resultSet.getString("trans_type")
                        + ", Amount: $" + resultSet.getDouble("trans_amount")
                        + ", Time: " + resultSet.getTimestamp("trans_date")
                        + ", Status: "+ resultSet.getString("trans_status")
                );

            }
            preparedStatement.close();
            sc.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean deposit() {
        double amount;
        try {
            System.out.print("Enter amount to deposit: ");
            amount = sc.nextDouble();
            if(amount <= 0){
                System.out.println("Invalid amount!");
            }
            else{
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE account SET account_balance = account_balance + ? WHERE account_number = ?");
            preparedStatement.setDouble(1,amount);
            preparedStatement.setString(2,userOBJ.getAccount_number());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            sc.close();
            System.out.println("Deposit successfully!");
//            transService_impl.deposit(amount,userOBJ);
            return true;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean withdraw() {
        try {
            System.out.print("Enter amount to withdraw: ");
            double amount = sc.nextDouble();
            if(amount <= 0){
                System.out.println("Invalid amount!");
            }
            else if(amount > userOBJ.getAccount_balance()){
                System.out.println("Not enough money!");
            }
            else{
                String account_passcode;
                System.out.print("Enter account passcode: ");
                account_passcode = sc.next();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE account SET account_balance = account_balance - ? WHERE account_number = ? AND account_passcode = ?");
            preparedStatement.setDouble(1,amount);
            preparedStatement.setString(2,userOBJ.getAccount_number());
            preparedStatement.setString(3,account_passcode);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            sc.close();
            System.out.println("Withdraw successfully!");
            return true;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean transfer() {
        try {
            System.out.print("Enter account number to transfer: ");
            String account_number = sc.nextLine();
            if(account_number.equals(userOBJ.getAccount_number())){
                System.out.println("Cannot transfer to your own account!");
            }
            else if(!dao.checkAccount(account_number)){
                System.out.println("Account not found!");
            }
            else{
            System.out.print("Enter amount to transfer: ");
            double amount = sc.nextDouble();
            if(amount <= 0){
                System.out.println("Invalid amount!");
            }
            else if(amount > userOBJ.getAccount_balance()){
                System.out.println("Not enough money!");
            }
            else{
                String account_passcode;
                System.out.print("Enter account passcode: ");
                account_passcode = sc.next();

            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE account SET account_balance = account_balance - ? WHERE account_number = ? AND account_passcode = ?"
                    + "UPDATE account SET account_balance = account_balance + ? WHERE account_number = ?");
            preparedStatement.setDouble(1,amount);
            preparedStatement.setString(2,userOBJ.getAccount_number());
            preparedStatement.setString(3,account_passcode);
            preparedStatement.setDouble(4,amount);
            preparedStatement.setString(5,account_number);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            sc.close();
            System.out.println("Transfer successfully!");
            return true;
            }
            }
        }catch (Exception e) {
            System.out.println();
            e.printStackTrace();
        }
        return false;
    }
    public void logout(){
        System.out.println("Logout successfully!");
        this.userOBJ = null;
    }
}
