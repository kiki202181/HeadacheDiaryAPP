package headache.app.controller;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Component
public class GlobalControllAdvice {

	@ExceptionHandler(DataAccessException.class)
	public String dataAccessExceptionHandler(DataAccessException e) {
		System.out.println("内部サーバーエラー（DB）");
		System.out.println(HttpStatus.INTERNAL_SERVER_ERROR);
	
		return "error";
	}

	@ExceptionHandler(Exception.class)
	public String exceptionHandler(Exception e) {
		
		System.out.println("内部サーバーエラー");
		System.out.println(HttpStatus.INTERNAL_SERVER_ERROR);

		return "error";
	}
}
