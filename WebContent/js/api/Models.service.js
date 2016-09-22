define([
	'lib/lodash',
	'util/util'
], function (
	_,
	util
) {
	'use strict';
	
	ModelsService.$inject = ['PreciseApi', 'Resources', 'Pages'];
	
	function ModelsService(PreciseApi, Resources, Pages) {
		
		var Models = this;
		
		Models.findAll = findAll;
		Models.firstPage = firstPage;
		Models.findByName = findByName;
		Models.newResource = newResource;
		Models.existingResource = existingResource;
		Models.resource = resource;
		Models.Resource = ModelResource;
		
		var REL_SINGULAR = 'model',
			REL_PLURAL = 'models',
			DEFAULT_PROJECTION = 'modelSummary';
		
		var projectionTemplateParams = {
			projection: DEFAULT_PROJECTION
		}
		
		function firstPage() {
			return PreciseApi.fromBase().traverse(function (builder) {
				return builder
					.follow(REL_PLURAL)
					.withTemplateParameters(projectionTemplateParams)
					.get();
			}).then(Pages.wrapper(REL_PLURAL));
		}
		
		function findAll() {
			return firstPage()
				.then(function (page) {
					return page.collectRemaining(projectionTemplateParams);
				});
		}
		
		function findByName(name) {
			return PreciseApi.fromBase()
				.traverse(function (builder) {
					return builder
						.follow(REL_PLURAL, 'search', 'findByName')
						.withTemplateParameters({
							name: name,
							projection: DEFAULT_PROJECTION
						})
						.get()
				}).then(Models.existingResource);
		}
		
		function newResource(model) {
			return resource(model, false);
		}
		
		function existingResource(model) {
			return resource(model, true);
		}
		
		function resource(model, existing) {
			return new ModelResource(model, existing);
		}
		
		function ModelResource(data, existing) {
			Resources.Base.call(this, data, existing);
		}
		
		util.defineClass(Resources.Base, {
			
			constructor: ModelResource,

			rels: {
				singular: REL_SINGULAR,
				plural: REL_PLURAL
			},
			
			defaultProjection: DEFAULT_PROJECTION,
		
			searchByModel: function (rel, params) {
				var templateParams = _.assign({
					model: PreciseApi.hrefTo(this.data),
				}, params);
				return PreciseApi.fromBase()
					.traverse(function (builder) {
						return builder
							.follow(rel, 'search', 'findByModel')
							.withTemplateParameters(templateParams)
							.get();
					}).then(Pages.wrapper(rel));
			},
			
			followToList: function (rel, params) {
				return PreciseApi.from(PreciseApi.hrefTo(this.data, rel))
					.traverse(function (builder) {
						return builder
							.withTemplateParameters(params)
							.get();				
					}).then(Pages.wrapper(rel));
			},
			
			getList: function (rel, params) {
				return params && params.projection
					? this.searchByModel(rel, params)
					: this.followToList(rel, params);
			},
			
			getTasks: function (params) {
				return this.getList('tasks', params);
			},
			
			getDependencies: function (params) {
				return this.getList('dependencies', params);
			},
			
			getPhases: function (params) {
				return this.getList('phases', params);
			},
			
			getTaskTypes: function (params) {
				return this.getList('taskTypes', params);
			}
		
		});

	}
	
	return ModelsService;
	
});