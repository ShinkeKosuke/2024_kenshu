package com.example.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.common.FlashData;
import com.example.demo.common.ValidationGroups.Create;
import com.example.demo.entity.User;
import com.example.demo.form.UserForm;
import com.example.demo.service.DepartmentService;
import com.example.demo.service.MailService;
import com.example.demo.service.UserService;

@Controller
@RequestMapping(value = { "/users" })
public class UsersController {
	@Autowired
	UserService userService;

	@Autowired
	MailService mailService;

	@Autowired
	DepartmentService departmentService;

	/*
	 * 新規作成画面表示
	 */
	@GetMapping(value = "/create")
	public String form(UserForm userForm, Model model) {
		model.addAttribute("userForm", userForm);
		return "users/create";
	}

	/*
	 * 新規登録
	 */
	@PostMapping(value = "/create")
	public String register(@Validated(Create.class) UserForm userForm, BindingResult result, Model model,
			RedirectAttributes ra) {
		FlashData flash;
		try {
			User user = new User();
			if (result.hasErrors()) {
				return "users/create";
			}
			if (!userService.isUnique(userForm.getMail())) {
				// emailが重複している
				flash = new FlashData().danger("メールアドレスが重複しています");
				model.addAttribute("flash", flash);
				return "users/create";
			}
			// 平文のパスワードを暗号文にする
			user.encodePassword(userForm.getPassword());
			user.setMail(userForm.getMail());
			user.setName(userForm.getName());
			user.setDepartment(departmentService.findById(userForm.getDepartmentId()));

			// 新規登録
			userService.save(user);
			flash = new FlashData().success("新規作成しました");

			// メール送信
			mailService.sendMail(user.getMail());

		} catch (Exception e) {
			flash = new FlashData().danger("処理中にエラーが発生しました");
		}
		ra.addFlashAttribute("flash", flash);
		return "redirect:/users/login";
	}
}