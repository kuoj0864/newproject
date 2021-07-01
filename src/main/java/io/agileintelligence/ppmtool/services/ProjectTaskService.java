package io.agileintelligence.ppmtool.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.agileintelligence.ppmtool.domain.Backlog;
import io.agileintelligence.ppmtool.domain.Project;
import io.agileintelligence.ppmtool.domain.ProjectTask;
import io.agileintelligence.ppmtool.exceptions.ProjectNotFoundException;
import io.agileintelligence.ppmtool.repositories.ProjectTaskRepository;

@Service
public class ProjectTaskService {

	@Autowired
	private ProjectTaskRepository projectTaskRepo;
	
	@Autowired
	private ProjectService projectService;
	
	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username) {
		
			// PTs to be added to a specific project, project != null, BL exists
			Backlog backlog = projectService.findProjectByIdentifier(projectIdentifier, username).getBacklog();
			// set the backlog to projectTask
			projectTask.setBacklog(backlog);
			// we want our project sequence to be like this IDPRO-1 IDPRO-2, ..., 100, 101
			Integer ptSequence = backlog.getPTSequence();
			// Update the Backlog sequence
			ptSequence++;
			backlog.setPTSequence(ptSequence);

			// Add sequence to ProjectTask
			projectTask.setProjectSequence(projectIdentifier + "-" + ptSequence);
			projectTask.setProjectIdentifier(projectIdentifier);
			
			// Initial priority when priority is null
			if (projectTask.getPriority()==null || projectTask.getPriority()==0) {
				projectTask.setPriority(3);
			}
			// Initial status when status is null
			if (projectTask.getStatus()==null || projectTask.getStatus()=="") {
				projectTask.setStatus("TO_DO");
			}
			return projectTaskRepo.save(projectTask);
			
	}

	public List<ProjectTask> findBacklogByProjectIdentifier(String projectIdentifier, String username) {
		Project project = projectService.findProjectByIdentifier(projectIdentifier, username);
		if (project == null) {
			throw new ProjectNotFoundException("Project with ID: '" + projectIdentifier + "' does not exist");
		}
		return projectTaskRepo.findByProjectIdentifierOrderByPriorityAscProjectSequenceAsc(projectIdentifier);
	}

	public ProjectTask findProjectTaskBySequence(String projectIdentifier, String sequence, String username) {
		// make sure we are searching in an existing backlog
		projectService.findProjectByIdentifier(projectIdentifier, username);
		
		// make sure that our task exists
		ProjectTask task = projectTaskRepo.findByProjectSequence(sequence);
		if (task == null) {
			throw new ProjectNotFoundException("Porject task: '" + sequence + "' not found");
		}
		
		// make sure that the projectIdentifier in the path corresponds to the right project
		if (!task.getProjectIdentifier().equalsIgnoreCase(projectIdentifier)) {
			throw new ProjectNotFoundException("Porject task: '" + sequence + "' doesn't exist in Project: '" + projectIdentifier + "'");
		}
		
		return task;
	}
	
	public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String projectId, String sequence, String username) {
		ProjectTask task = this.findProjectTaskBySequence(projectId, sequence, username);
		
		if (!updatedTask.getProjectIdentifier().equalsIgnoreCase(task.getProjectIdentifier())) {
			throw new ProjectNotFoundException("Project with ID: '" + projectId + "' doesn't match the updatedTask: '" + updatedTask.getProjectIdentifier() + "'");
		}

		if (!updatedTask.getProjectSequence().equalsIgnoreCase(task.getProjectSequence())) {
			throw new ProjectNotFoundException("Project Task: '" + sequence + "' doesn't match the updatedTask: '" + updatedTask.getProjectSequence() + "'");
		}

		task = updatedTask;
		
		return projectTaskRepo.save(task);
	}
	
	public void deleteByProjectSequence(String projectId, String sequence, String username ) {
        ProjectTask projectTask = findProjectTaskBySequence(projectId, sequence, username);
        projectTaskRepo.delete(projectTask);

	}
}
