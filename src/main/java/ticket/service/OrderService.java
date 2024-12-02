package ticket.service;

import java.util.ArrayList;
import java.util.List;

import ticket.model.dto.OrderDto;
import ticket.model.entity.Order;
import ticket.repository.OrderDao;
import ticket.repository.OrderDaoImpl;

public class OrderService {
	private OrderDao orderDao = new OrderDaoImpl();
	
	public List<OrderDto> findAllOrders(String userId) {
		List<OrderDto> orderDtos = new ArrayList<OrderDto>();
		List<Order> orders = orderDao.findAllOrders(Integer.parseInt(userId));
		for (Order order : orders) {
			OrderDto orderDto = new OrderDto();
			orderDto.setOrderId(order.getOrderId());
			orderDto.setUserId(order.getUserId());
			orderDto.setEventName(order.getEventName());
			orderDto.setOrderPrice(order.getOrderPrice());
			orderDto.setOrderDate(order.getOrderDate());
			orderDto.setOrderStatus(order.getOrderStatus());
			
			orderDtos.add(orderDto);
		}
		return orderDtos;
	}
	
	public List<OrderDto> getOrderSeats(String orderId) {
		List<OrderDto> orderDtos = new ArrayList<OrderDto>();
		List<Order> orders = orderDao.getOrderSeats(Integer.parseInt(orderId));

		for(Order order : orders) {
			OrderDto orderDto = new OrderDto();
			orderDto.setOrderId(order.getOrderId());
			orderDto.setSeatId(order.getSeatId());
			orderDto.setCategoryName(order.getCategoryName());
			orderDto.setSeatNumber(order.getSeatNumber());
			
			orderDtos.add(orderDto);
		}
		return orderDtos;
	}
	
	public Integer addOrder(String userId, String eventName, String orderPrice, String orderDate) {
		Integer orderId = orderDao.addOrder(Integer.parseInt(userId), eventName, Integer.parseInt(orderPrice), orderDate);
		return orderId;
	}
	
	public void addOrderSeats(Integer orderId , String[] seatIds, String[] categoryNames, String[] seatNumbers) {
		List<Order> orders = new ArrayList<Order>();
		
		for(int i=0;i<seatIds.length;i++) {
			Integer seatId = Integer.parseInt(seatIds[i]);
			String categoryName = categoryNames[i];
			Integer seatNumber = Integer.parseInt(seatNumbers[i]);
			
			Order order = new Order();
			order.setOrderId(orderId);
			order.setSeatId(seatId);
			order.setCategoryName(categoryName);
			order.setSeatNumber(seatNumber);
			
			orders.add(order);
		}
		orderDao.addOrderSeats(orders);
	}
	
}
