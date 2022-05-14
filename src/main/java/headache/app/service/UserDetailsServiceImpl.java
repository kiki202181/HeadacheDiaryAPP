package headache.app.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import headache.app.entity.LoginUser;
import headache.app.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<LoginUser> UserList;
		UserList = userRepository.findByUserName(username);
		LoginUser loginUser = null;

		try {
			
			loginUser = UserList.get(0);
			
		} catch (Exception e) {
			System.out.println("入力されたユーザーがいません。");
		}

		Collection<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
		GrantedAuthority authority = new SimpleGrantedAuthority(loginUser.getAuthority());

		grantList.add(authority);

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String password = encoder.encode(loginUser.getPassword());

		UserDetails userDetails = (UserDetails) new User(username, password, grantList);

		return userDetails;

	}

}
