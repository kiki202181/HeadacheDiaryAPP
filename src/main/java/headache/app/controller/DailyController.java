package headache.app.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import headache.app.entity.Day;
import headache.app.entity.Diary;
import headache.app.entity.LoginUser;
import headache.app.entity.MyCalendar;
import headache.app.repository.DiaryRepository;
import headache.app.repository.UserRepository;

@Controller
public class DailyController {

	@Autowired
	DiaryRepository diaryRepository;
	@Autowired
	UserRepository userRepository;

	List<Diary> diary_list;
	List<LoginUser> user_list;
	private static final TemporalField DAY_OF_WEEK = WeekFields.SUNDAY_START.dayOfWeek();

	@GetMapping("/") // ログインのページ
	public String firstPage(Model model) {
		LoginUser user = new LoginUser();
		model.addAttribute("User", user);
		return "login";
	}
	
	@GetMapping("/login/error")
   	public String loginError(Model model) {
   		model.addAttribute("loginError", true);
   		LoginUser user = new LoginUser();
   		model.addAttribute("User", user);
   		model.addAttribute("ErrorMessage", "ユーザー名かパスワードが間違っています。");
   		return "login";
   	}

	@GetMapping("/signIn") // ログイン後、viewに全部表示
	public String view(Authentication formUser, Model model) {

		List<LoginUser> users = userRepository.findByUserName(formUser.getName());

		if (users.size() > 0) {
			LoginUser user = new LoginUser();
			user = users.get(0);
			return view_page(model, user, null);

		} else {
			model.addAttribute("ErrorMessage", "ユーザー名が間違っています。");
			return firstPage(model);
		}

	}

	private String view_page(Model model, LoginUser user, String date) {

		LocalDate Specified_month = null;

		if (date == null) {
			Specified_month = LocalDate.now();
		} else {
			DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");// StringからLocalDateに変更
			Specified_month = LocalDate.parse(date, format);
		}

		set_one_month(Specified_month, user, model);
		calendar_set(model, Specified_month, diary_list);
		model.addAttribute("user", user);
		return "view";
	}

	private void set_one_month(LocalDate Specified_month, LoginUser user, Model model) {

		model.addAttribute("user_id", user.getUser_id());

		final LocalDate beginOfMonth = Specified_month.withDayOfMonth(1);// 月初日
		final LocalDate endOfMonth = Specified_month.withDayOfMonth(Specified_month.lengthOfMonth());// 月末日

		diary_list = diaryRepository.specify_the_period(beginOfMonth, endOfMonth, user.getUser_id());

		model.addAttribute("diary_list", diary_list);

	}

	// 日付を設定（カレンダー）
	private void calendar_set(Model model, final LocalDate specified_day, List<Diary> diary_list) {

		LocalDate now = LocalDate.now();
		Map<String, String> map = new HashMap<>();
		String string_day;

		for (Diary diary : diary_list) {
			string_day = String.valueOf(diary.getDate());

			if (diary.getMorning_condition().equals("bad") || diary.getNoon_condition().equals("bad")
					|| diary.getNight_condition().equals("bad")) {
				map.put(string_day, "bad");
			} else if (diary.getMorning_condition().equals("not_good") || diary.getNoon_condition().equals("not_good")
					|| diary.getNight_condition().equals("not_good")) {

				map.put(string_day, "not_good");
			} else {
				map.put(string_day, "fine");
			}
		}

		// 初日
		final LocalDate beginOfMonth = specified_day.withDayOfMonth(1);

		// 末日
		final LocalDate endOfMonth = specified_day.withDayOfMonth(specified_day.lengthOfMonth());

		// 月初日の週の1日目(=日曜日)
		final LocalDate beginOfCalendar = beginOfMonth.with(DAY_OF_WEEK, 1);

		// 月末日の週の7日目(=土曜)
		final LocalDate endOfCalendar = endOfMonth.with(DAY_OF_WEEK, 7);

		final List<List<Day>> weeks = new ArrayList<>();

		for (LocalDate day = beginOfCalendar; day.isBefore(endOfCalendar);) {
			final List<Day> week = new ArrayList<>();
			weeks.add(week);

			for (int i = 0; i < 7; i++) {

				final Day d = new Day();
				d.setDayOfMonth(day.getDayOfMonth());
				d.setLocalDate(day);

				if (specified_day.getMonthValue() == day.getMonthValue()) {// 今月と同値
					d.setThisMonth(true);

					if (day.isBefore(now)) {// 今日より前の日
						d.setBeforeToday(true);
					}

				} else {// 今月ではない
					d.setThisMonth(false);
				}

				if (map.containsKey(String.valueOf(day))) {
					d.setBeforeToday(true);
				}

				week.add(d);
				day = day.plusDays(1);
			}
		}

//--------------月初から月末まで----------------------------------------------
		LocalDate lastDay = endOfMonth;
		lastDay = lastDay.plusDays(1);

		for (LocalDate day = beginOfMonth; day.isBefore(lastDay);) {

			if (!map.containsKey(String.valueOf(day))) {
				map.put(String.valueOf(day), "fine");
			}
			day = day.plusDays(1);
		}

		final MyCalendar cal = new MyCalendar();
		cal.setWeeks(weeks);

		model.addAttribute("calendar", cal);
		model.addAttribute("map", map);
		model.addAttribute("specified_day", specified_day);
	}

	@PostMapping("/deleteDiary")
	public String deleteDiary(@RequestParam(value = "diary_id") long id,
			@RequestParam(value = "specified_day") String specified_day, Model model) {

		diary_list = diaryRepository.findByDiaryId(id);
		Diary diary = diary_list.get(0);
		diaryRepository.deleteDiary(id);// 削除

		List<LoginUser> users = userRepository.findByUserID(diary.getUser_id());
		LoginUser user = users.get(0);
		return view_page(model, user, specified_day);

	}

	@PostMapping("/return_page")
	public String return_page(@RequestParam(value = "user_id") long user_id,
			@RequestParam(value = "specified_day") String specified_day, Model model) {

		List<LoginUser> users = userRepository.findByUserID(user_id);
		LoginUser user = users.get(0);
		
		return view_page(model, user, specified_day);


	}

	
	@PostMapping("/editDiary")
	public String editDiary(@RequestParam(value = "diary_id") long id,
			@RequestParam(value = "specified_day") String specified_day, Model model) {

		diary_list = diaryRepository.findByDiaryId(id);
		Diary diary = diary_list.get(0);

		model.addAttribute("Diary", diary);
		model.addAttribute("specified_day", specified_day);

		return "form";

	}

	@PostMapping("/addDiary")
	public String addDiary(@RequestParam(value = "user_id") long user_id,
			@RequestParam(value = "specified_day") String specified_day, Model model) {

		model.addAttribute("specified_day", specified_day);
		set_date(user_id, model);
		return "form";

	}

	@PostMapping("/saveDiary")
	public String addDiary(@Validated @ModelAttribute Diary diary, BindingResult result,
			@RequestParam(value = "update") String update,
			@RequestParam(value = "specified_day") String specified_day,
			Model model, RedirectAttributes redirectAttribute) {


		Diary d = new Diary();
		d = diary;

		if (result.hasErrors()) {
			System.out.println("エラーが起こっています");
			String error_message = "";
			for (ObjectError error : result.getAllErrors()) {
				error_message += error.getDefaultMessage();
				model.addAttribute("error_message", error_message);
			}

			set_date(d.getUser_id(), model);
			return "form";
		}

		LocalDate date = d.getDate();
		String addDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

		// DATEがすでに存在していないか確認
		diary_list = diaryRepository.IsDate(diary.getDate(), diary.getUser_id());

		if (diary_list.size() == 0) {
			System.out.println("新規作成");
			diaryRepository.addDiary(addDate, d.getMenstruation(), d.getMorning_condition(), d.getNoon_condition(),
					d.getNight_condition(), d.getMorning_medicine(), d.getNoon_medicine(), d.getNight_medicine(),
					d.getImpact(), d.getThrobbing_pain(), d.getNausea(), d.getVomiting(), d.getMemo(), d.getUser_id());
		} else {

			if (update.equals("update")) {
				System.out.println("更新");

				diaryRepository.deleteDiary(d.getDiary_id());

				diaryRepository.addDiary(addDate, d.getMenstruation(), d.getMorning_condition(), d.getNoon_condition(),
						d.getNight_condition(), d.getMorning_medicine(), d.getNoon_medicine(), d.getNight_medicine(),
						d.getImpact(), d.getThrobbing_pain(), d.getNausea(), d.getVomiting(), d.getMemo(),
						d.getUser_id());

			} else {
				
				model.addAttribute("IsDate", true);// モーダルのためにクラスを追加する
				model.addAttribute("Diary", diary);
				model.addAttribute("specified_day", specified_day);
				return "form";
			}
		}
		
		model.addAttribute("specified_day", specified_day);
		
		List<LoginUser> users = userRepository.findByUserID(diary.getUser_id());
		LoginUser user = users.get(0);
		return view_page(model, user, specified_day);
		

	}

	@PostMapping("/moving_month") // 前月・次月ボタン
	public String test(@ModelAttribute @RequestParam(value = "month_button") String month_button,
			@RequestParam(value = "specified_day") String specified_day,
			@RequestParam(value = "username") String username, Model model) {

		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");// StringからLocalDateに変更
		LocalDate localDate = LocalDate.parse(specified_day, format);

		if (month_button.equals("next_month")) {// 前月・次月どちらを押されたか判断
			localDate = localDate.plusMonths(1);
			System.out.println(localDate.plusMonths(1));

		} else {
			localDate = localDate.minusMonths(1);
			System.out.println(localDate.minusMonths(1));
		}
		List<LoginUser> users = userRepository.findByUserName(username);
		LoginUser user = users.get(0);
		set_one_month(localDate, user, model);
		calendar_set(model, localDate, diary_list);
		model.addAttribute("user", user);

		return "view";
	}

	private void set_date(long user_id, Model model) {
		Diary diary = new Diary();
		diary.setUser_id(user_id);
		diary.setDate(LocalDate.now());
		model.addAttribute("Diary", diary);
	}

}
