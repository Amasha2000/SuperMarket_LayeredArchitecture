package bo.custom;

import bo.SuperBO;
import dto.OrderDetailDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ManageOrderBO extends SuperBO {
    ArrayList<OrderDetailDTO> getOrderDetail(String id) throws SQLException, ClassNotFoundException;
}
