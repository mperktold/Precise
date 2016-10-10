package it.unibz.precise.rest.mdl.conversion;

import it.unibz.precise.model.Model;
import it.unibz.precise.rest.mdl.ast.MDLConfigAST;
import it.unibz.util.Util;

class ConfigTranslator extends AbstractMDLTranslator<Model, MDLConfigAST> {
	
	ConfigTranslator(MDLContext context) {
		super(context);
	}

	@Override
	public void updateMDL(Model model, MDLConfigAST mdlConfig) {
		mdlConfig.setHoursPerDay(model.getHoursPerDay());
		mdlConfig.setAttributes(Util.mapToList(model.getAttributes(), context().attributes()::toMDL));
		mdlConfig.setPhases(Util.mapToList(model.getPhases(), context().phases()::toMDL));
		mdlConfig.setTaskDefinitions(Util.mapToList(model.getTaskTypes(), context().taskTypes()::toMDL));
	}
	
	@Override
	public void updateEntity(MDLConfigAST mdlConfig, Model model) {
		model.setHoursPerDay(mdlConfig.getHoursPerDay());
		model.setAttributes(Util.mapToList(mdlConfig.getAttributes(), context().attributes()::toEntity));
		model.setPhases(Util.mapToList(mdlConfig.getPhases(), context().phases()::toEntity));
		model.setTaskTypes(Util.mapToList(mdlConfig.getTaskDefinitions(), context().taskTypes()::toEntity));
	}

	@Override
	public Model createEntity(MDLConfigAST mdl) {
		return new Model();
	}

	@Override
	public MDLConfigAST createMDL(Model entity) {
		return new MDLConfigAST();
	}

}
