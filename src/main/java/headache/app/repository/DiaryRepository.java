package headache.app.repository;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import headache.app.entity.Diary;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
	@Transactional
	@Query(value = "SELECT * FROM diary_tb", nativeQuery = true)
	List<Diary> findAllDiary();

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM diary_tb WHERE diary_id=?1", nativeQuery = true)
	int deleteDiary(Long diary_id);

	@Transactional
	@Query(value = "SELECT * FROM diary_tb where diary_id=?1", nativeQuery = true)
	List<Diary> findByDiaryId(Long dairy_id);
	
	@Transactional
	@Modifying
	@Query(value = "SELECT * FROM diary_tb WHERE date=?1 and user_id=?2", nativeQuery = true)
	List<Diary> IsDate(LocalDate date,Long user_id);
	

	@Transactional
	@Query(value = "SELECT * FROM diary_tb WHERE date BETWEEN ?1 AND ?2 AND user_id=?3 ORDER BY date", nativeQuery = true)
	List<Diary> specify_the_period(LocalDate first_day,LocalDate last_day,Long user_id);
	
	@Transactional
	@Modifying
	@Query(value = "INSERT INTO diary_tb (date,menstruation,morning_condition,noon_condition,night_condition,morning_medicine,noon_medicine,night_medicine,impact,throbbing_pain,nausea,vomiting,memo,user_id) VALUES (?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,?11,?12,?13,?14)", nativeQuery = true)
	int addDiary(String date, String menstruation, String morning_condition, String noon_condition,
			String night_condition, String morning_medicine, String noon_medicine, String night_medicine, String impact,
			String throbbing_pain, String nausea, String vomiting, String memo, long user_id);

	@Transactional
	@Modifying
	@Query(value = "UPDATE diary_tb SET date=?1,menstruation=?2,morning_condition=?3,noon_condition=?4,night_condition=?5,morning_medicine=?6,noon_medicine=?7,night_medicine=?8,impact=?9,throbbing_pain=?10,nausea=?11,vomiting=?12,memo=?13 WHERE diary_id=?14", nativeQuery = true)
	int saveDiary(String date, String menstruation, String morning_condition, String noon_condition,
			String night_condition, String morning_medicine, String noon_medicine, String night_medicine, String impact,
			String throbbing_pain, String nausea, String vomiting, String memo, long diary_id);

	@Transactional
	@Query(value = "SELECT * FROM diary_tb WHERE user_id=?1", nativeQuery = true)
	List<Diary> findByUserId(Long user_id);

}
