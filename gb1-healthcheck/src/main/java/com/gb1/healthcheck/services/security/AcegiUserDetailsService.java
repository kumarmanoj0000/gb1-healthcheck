package com.gb1.healthcheck.services.security;

import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UserDetailsService;
import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.gb1.healthcheck.domain.users.AcegiUserDetailsAdapter;
import com.gb1.healthcheck.domain.users.User;
import com.gb1.healthcheck.domain.users.UserRepository;

/**
 * An implementation of the Acegi user details service that finds a specific user given its name
 * (login) and provides its details through the required interface.
 * 
 * @author Guillaume Bilodeau
 */
public class AcegiUserDetailsService implements UserDetailsService {
	private UserRepository userRepo;

	public AcegiUserDetailsService() {
	}

	/**
	 * Loads a user's details corresponding to a given user name.
	 * 
	 * @param username The user's name
	 * @return The user's details
	 * @throws UsernameNotFoundException When no user is found
	 */
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) {
		UserDetails userDetails = null;
		User user = userRepo.findUserByLogin(username);

		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		else {
			userDetails = new AcegiUserDetailsAdapter(user);

			// authorities should be eagerly fetched
			userDetails.getAuthorities();
		}

		return userDetails;
	}

	// external dependencies

	public void setUserRepository(UserRepository userRepo) {
		this.userRepo = userRepo;
	}
}
