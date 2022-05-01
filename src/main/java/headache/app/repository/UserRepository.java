package headache.app.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import headache.app.entity.LoginUser;

public interface UserRepository extends JpaRepository<LoginUser, Long> {

	@Transactional
	@Query(value = "SELECT * FROM user_tb", nativeQuery = true)
	List<LoginUser> findAllUser();

	@Transactional
	@Query(value = "SELECT * FROM user_tb WHERE username=?1", nativeQuery = true)
	List<LoginUser> findByUserName(String username);

	@Transactional
	@Query(value = "SELECT * FROM user_tb WHERE user_id=?1", nativeQuery = true)
	List<LoginUser> findByUserID(long userid);
	

	@Transactional
	@Modifying
	@Query(value = "INSERT INTO user_tb (username, password, authority) VALUES (?1,?2,?3)", nativeQuery = true)
	int addUser(String username, String password, String authority);

}
