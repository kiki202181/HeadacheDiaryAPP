package headache.app.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity(name = "Diary")
@Table(name = "diary_tb")
public class Diary {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long diary_id;
	
	// 日付
	@DateTimeFormat(pattern = "yyyy-MM-dd") // 入力時の期待フォーマット
	@JsonFormat(pattern = "yyyy/MM/dd") // 出力時の期待フォーマット
	private LocalDate date;

	private String menstruation;

	// 体調
	private String morning_condition;
	private String noon_condition;
	private String night_condition;

	// 薬
	private String morning_medicine;
	private String noon_medicine;
	private String night_medicine;

	// 日常生活への影響
	private String impact;

	// 症状
	private String throbbing_pain;// 脈打つ痛み
	private String nausea;// 吐き気
	private String vomiting;// 嘔吐

	@Size(max = 140, message = "メモは140文字以内で入力してください")
	private String memo;
	
	private long user_id;

}