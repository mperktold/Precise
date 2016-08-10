package it.unibz.precise.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Model extends BaseEntity {
	
	@Column(unique=true, nullable=false)
	private String name;
	
	private String description;
	
	/**
	 * Indicates whether the model has a building configuration (i.e. Attributes,
	 * Phases, and relations among them), and that this configuration cannot be
	 * changed anymore unless it is erased.
	 */
	private boolean buildingConfigured;
	
	// Building configuration
	//---------------------------------------------------
	
	@OneToMany(mappedBy="model", cascade=CascadeType.ALL, orphanRemoval=true)
	private List<Attribute> attributes = new ArrayList<>();
	
	@OneToMany(mappedBy="model", cascade=CascadeType.ALL, orphanRemoval=true)
	private List<Phase> phases = new ArrayList<>();

	// Task configuration
	//---------------------------------------------------

	@OneToMany(mappedBy="model", cascade=CascadeType.ALL, orphanRemoval=true)
	private List<TaskType> taskTypes = new ArrayList<>();
	
	// Diagram
	//---------------------------------------------------

	@OneToMany(mappedBy="model", cascade=CascadeType.ALL, orphanRemoval=true)
	private List<Task> tasks = new ArrayList<>();
	
	@OneToMany(mappedBy="model", cascade=CascadeType.ALL, orphanRemoval=true)
	private List<Dependency> dependencies = new ArrayList<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isBuildingConfigured() {
		return buildingConfigured;
	}

	public void setBuildingConfigured(boolean buildingConfigured) {
		this.buildingConfigured = buildingConfigured;
	}

	public List<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<Attribute> attributes) {
		ModelToMany.ATTRIBUTES.setMany(this, attributes);
	}
	
	void internalSetAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}
	
	public void addAttribute(Attribute attribute) {
		ModelToMany.ATTRIBUTES.addOneOfMany(this, attribute);
	}

	public List<Phase> getPhases() {
		return phases;
	}

	public void setPhases(List<Phase> phases) {
		ModelToMany.PHASES.setMany(this, phases);
	}
	
	void internalSetPhases(List<Phase> phases) {
		this.phases = phases;
	}
	
	public void addPhase(Phase phase) {
		ModelToMany.PHASES.addOneOfMany(this, phase);
	}
	
	public List<Task> getTasks() {
		return tasks;
	}
	
	public void setTasks(List<Task> tasks) {
		ModelToMany.TASKS.setMany(this, tasks);
	}
	
	void internalSetTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
	
	public void addTask(Task task) {
		ModelToMany.TASKS.addOneOfMany(this, task);
	}

	public List<TaskType> getTaskTypes() {
		return taskTypes;
	}

	public void setTaskTypes(List<TaskType> taskTypes) {
		ModelToMany.TYPES.setMany(this, taskTypes);
	}
	
	void internalSetTaskTypes(List<TaskType> taskTypes) {
		this.taskTypes = taskTypes;
	}
	
	public void addTaskType(TaskType taskType) {
		ModelToMany.TYPES.addOneOfMany(this, taskType);
	}

	public List<Dependency> getDependencies() {
		return dependencies;
	}

	public void setDependencies(List<Dependency> dependencies) {
		ModelToMany.DEPENDENCIES.setMany(this, dependencies);
	}
	
	void internalSetDependencies(List<Dependency> dependencies) {
		this.dependencies = dependencies;
	}
	
	public void addDependency(Dependency dependency) {
		ModelToMany.DEPENDENCIES.addOneOfMany(this, dependency);
	}
	
}
