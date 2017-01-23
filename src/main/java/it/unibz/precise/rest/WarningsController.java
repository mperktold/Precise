package it.unibz.precise.rest;

import it.unibz.precise.check.ConsistencyChecker;
import it.unibz.precise.check.ConsistencyClassification;
import it.unibz.precise.check.ConsistencyWarning;
import it.unibz.precise.model.BaseEntity;
import it.unibz.precise.model.Model;
import it.unibz.precise.model.projection.EmptyProjection;
import it.unibz.precise.rep.ModelRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Exposes a list of {@link ConsistencyWarning}s by invoking all {@link ConsistencyChecker}s.
 * 
 * @author MatthiasP
 *
 */
@RepositoryRestController
public class WarningsController {
	
	private List<ConsistencyChecker> consistencyCheckers;		// All ConsistencyChecker-Beans in the ApplicationContext
	private ModelRepository modelRepository;
	private ProjectionFactory projectionFactory;
	
	@Autowired
	public WarningsController(List<ConsistencyChecker> consistencyCheckers, ModelRepository modelRepository, ProjectionFactory projectionFactory) {
		this.consistencyCheckers = consistencyCheckers;
		this.modelRepository = modelRepository;
		this.projectionFactory = projectionFactory;
		// Sort a static number of few checkers once, instead of sorting potentially many warnings on every request.
		consistencyCheckers.sort(ConsistencyClassification.BY_CATEGORY_AND_TYPE);
	}

	/** Invokes all checkers for the given model and returns the list of resulting warnings. */
	@RequestMapping(path="/models/{id}/warnings", method=RequestMethod.GET)
	public ResponseEntity<?> getWarnings(@PathVariable("id") long id) {
		Model model = modelRepository.findOne(id);
		
		if (model == null)
			return ResponseEntity.notFound().build();
		
		// Logic-wise, the following could be parallelized (using .parallelStream()),
		// but that results in random errors thrown from Hibernate.
		// Apparently, the way Hibernate retrieves entities is not thread-safe.
		List<WarningResourceContent> projected = consistencyCheckers.stream()
			.flatMap(c -> c.check(model))
			.filter(Objects::nonNull)		// In case some checker fails to ensure this
			.map(w -> new WarningResourceContent(w, this::mapEntity))
			.collect(Collectors.toList());
		
		return ResponseEntity.ok(new Resources<>(projected));
	}
	
	/**
	 * Maps entities to an {@link EmptyProjection}, which has no properties but only links.
	 * This is just enough information to identify the entity, which is the only purpose of
	 * including the entities in the result.
	 */
	private EmptyProjection mapEntity(BaseEntity e) {
		return projectionFactory.createProjection(EmptyProjection.class, e);
	}

}
