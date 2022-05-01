package headache.app.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity(name = "User")
@Table(name = "user_tb")
public class LoginUser {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long user_id;
	@Size(max=20 ,message = "ユーザー名は20文字以内で入力してください")
	private String username;
	@Size(max=20 ,message = "パスワードは20文字以内で入力してください")
	private String password;
	private String authority;

}
