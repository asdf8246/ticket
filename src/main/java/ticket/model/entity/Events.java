package ticket.model.entity;

/**
CREATE TABLE events (
    event_id INT AUTO_INCREMENT PRIMARY KEY comment '活動ID',
    event_name VARCHAR(255) NOT NULL comment '活動名稱',
    event_date DATETIME NOT NULL comment '活動日期',
    venue VARCHAR(255) NOT NULL comment '活動地點',
    description TEXT  comment '活動簡介'  // TEXT：文本型，通常用來儲存較長的文字資料
);
 */


public class Events {
	private Integer eventId;
	private String eventName;
	private String eventDate;
	private String venue;
	private String description;
}
