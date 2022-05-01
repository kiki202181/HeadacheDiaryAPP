package headache.app.entity;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Day {
	public LocalDate localDate;
	public int dayOfMonth;
	private boolean beforeToday;
	private boolean thisMonth;

	// 今日より前かどうか
	public void beforeToday(final boolean day) {
		this.beforeToday = day;
	}

	// 当月かどうか
	public void thisMonth(final boolean day) {
		this.thisMonth = day;
	}
}