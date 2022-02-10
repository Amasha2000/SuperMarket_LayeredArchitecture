package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.OrderDAO;
import entity.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public boolean add(Order order) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO `Order` VALUES(?,?,?,?,?)",order.getOrderId(),order.getCustId(),order.getOrderDate(),order.getTime(),order.getCost());
    }

    @Override
    public boolean update(Order order) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE `Order` SET custId=?, orderDate=?, time=?, cost=? WHERE orderId=?",order.getCustId(),order.getOrderDate(),order.getTime(),order.getCost(),order.getOrderId());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM `Order` WHERE orderId=?",id);
    }

    @Override
    public Order search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst=CrudUtil.executeQuery( "SELECT * FROM `Order` WHERE orderId=?");
        rst.next();
        return new Order(id,rst.getString("custId"),rst.getString("orderDate"),rst.getString("time"),rst.getDouble("cost"));
    }

    @Override
    public ArrayList<Order> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Order> orders=new ArrayList<>();
        ResultSet rst=CrudUtil.executeQuery("SELECT * FROM `Order`");
        while (rst.next()){
             orders.add(new Order(rst.getString("orderId"),rst.getString("custId"),rst.getString("orderDate"),rst.getString("time"),rst.getDouble("cost")));
        }
        return orders;
    }

    @Override
    public String getOrderId() throws SQLException, ClassNotFoundException {
        ResultSet rst=CrudUtil.executeQuery("SELECT orderId FROM `Order` ORDER BY orderId DESC LIMIT 1");
        if(rst.next()){
            int index=Integer.parseInt(rst.getString(1).split("-")[1]);
            index=index+1;
            if(index<10){
                return "O-000"+ index;
            }else if(index<100){
                return "O-00"+ index;
            }else if(index<1000){
                return "O-0"+ index;
            }else{
                return "O-"+ index;
            }
        }else{
            return "O-0001";
        }
    }
}
