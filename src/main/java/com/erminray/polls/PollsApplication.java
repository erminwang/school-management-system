package com.erminray.polls;

import com.erminray.polls.model.Role;
import com.erminray.polls.model.RoleName;
import com.erminray.polls.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@EntityScan(basePackageClasses = {
		PollsApplication.class,
		Jsr310JpaConverters.class
})
public class PollsApplication {

	@Autowired
	RoleRepository roleRepository;

	/**
	 * @PostConstruct is an annotation used on a method that needs to be executed after dependency injection is
	 * done to perform any initialization. The "init()" method will be called when PollsApplication is initialized
	 */
	@PostConstruct
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

		// store each role name in "roles" table if it's not already in there
		for(RoleName rn : RoleName.values()) {
			if(!roleRepository.findByName(rn).isPresent()) {
				Role role = new Role(rn);
				roleRepository.save(role);
			}
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(PollsApplication.class, args);
	}
}
