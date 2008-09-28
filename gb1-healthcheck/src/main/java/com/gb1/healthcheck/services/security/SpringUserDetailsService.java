package com.gb1.healthcheck.services.security;

import javax.annotation.Resource;

import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gb1.healthcheck.domain.users.SpringUserDetailsAdapter;
import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.domain.users.UserRepository;

/**
 * An implementation of the Spring Security user details service that finds a specific user given
 * its name (login) and provides its details through the required interface.
 * 
 * @author Guillaume Bilodeau
 */
@Service("userDetailsService")
@Transactional(readOnly = true)
public class SpringUserDetailsService implements UserDetailsService {
	@Resource
	protected UserRepository userRepo;

	public SpringUserDetailsService() {
	}

	/**
	 * Loads a user's details corresponding to a given user name.
	 * 
	 * @param username The user's name
	 * @return The user's details
	 * @throws UsernameNotFoundException When no user is found
	 */
	public UserDetails loadUserByUsername(String username) {
		UserDetails userDetails = null;
		User user = userRepo.findUserByLogin(username);

		if (user == null) {
			throw new UsernameNotFoundException(username);
		}

		// authorities should be eagerly fetched
		userDetails = new SpringUserDetailsAdapter(user);
		userDetails.getAuthorities();

		return userDetails;
	}
}
