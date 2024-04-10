package com.example.demo.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.common.DataNotFoundException;
import com.example.demo.dao.EventDao;
import com.example.demo.entity.Event;
import com.example.demo.usecase.EventCount;
import com.example.demo.usecase.Schedule;
import com.example.demo.util.DateFormatUtil;

@Service
public class EventService implements BaseService<Event> {
	@Autowired
	private EventDao dao;

	@Override
	public List<Event> findAll() {
		return dao.findAll();
	}

	@Override
	public Event findById(Integer id) throws DataNotFoundException {
		return dao.findById(id);
	}
	
	
	public List<Event> findByEventDateAndIsDeleteNotOrderByUserId(String eventDate) {
		return dao.findByEventDateAndIsDeleteNotOrderByUserId(eventDate);
	}

	@Override
	public void save(Event tweet) {
		dao.save(tweet);
	}

	@Override
	public void deleteById(Integer id) {
		dao.deleteById(id);
	}
	
	public List<EventCount> findMonthlyEvent(String satartDate, String endDate) {
		return dao.findMonthlyEvent(satartDate, endDate);
	}
	
	public ArrayList<ArrayList<Object>> createCalender(Integer year, Integer month){
		ArrayList<ArrayList<Object>> calendarBoxNum = new ArrayList<ArrayList<Object>>();
		ArrayList<Object> weekBox = new ArrayList<>();
		
		Calendar calendar  = Calendar.getInstance();
		calendar.set(year, month, 1);
		int firstDayWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1; 
		int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

		List<EventCount> events = this.findMonthlyEvent(
				DateFormatUtil.getYearMonthDay(year, month, 1),
				DateFormatUtil.getYearMonthDay(year, month + 1, 1)
				);
		
		
		for (Integer day = 1; day <= lastDay; day++) {
			Schedule schedule = new Schedule();
			if (day == 1) {
				//初日が何曜日かに合わせて空白を出力
				for (int i = 0; i < firstDayWeek; i++){
					weekBox.add(schedule);
					schedule = new Schedule();
				}
			}
			schedule.setDate(DateFormatUtil.getYearMonthDay(year, month, day));
			schedule.setDay(day);
			for (EventCount event : events) {
				if (Integer.parseInt(event.getEventDate().substring(6, 8)) == day) {
					schedule.setScheduleCount(event.getEventCount());
				}
			}

			weekBox.add(schedule);
			if ((firstDayWeek + day) % 7 == 0) {
				calendarBoxNum.add(weekBox);
				weekBox = new ArrayList<>();
			}
			if (lastDay == day){
				Integer week = (firstDayWeek + day) % 7;
				if (week != 0) {
					for (int i = week; i < 7; i++) {
						schedule = new Schedule();
						weekBox.add(schedule);
					}
				}
				calendarBoxNum.add(weekBox);
			}
		}		
		return calendarBoxNum;
	}
}
