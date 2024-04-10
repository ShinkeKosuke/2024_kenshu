package com.example.demo.usecase;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class Schedule {
	private Integer day;

	private String date;

	private Integer scheduleCount;
}