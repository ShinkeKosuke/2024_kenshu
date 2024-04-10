package com.example.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.common.DataNotFoundException;
import com.example.demo.common.FlashData;
import com.example.demo.entity.Event;
import com.example.demo.service.EventService;
import com.example.demo.service.UserService;
import com.example.demo.util.DateFormatUtil;


@Controller
@RequestMapping(value = { "/admin" })
public class EventsController {
	@Autowired
	EventService eventService;

	@Autowired
	UserService userService;
	
	/*
	 * 月別カレンダー画面表示
	 */
	@GetMapping()
	public String index(Model model) {
		// 曜日情報をセット
		String[] week = {"日", "月", "火", "水", "木", "金", "土"};
		
		Integer year = DateFormatUtil.getYear(); 
		Integer month = DateFormatUtil.getMonth();
		
		model.addAttribute("weeks", week);
		model.addAttribute("beforeMonth", DateFormatUtil.getYearMonth(year, month - 1));
		model.addAttribute("curenntMonth", DateFormatUtil.getYearMonth(year, month));
		model.addAttribute("afterMonth", DateFormatUtil.getYearMonth(year, month + 1));
		model.addAttribute("calendarBoxNum", eventService.createCalender(year, month));
		
		return "admin/events/monthly";
	}

	@GetMapping(value = { "/{yearMonth}" })
	public String yearMonth(@PathVariable String yearMonth, Model model) {
		// 曜日情報をセット
		String[] week = {"日", "月", "火", "水", "木", "金", "土"};
		
		Integer year = Integer.parseInt(yearMonth.substring(0, 4));
		Integer month = Integer.parseInt(yearMonth.substring(4, 6)) - 1;	
		
		model.addAttribute("weeks", week);
		model.addAttribute("beforeMonth", DateFormatUtil.getYearMonth(year, month - 1));
		model.addAttribute("curenntMonth", DateFormatUtil.getYearMonth(year, month));
		model.addAttribute("afterMonth", DateFormatUtil.getYearMonth(year, month + 1));
		model.addAttribute("calendarBoxNum", eventService.createCalender(year, month));
		
		return "admin/events/monthly";
	}

	/*
	 * 日別カレンダー画面表示
	 */
	@GetMapping(value = { "/daily/{date}" })
	public String dailyIndex(@PathVariable String date, Model model) {
		model.addAttribute("date", date);
		model.addAttribute("events", eventService.findByEventDateAndIsDeleteNotOrderByUserId(date));
		return "admin/events/daily";
	}

	@GetMapping(value = "/event/create")
	public String form(@ModelAttribute Event event, Model model) {
		model.addAttribute("loginUser", userService.getUserInfo());
		return "admin/events/create";
	}

	@GetMapping(value = "/event/create/{date}")
	public String form(@PathVariable String date, Model model) {
		Event event = new Event();
		event.setEventDate(DateFormatUtil.getYearMonthDayFormat(date));
		model.addAttribute("loginUser", userService.getUserInfo());
		model.addAttribute("event", event);
		return "admin/events/create";
	}

	@PostMapping(value = "/event/create")
	public String register(@Validated @ModelAttribute Event event, BindingResult result, Model model, RedirectAttributes ra) {
		FlashData flash;
		try {
			if (result.hasErrors()) {
				return "admin/events/create";
			}
			eventService.save(event);
			flash = new FlashData().success("イベントの登録が完了しました");
		} catch (Exception e) {
			System.out.print(e.getMessage());
			flash = new FlashData().danger("処理中にエラーが発生しました");
		}
		ra.addFlashAttribute("flash", flash);
		return "redirect:/admin";
	}

	@GetMapping(value = "/event/edit/{id}")
	public String edit(@PathVariable Integer id, Model model) {
		try {
			model.addAttribute("event", eventService.findById(id));
		} catch (DataNotFoundException e) {
			return "redirect:/admin";
		}
		return "admin/events/edit";
	}

	@PostMapping(value = "/event/edit/{id}")
	public String update(@PathVariable Integer id, @Validated Event event, BindingResult result, Model model, RedirectAttributes ra) {
		FlashData flash;
		try {
			if (result.hasErrors()) {
				return "admin/events/edit";
			}
			eventService.findById(id);
			// 更新
			eventService.save(event);
			flash = new FlashData().success("イベントの更新が完了しました");
		} catch (Exception e) {
			flash = new FlashData().danger("処理中にエラーが発生しました");
		}
		ra.addFlashAttribute("flash", flash);
		return "redirect:/admin";
	}

	@GetMapping(value = "/event/delete/{id}")
	public String delete(@PathVariable Integer id, RedirectAttributes ra) {
		FlashData flash;
		try {
			Event event = eventService.findById(id);
			event.setIsDelete(1);
			eventService.save(event);
			flash = new FlashData().success("イベントの削除が完了しました");
		} catch (DataNotFoundException e) {
			flash = new FlashData().danger("該当データがありません");
		} catch (Exception e) {
			flash = new FlashData().danger("処理中にエラーが発生しました");
		}
		ra.addFlashAttribute("flash", flash);
		return "redirect:/admin";
	}
}