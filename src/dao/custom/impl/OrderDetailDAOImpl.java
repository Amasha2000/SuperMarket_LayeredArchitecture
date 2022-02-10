package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.OrderDetailDAO;
import entity.OrderDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailDAOImpl implements OrderDetailDAO {
    @Override
    public boolean add(OrderDetail dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO `Order Detail` VALUES(?,?,?,?,?)",dto.getItemCode(),dto.getOrderId(),dto.getOrderQty(),dto.getPrice(),dto.getDiscount());
    }

    @Override
    public boolean update(OrderDetail orderDetailDAO) {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public boolean delete(String s) {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public OrderDetail search(String s) {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public ArrayList<OrderDetail> getAll() {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public ArrayList<OrderDetail> getAllOrderDetails(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst=CrudUtil.executeQuery("SELECT * FROM `Order Detail` WHERE orderId=?",id);
        ArrayList<OrderDetail> orderDetails=new ArrayList<>();
        while (rst.next()){
            orderDetails.add(new OrderDetail(rst.getString("itemCode"),id,rst.getInt("orderQty"),rst.getDouble("price"),rst.getDouble("discount")));
        }
        return  orderDetails;
    }
}
