package it.unibz.precise.rest.mdl.ast;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.IntSequenceGenerator;

import it.unibz.precise.model.Position;

@JsonIdentityInfo(generator=IntSequenceGenerator.class, property="id", scope=MDLTaskAST.class)
@JsonIdentityReference(alwaysAsId=false)
public class MDLTaskAST {
	
	private MDLTaskTypeAST definition;
	private int numberOfWorkersNeeded;
	private int durationDays;
	private MDLScopeAST exclusiveness;
	private List<MDLOrderSpecificationAST> order;
	private Position position;
	private List<Map<String, String>> locations;
	
	public MDLTaskTypeAST getDefinition() {
		return definition;
	}

	public void setDefinition(MDLTaskTypeAST definition) {
		this.definition = definition;
	}

	public int getNumberOfWorkersNeeded() {
		return numberOfWorkersNeeded;
	}

	public void setNumberOfWorkersNeeded(int numberOfWorkersNeeded) {
		this.numberOfWorkersNeeded = numberOfWorkersNeeded;
	}

	public int getDurationDays() {
		return durationDays;
	}

	public void setDurationDays(int durationDays) {
		this.durationDays = durationDays;
	}

	public MDLScopeAST getExclusiveness() {
		return exclusiveness;
	}

	public void setExclusiveness(MDLScopeAST exclusiveness) {
		this.exclusiveness = exclusiveness;
	}

	public List<MDLOrderSpecificationAST> getOrder() {
		return order;
	}

	public void setOrder(List<MDLOrderSpecificationAST> order) {
		this.order = order;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public List<Map<String, String>> getLocations() {
		return locations;
	}

	public void setLocations(List<Map<String, String>> locations) {
		this.locations = locations;
	}

}
