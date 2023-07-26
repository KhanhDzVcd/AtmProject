//package service_impl;
//import database.DbConn;
//import service.TransService;
//import Object.UserOBJ;
//import java.sql.*;
//
//public class TransService_impl implements TransService {
//    Connection connection = DbConn.getInstance().getConnection();
//    UserService_impl userService_impl = new UserService_impl();
//    @Override
//    public void deposit(Double amount, UserOBJ userOBJ){
//        try{
//        if(userService_impl.deposit()){
//            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO transaction(trans_date,trans_amount,trans_type,from_account,to_account,trans_status) VALUES (NOW(),?,?,?,?,?)");
//            preparedStatement.setDouble(1,amount);
//            preparedStatement.setString(2,"Deposit");
//            preparedStatement.setInt(3,userOBJ.getAccount_id());
//            preparedStatement.setInt(4,userOBJ.getAccount_id());
//            preparedStatement.setString(5,"Success");
//            preparedStatement.executeUpdate();
//            preparedStatement.close();
//        }else {
//            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO transaction(trans_date,trans_amount,trans_type,from_account,to_account,trans_status) VALUES (NOW(),?,?,?,?,?)");
//            preparedStatement.setDouble(1, amount);
//            preparedStatement.setString(2, "Deposit");
//            preparedStatement.setInt(3, userOBJ.getAccount_id());
//            preparedStatement.setInt(4, userOBJ.getAccount_id());
//            preparedStatement.setString(5, "Failed");
//            preparedStatement.executeUpdate();
//            preparedStatement.close();
//        }
//        }catch (SQLException e){
//            e.printStackTrace();
//        }
//
//    }
//
//    @Override
//    public void withdraw(Double amount, UserOBJ userOBJ) {
//        try {
//            if(userService_impl.withdraw()){
//                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO transaction(trans_date,trans_amount,trans_type,from_account,to_account,trans_status) VALUES (NOW(),?,?,?,?,?)");
//                preparedStatement.setDouble(1,amount);
//                preparedStatement.setString(2,"Withdraw");
//                preparedStatement.setInt(3,userOBJ.getAccount_id());
//                preparedStatement.setInt(4,userOBJ.getAccount_id());
//                preparedStatement.setString(5,"Success");
//                preparedStatement.executeUpdate();
//                preparedStatement.close();
//            }else {
//                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO transaction(trans_date,trans_amount,trans_type,from_account,to_account,trans_status) VALUES (NOW(),?,?,?,?,?)");
//                preparedStatement.setDouble(1,amount);
//                preparedStatement.setString(2,"Withdraw");
//                preparedStatement.setInt(3,userOBJ.getAccount_id());
//                preparedStatement.setInt(4,userOBJ.getAccount_id());
//                preparedStatement.setString(5,"Failed");
//                preparedStatement.executeUpdate();
//                preparedStatement.close();
//            }
//        }catch (SQLException e){
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void transfer(Double amount, UserOBJ userOBJ, int account_id){
//        try {
//            if(userService_impl.transfer()){
//                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO transaction(trans_date,trans_amount,trans_type,from_account,to_account,trans_status) VALUES (NOW(),?,?,?,?,?)");
//                preparedStatement.setDouble(1,amount);
//                preparedStatement.setString(2,"Transfer");
//                preparedStatement.setInt(3,userOBJ.getAccount_id());
//                preparedStatement.setInt(4,account_id);
//                preparedStatement.setString(5,"Success");
//                preparedStatement.executeUpdate();
//                preparedStatement.close();
//            }else {
//                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO transaction(trans_date,trans_amount,trans_type,from_account,to_account,trans_status) VALUES (NOW(),?,?,?,?,?)");
//                preparedStatement.setDouble(1,amount);
//                preparedStatement.setString(2,"Transfer");
//                preparedStatement.setInt(3,userOBJ.getAccount_id());
//                preparedStatement.setInt(4,account_id);
//                preparedStatement.setString(5,"Failed");
//                preparedStatement.executeUpdate();
//                preparedStatement.close();
//            }
//        }catch (SQLException e){
//            e.printStackTrace();
//        }
//
//    }
//}
