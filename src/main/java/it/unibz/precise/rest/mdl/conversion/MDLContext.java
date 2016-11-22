package it.unibz.precise.rest.mdl.conversion;

import static it.unibz.precise.rest.mdl.conversion.CachingTranslator.cache;

import it.unibz.precise.model.Attribute;
import it.unibz.precise.model.Craft;
import it.unibz.precise.model.Dependency;
import it.unibz.precise.model.Model;
import it.unibz.precise.model.OrderSpecification;
import it.unibz.precise.model.Phase;
import it.unibz.precise.model.Scope;
import it.unibz.precise.model.Task;
import it.unibz.precise.model.TaskType;
import it.unibz.precise.rest.mdl.ast.MDLAttributeAST;
import it.unibz.precise.rest.mdl.ast.MDLConfigAST;
import it.unibz.precise.rest.mdl.ast.MDLCraftAST;
import it.unibz.precise.rest.mdl.ast.MDLDependencyAST;
import it.unibz.precise.rest.mdl.ast.MDLDiagramAST;
import it.unibz.precise.rest.mdl.ast.MDLFileAST;
import it.unibz.precise.rest.mdl.ast.MDLModelAST;
import it.unibz.precise.rest.mdl.ast.MDLOrderSpecificationAST;
import it.unibz.precise.rest.mdl.ast.MDLPhaseAST;
import it.unibz.precise.rest.mdl.ast.MDLScopeAST;
import it.unibz.precise.rest.mdl.ast.MDLTaskAST;
import it.unibz.precise.rest.mdl.ast.MDLTaskTypeAST;

public class MDLContext {
	
	private final CachingTranslator<Model, MDLFileAST> fileTranslator;
	private final CachingTranslator<Model, MDLModelAST> modelTranslator;
	private final CachingTranslator<Model, MDLConfigAST> configTranslator;
	private final CachingTranslator<Model, MDLDiagramAST> diagramTranslator;
	private final CachingTranslator<Craft, MDLCraftAST> craftTranslator;
	private final CachingTranslator<Attribute, MDLAttributeAST> attributeTranslator;
	private final CachingTranslator<Phase, MDLPhaseAST> phaseTranslator;
	private final CachingTranslator<TaskType, MDLTaskTypeAST> taskTypeTranslator;
	private final CachingTranslator<Task, MDLTaskAST> taskTranslator;
	private final CachingTranslator<Dependency, MDLDependencyAST> dependencyTranslator;
	private final CachingTranslator<Scope, MDLScopeAST> scopeTranslator;
	private final CachingTranslator<OrderSpecification, MDLOrderSpecificationAST> orderSpecTranslator;
	
	private boolean strictMode = true;
	
	private MDLContext() {
		fileTranslator       = cache(new FileTranslator(this));
		modelTranslator      = cache(new ModelTranslator(this));
		configTranslator     = cache(new ConfigTranslator(this));
		diagramTranslator    = cache(new DiagramTranslator(this));
		craftTranslator      = cache(new CraftTranslator(this));
		attributeTranslator  = cache(new AttributeTranslator(this));
		phaseTranslator      = cache(new PhaseTranslator(this));
		taskTypeTranslator   = cache(new TaskTypeTranslator(this));
		taskTranslator       = cache(new TaskTranslator(this));
		dependencyTranslator = cache(new DependencyTranslator(this));
		scopeTranslator      = cache(new ScopeTranslator(this));
		orderSpecTranslator  = cache(new OrderSpecificationTranslator(this));
	}
	
	public static MDLContext create() {
		return new MDLContext();
	}
	
	public boolean isStrictMode() {
		return strictMode;
	}
	
	public MDLContext switchStrictMode(boolean strictMode) {
		this.strictMode = strictMode;
		return this;
	}
		
	public CachingTranslator<Model, MDLFileAST> files() {
		return fileTranslator;
	}
	
	public CachingTranslator<Model, MDLModelAST> models() {
		return modelTranslator;
	}

	public CachingTranslator<Model, MDLConfigAST> configs() {
		return configTranslator;
	}
	
	public CachingTranslator<Model, MDLDiagramAST> diagrams() {
		return diagramTranslator;
	}
	
	public CachingTranslator<Craft, MDLCraftAST> crafts() {
		return craftTranslator;
	}

	public CachingTranslator<Attribute, MDLAttributeAST> attributes() {
		return attributeTranslator;
	}
	
	public CachingTranslator<Phase, MDLPhaseAST> phases() {
		return phaseTranslator;
	}
	
	public CachingTranslator<TaskType, MDLTaskTypeAST> taskTypes() {
		return taskTypeTranslator;
	}
	
	public CachingTranslator<Task, MDLTaskAST> tasks() {
		return taskTranslator;
	}
	
	public CachingTranslator<Dependency, MDLDependencyAST> dependencies() {
		return dependencyTranslator;
	}
	
	public CachingTranslator<Scope, MDLScopeAST> scopes() {
		return scopeTranslator;
	}
	
	public CachingTranslator<OrderSpecification, MDLOrderSpecificationAST> orderSpecs() {
		return orderSpecTranslator;
	}
	
}
