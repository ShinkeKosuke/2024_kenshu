package com.example.demo.form;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Entity
public class UserForm {
	@Id
	private Integer id;

	@NotEmpty(message = "メールアドレスは必須項目です")
	@Email(message = "メールアドレスの形式が不正です")
	private String mail;

	@NotEmpty(message = "パスワードは必須項目です")
	private String password;
	
	@NotEmpty(message = "パスワード(再確認)は必須項目です")
	private String confrimPassword;
	
	@NotEmpty(message = "ニックネームは必須項目です")
	private String nickname;
	
	@AssertTrue(message = "パスワードとパスワード(再認証)は同一にしてください。")
    public boolean isPasswordValid() {
        return password.equals(confrimPassword);
    }
}

