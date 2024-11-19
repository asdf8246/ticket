package ticket.model.entity;

import lombok.Data;

/**
 CREATE TABLE tickets (
    ticket_id INT AUTO_INCREMENT PRIMARY KEY,
    event_id INT NOT NULL,  -- 外鍵連接到活動
    user_id INT NOT NULL,  -- 預訂者的用戶 ID
    seat_id INT NOT NULL,  -- 外鍵連接到座位
    price DECIMAL(10, 2) NOT NULL,  -- 實際支付的票價
    order_date DATETIME NOT NULL,
    ticket_status ENUM('pending', 'paid', 'canceled') DEFAULT 'pending',  -- 訂單狀態
    FOREIGN KEY (event_id) REFERENCES events(event_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (seat_id) REFERENCES seats(seat_id)
);
 */

@Data
public class Tickets {
	private Integer ticketId;
	private Integer eventId;
	private Integer userId;
	private Integer seatId;
	private Double price;
	private String orderDate;
	private String ticketStatus;
}
