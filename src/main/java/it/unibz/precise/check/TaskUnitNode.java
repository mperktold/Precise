package it.unibz.precise.check;

import java.util.Set;

import it.unibz.precise.graph.disj.DisjunctiveGraph;
import it.unibz.precise.model.AttributeHierarchyNode;
import it.unibz.precise.model.Model;
import it.unibz.precise.model.PatternEntry;
import it.unibz.precise.model.Task;

/**
 * Represents an immutable node in a disjunctive graph that corresponds to a process model.
 * Each node has a task and a unit location.
 * Overrides {@link Object#equals(Object) equals} and {@link Object#hashCode() hashCode}
 * using the underlying task and unit such that any pair of {@link Task} and
 * {@link AttributeHierarchyNode}{@link Set} is contained at most once in a {@link Set},
 * which is important when translating a {@link Model} into a {@link DisjunctiveGraph}.
 * 
 * @see ModelToGraphTranslator
 * @see DisjunctiveGraph
 */
public class TaskUnitNode {
	// TODO: Should we change this to task type?
	private final Task task;
	private final AttributeHierarchyNode unit;
	
	public TaskUnitNode(Task task, AttributeHierarchyNode unit) {
		this.task = task;
		this.unit = unit;
	}
	
	public Task getTask() {
		return task;
	}

	public AttributeHierarchyNode getUnit() {
		return unit;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((task == null) ? 0 : task.hashCode());
		result = prime * result + ((unit == null) ? 0 : unit.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaskUnitNode other = (TaskUnitNode) obj;
		if (task == null) {
			if (other.task != null)
				return false;
		} else if (!task.equals(other.task))
			return false;
		if (unit == null) {
			if (other.unit != null)
				return false;
		} else if (!unit.equals(other.unit))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "<" + task.getShortIdentification() + "," + PatternEntry.toValueString(unit.getPattern()) + ">"; 
	}
	
}