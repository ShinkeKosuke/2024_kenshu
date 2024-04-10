package com.example.demo.entity;

import com.example.demo.common.ValidationGroups.Create;
import com.example.demo.common.ValidationGroups.Update;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "events")
public class Event {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	@NotNull(message = "ユーザIDは必須入力です")
	private User user;

	@Column(nullable = false, name="event_date")
	@NotEmpty(groups = { Create.class, Update.class }, message = "イベント年月日が未入力です")
	private String eventDate;

	@Column(nullable = false, name="event_allday")
	private Integer eventAllday;

	@Column(nullable = false, name="event_start_time")
	private String eventStartTime;

	@Column(nullable = false, name="event_end_time")
	private String eventEndTime;

	@Column(nullable = false, name="event_name")
	@NotEmpty(groups = { Create.class, Update.class }, message = "イベント名が未入力です")
	private String eventName;

	@Column(nullable = false)
	@NotEmpty(groups = { Create.class, Update.class }, message = "メモが未入力です")
	private String memo;

	@Column(nullable = false, name="registered_user_id" )
	@JoinColumn(name = "user_id")
	private Integer registeredUserId;

	@Column(nullable = false, name="is_delete")
	private Integer isDelete;
}
