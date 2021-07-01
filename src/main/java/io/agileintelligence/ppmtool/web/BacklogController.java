package io.agileintelligence.ppmtool.web;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.agileintelligence.ppmtool.domain.ProjectTask;
import io.agileintelligence.ppmtool.services.MapValidationErrorService;
import io.agileintelligence.ppmtool.services.ProjectTaskService;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {

	@Autowired
	private ProjectTaskService projectTaskService;
	
	@Autowired
	private MapValidationErrorService mapValidationErrorService;
	
	@PostMapping("/{projectIdentifier}")
	public ResponseEntity<?> addPTtoBacklog(@Valid @RequestBody ProjectTask projectTask, 
			BindingResult result, @PathVariable String projectIdentifier, Principal principal) {
		
		ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
		if (errorMap != null) return errorMap;
		
		ProjectTask projectTask1 = projectTaskService.addProjectTask(projectIdentifier, projectTask, principal.getName());
		
		
		return new ResponseEntity<ProjectTask>(projectTask1, HttpStatus.CREATED);
	}
	
	@GetMapping("/{projectIdentifier}")
	public ResponseEntity<List<ProjectTask>> getProjectBacklog(@PathVariable String projectIdentifier, Principal principal) {
		
		return new ResponseEntity<List<ProjectTask>>(projectTaskService.findBacklogByProjectIdentifier(projectIdentifier, principal.getName()), HttpStatus.OK);
	}

	@GetMapping("/{projectIdentifier}/{sequence}")
	public ResponseEntity<?> getProjectTask(@PathVariable String projectIdentifier, @PathVariable String sequence, Principal principal) {
		
		ProjectTask projectTask = projectTaskService.findProjectTaskBySequence(projectIdentifier, sequence, principal.getName());
		return new ResponseEntity<ProjectTask>(projectTask, HttpStatus.OK);
	}

	@PatchMapping("/{projectIdentifier}/{sequence}")
	public ResponseEntity<?> updateProjectTask(@Valid @RequestBody ProjectTask updateTask, 
			BindingResult result, @PathVariable String projectIdentifier, @PathVariable String sequence, Principal principal) {

		ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
		if (errorMap != null) return errorMap;
		
		ProjectTask task = projectTaskService.updateByProjectSequence(updateTask, projectIdentifier, sequence, principal.getName());
		
		return new ResponseEntity<ProjectTask>(task, HttpStatus.OK);
	}

	@DeleteMapping("/{projectIdentifier}/{sequence}")
	public ResponseEntity<?> deleteProjectTask(@PathVariable String projectIdentifier, @PathVariable String sequence, Principal principal) {
		projectTaskService.deleteByProjectSequence(projectIdentifier, sequence, principal.getName());
		return new ResponseEntity<String>("Project Task " + sequence + " was deleted successfully", HttpStatus.OK);
	}
}