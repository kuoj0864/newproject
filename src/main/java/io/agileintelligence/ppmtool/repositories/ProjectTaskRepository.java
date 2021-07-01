package io.agileintelligence.ppmtool.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.agileintelligence.ppmtool.domain.ProjectTask;

@Repository
public interface ProjectTaskRepository extends JpaRepository<ProjectTask, Long> {
	List<ProjectTask> findByProjectIdentifierOrderByPriority(String projectIdentifier);
	List<ProjectTask> findByProjectIdentifierOrderByPriorityAscProjectSequenceAsc(String projectIdentifier);
	
	ProjectTask findByProjectSequence(String sequence);
}
