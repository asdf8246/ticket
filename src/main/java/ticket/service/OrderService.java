package ticket.service;

import java.text.SimpleDateFormat;
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
	
	public Integer addOrder(Integer userId, String eventName, String[] orderPrices, String[] numSeats, String orderDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Integer orderPrice = 0;
		for(int i=0;i<orderPrices.length;i++) {
			Integer price = Integer.parseInt(orderPrices[i]);
			Integer numSeat = Integer.parseInt(numSeats[i]);
			
			orderPrice = (price * numSeat) + orderPrice;
		}
		
		Integer orderId = orderDao.addOrder(userId, eventName, orderPrice, sdf.format(orderDate));
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
