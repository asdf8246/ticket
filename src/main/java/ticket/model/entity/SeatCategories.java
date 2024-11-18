package ticket.model.entity;


/**
 CREATE TABLE seat_categories (
    seat_category_id INT AUTO_INCREMENT PRIMARY KEY,
    event_id INT NOT NULL,  -- 外鍵連接到活動
    category_name VARCHAR(255) NOT NULL,  -- 座位等級名稱（如：VIP、普通等）
    price DECIMAL(10, 2) NOT NULL,  -- 票價
    num_seats INT NOT NULL,  -- 該區域有多少個座位
    FOREIGN KEY (event_id) REFERENCES events(event_id)  //外鍵約束 參照 events 的 event_id 欄位
);
 */


public class SeatCategories {
	private Integer seatCategoryId;
	private Integer eventId;
	private String categoryName;
	private Double price;
	private Integer numSeats;
}