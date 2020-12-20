package it.unibz.precise.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.unibz.precise.check.ModelToGraphTranslator;
import it.unibz.precise.graph.disj.AcyclicOrientationFinder;
import it.unibz.precise.graph.disj.DisjunctiveGraph;
import it.unibz.precise.model.Model;
import it.unibz.precise.rep.ModelRepository;

/**
 * REST controller for the {@link DisjunctiveGraph} representation of a model.
 * Exposes both the original disjunctive graph as well as an acyclic orientation, if one exists.
 * 
 * @author MatthiasP
 *
 */
@RestController
@RequestMapping(
	path=GraphController.CTRL_PATH,
	produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE}
)
public class GraphController {

	@Autowired
	private ModelRepository repository;
	
	@Autowired
	private ModelToGraphTranslator translator;
	
	@Autowired
	private AcyclicOrientationFinder orientationFinder;
	
	public static final String CTRL_PATH = FileControllers.ROOT_PATH + "/graph";
	public static final String FILE_PATH = FileControllers.NAME_PATTERN;
	public static final String FILE_EXT = ".json";				// Used for exporting only; imports work with any extension, only the syntax counts.
	
	public static final String ORIENTATION_SUB_PATH = "/orientation";	// Subpath for orientations
	
	/**
	 * Exposes the whole {@link DisjunctiveGraph} corresponding to the given model,
	 * containing all edges, even the simple ones.
	 */
	@RequestMapping(
		path=FILE_PATH + FILE_EXT,
		method=RequestMethod.GET,
		produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE}
	)
	public ResponseEntity<?> getGraph(@PathVariable("name") String name) {
		Model model = repository.findByName(name);
		if (model == null)
			return ResponseEntity.notFound().build();
		ModelToGraphTranslator.Input input = new ModelToGraphTranslator.Input(model);
		DisjunctiveGraph g = translator.translate(input);
		return ok(name, new DisjunctiveGraphAST(input, g));
	}
	
	/** Exposes an acyclic orient of the given model if one exists. */
	@RequestMapping(
		path=ORIENTATION_SUB_PATH + FILE_PATH + FILE_EXT,
		method=RequestMethod.GET,
		produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE}
	)
	public ResponseEntity<?> getOrientation(@PathVariable("name") String name) {
		Model model = repository.findByName(name);
		if (model == null)
			return ResponseEntity.notFound().build();
		ModelToGraphTranslator.Input input = new ModelToGraphTranslator.Input(model);
		DisjunctiveGraph orgGraph = translator.translate(input);
		DisjunctiveGraph orientation = orientationFinder.search(orgGraph).buildOrientation();
		return ok(name, new DisjunctiveGraphAST(input, orientation));
	}
	
	/**
	 * Transforms the given graph to a response entity.
	 * Returns {@link HttpStatus#NOT_FOUND} if {@code g} is null,
	 * a {@link DisjunctiveGraphAST} representation otherwise.
	 */
	private ResponseEntity<?> ok(String name, DisjunctiveGraphAST ast) {
		return ResponseEntity.ok()
			.header(HttpHeaders.CONTENT_DISPOSITION, FileControllers.getContentDisposition(name, FILE_EXT))
			.body(ast);
	}

}
