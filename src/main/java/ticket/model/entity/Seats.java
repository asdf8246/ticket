package ticket.model.entity;

import lombok.Data;

/**
 CREATE TABLE seats (
    seat_id INT AUTO_INCREMENT PRIMARY KEY,
    event_id INT NOT NULL,  -- 外鍵連接到活動
    seat_category_id INT NOT NULL,  -- 外鍵連接到座位等級
    seat_number VARCHAR(255) NOT NULL,  -- 座位編號，如 A1、B3 等
    seat_status ENUM('available', 'reserved', 'sold') DEFAULT 'available',  -- 座位狀態
    FOREIGN KEY (event_id) REFERENCES events(event_id),
    FOREIGN KEY (seat_category_id) REFERENCES seat_categories(seat_category_id)
);
 */

@Data
public class Seats {
	private Integer seatId;
	private Integer eventId;
	private Integer seatCategoryId;
	private String seatNumber;
	private String seatStatus;
	
}
