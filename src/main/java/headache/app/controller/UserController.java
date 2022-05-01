package headache.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import headache.app.entity.LoginUser;
import headache.app.repository.UserRepository;

@Controller
public class UserController {

	@Autowired
	UserRepository userRepository;
	List<LoginUser> user_list;

	@GetMapping("/NewUser")
	public String diaryPage(Model model) {

		LoginUser user = new LoginUser();
		model.addAttribute("User", user);
		return "NewUser";
	}

	@PostMapping("/NewUser")
	public String addDiary(@Validated @ModelAttribute LoginUser user, BindingResult result, Model model) {

		if (result.hasErrors()) {
			List<String> errorList = new ArrayList<String>();
			for (ObjectError error : result.getAllErrors()) {
				errorList.add(error.getDefaultMessage());
			}
			model.addAttribute("validationError", errorList);
			return diaryPage(model);
		}

		user_list = userRepository.findByUserName(user.getUsername());

		if (user_list.size() > 0) {
			model.addAttribute("ErrorMessage", "すでに使われているユーザー名です");
			return diaryPage(model);

		} else {
			userRepository.addUser(user.getUsername(), user.getPassword(), "USER");
			model.addAttribute("Message", "登録しました");
		}
		LoginUser formUser = new LoginUser();
		model.addAttribute("User", formUser);
		return "login";

	}
}
