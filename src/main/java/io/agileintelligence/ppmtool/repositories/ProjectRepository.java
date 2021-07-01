package io.agileintelligence.ppmtool.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.agileintelligence.ppmtool.domain.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

	Project findByProjectIdentifier(String projectIdentifier);
	List<Project> findAllByProjectLeader(String username);
}
