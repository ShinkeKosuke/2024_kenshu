package com.example.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.common.DataNotFoundException;
import com.example.demo.common.FlashData;
import com.example.demo.common.ValidationGroups.Create;
import com.example.demo.entity.Tweet;
import com.example.demo.service.TweetService;
import com.example.demo.service.UserService;

@Controller
@RequestMapping(value = {"/admin"})
public class TweetController {
	@Autowired
	TweetService tweetService;
	
	@Autowired
	UserService userService;

	/*
	 * マイクロブログホーム画面
	 */
	@GetMapping(value = "/")
	public String index(Tweet tweet, Model model) {
		tweet.setUser(userService.getUserInfo());
		model.addAttribute("tweet", tweet);
		model.addAttribute("tweetList", tweetService.findAll());
		return "admin/tweet/index";
	}
	
	/*
	 * つぶやき登録
	 */
	@PostMapping(value = "/create")
	public String register(@Validated(Create.class) Tweet tweet, BindingResult result, Model model, RedirectAttributes ra) {
		FlashData flash;
		try {
			if (result.hasErrors()) {
				return "redirect:/admin/tweet/";
			}
			// 新規登録
			tweetService.save(tweet);
			flash = new FlashData().success("新規作成しました");
		} catch (Exception e) {
			flash = new FlashData().danger("処理中にエラーが発生しました");
		}
		ra.addFlashAttribute("flash", flash);
		return "redirect:/admin/";
	}

	/*
	 * 個別つぶやき画面
	 */
	@GetMapping(value = "/detail/{tweetId}")
	public String tweet(@PathVariable Integer tweetId, Model model) {
		FlashData flash;
		try {
			model.addAttribute("tweet", tweetService.findById(tweetId));
		} catch (DataNotFoundException e) {
			flash = new FlashData().danger("該当データがありません");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			flash = new FlashData().danger("エラーが発生しました");
		}
		return "admin/tweet/detail";
	}

	/*
	 * 個別ユーザつぶやき画面
	 */
	@GetMapping(value = "/userTweet/{userId}")
	public String userTweet(@PathVariable Integer userId, Model model) {
		model.addAttribute("userTweetList", tweetService.findByUserId(userId));
		return "admin/tweet/userTweet";
	}
}
