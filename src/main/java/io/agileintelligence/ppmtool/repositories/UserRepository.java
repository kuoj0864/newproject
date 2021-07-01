package io.agileintelligence.ppmtool.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import io.agileintelligence.ppmtool.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByUsername(String username);
	User getById(Long id);

}
