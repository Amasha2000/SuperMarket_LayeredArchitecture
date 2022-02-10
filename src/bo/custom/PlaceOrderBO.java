package bo.custom;

import bo.SuperBO;
import dto.OrderDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PlaceOrderBO extends SuperBO {
    boolean addOrder(OrderDTO orderDTO) throws SQLException, ClassNotFoundException;
    boolean updateOrder(OrderDTO orderDTO) throws SQLException, ClassNotFoundException;
    boolean deleteOrder(String id) throws SQLException, ClassNotFoundException;
    OrderDTO search(String id) throws SQLException, ClassNotFoundException;
    ArrayList<OrderDTO> getAllOrders() throws SQLException, ClassNotFoundException;
    String getOrderId() throws SQLException, ClassNotFoundException;
}
