package bo.custom.impl;

import bo.custom.ManageOrderBO;
import dao.DAOFactory;
import dao.custom.OrderDetailDAO;
import dto.OrderDetailDTO;
import entity.OrderDetail;

import java.sql.SQLException;
import java.util.ArrayList;

public class ManageOrderBOImpl implements ManageOrderBO {

    private final OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getDaoFactory().getDAOType(DAOFactory.DAOTypes.ORDER_DETAIL);


    @Override
    public ArrayList<OrderDetailDTO> getOrderDetail(String id) throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetailDTO> allOrderDetails = new ArrayList<>();
        ArrayList<OrderDetail> all = orderDetailDAO.getAllOrderDetails(id);
        for (OrderDetail orderDetail : all) {
            allOrderDetails.add(new OrderDetailDTO(orderDetail.getItemCode(),id,orderDetail.getOrderQty(),orderDetail.getPrice(),orderDetail.getDiscount()));
        }
        return allOrderDetails;
    }
}
