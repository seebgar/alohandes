(function (ng) {

	var mod = ng.module("propuestaModule", ['ui.router']);

	mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {

		$urlRouterProvider.otherwise("/x");

		$stateProvider

		.state('mayores', {
			url: "/mayores",
			templateUrl: "../../modules/consultas/panel_mayores.html",
			controller: 'propuestasController'
		})

		.state('mayores.mes', {
			url: "/mayores",
			templateUrl: "../../modules/consultas/mayores_mes.html",
			controller: 'propuestasController'
		})

		.state('mayores.semana', {
			url: "/mayores",
			templateUrl: "../../modules/consultas/mayores_sem.html",
			controller: 'propuestasController'
		})

		.state('ingresos', {
			url: "/ingresos",
			templateUrl: "../../modules/consultas/panel_ingresos.html",
			controller: 'propuestasController'
		})

		.state('ingresos.mes', {
			url: "/ingresos/mes",
			templateUrl: "../../modules/consultas/ingresos_mes.html",
			controller: 'propuestasController'
		})

		.state('ingresos.semana', {
			url: "/ingresos/mes",
			templateUrl: "../../modules/consultas/ingresos_sem.html",
			controller: 'propuestasController'
		})

		.state('menores', {
			url: "/menores",
			templateUrl: "../../modules/consultas/panel_menores.html",
			controller: 'propuestasController'
		})

		.state('menores.mes', {
			url: "/menores",
			templateUrl: "../../modules/consultas/menores_mes.html",
			controller: 'propuestasController'
		})

		.state('menores.semana', {
			url: "/menores",
			templateUrl: "../../modules/consultas/menores_sem.html",
			controller: 'propuestasController'
		})

		.state('frecuentes', {
			url: "/frecuentes",
			templateUrl: "../../modules/consultas/panel_frecuentes.html",
			controller: 'propuestasController'
		})

		.state('frecuentes.param', {
			url: "/frecuentes/:f",
			param: {
				f: null
			},
			templateUrl: "../../modules/consultas/tabla_f.html",
			controller: 'propuestasController'
		})

		.state('pocos', {
			url: "/pocos",
			templateUrl: "../../modules/consultas/panel_pocos.html",
			controller: 'propuestasController'
		})


		.state('perfil_operador', {
			url: "/perfil",
			param: {
				id: null
			},
			templateUrl: "../../modules/propuestas/perfil_operador.html",
			controller: 'propuestasController'
		})

		.state('propuestas_id', {
			url: "/propuestasID/:id",
			param: {
				id: null
			},
			templateUrl: "../../modules/propuestas/panel_lista_propuestas.html",
			controller: 'propuestasController'
		})




	}]);

})(window.angular);



(function (ng) {

	var mod = ng.module("propuestaModule");
	mod.constant("propuestaContext", "api/propuestas");

	mod.controller('propuestasController', ['$scope', '$http', 'propuestaContext', '$state',

		function ($scope, $http, propuestaContext, $state) {

		// http://localhost:8080/Alohandes_IT1/rest/consultas/mayor/mes/apartamento
		// data/mayores.json
		$http.get('http://localhost:8080/Alohandes_IT1/rest/consultas/mayor/mes/apartamento').then(function (response) {
			$scope.lista_mayores = response.data;
		});

		$http.get('http://localhost:8080/Alohandes_IT1/rest/consultas/mayor/semana/apartamento').then(function (response) {
			$scope.lista_mayores_sem = response.data;
		});

		// http://localhost:8080/Alohandes_IT1/rest/consultas/ingresos/mes/apartamento
		// 'data/ingresos.json'
		$http.get('http://localhost:8080/Alohandes_IT1/rest/consultas/ingresos/mes/apartamento').then(function (response) {
			$scope.lista_ingresos = response.data;
		});

		// http://localhost:8080/Alohandes_IT1/rest/consultas/ingresos/semana/apartamento
		$http.get('http://localhost:8080/Alohandes_IT1/rest/consultas/ingresos/semana/apartamento').then(function (response) {
			$scope.lista_ingresos_sem = response.data;
		});


		// http://localhost:8080/Alohandes_IT1/rest/consultas/menor/mes/apartamento
		// 'data/menores.json'
		$http.get('http://localhost:8080/Alohandes_IT1/rest/consultas/menor/mes/apartamento').then(function (response) {
			$scope.lista_menores = response.data;
		});

		$http.get('http://localhost:8080/Alohandes_IT1/rest/consultas/menor/semana/apartamento').then(function (response) {
			$scope.lista_menores_sem = response.data;
		});



		//http://localhost:8080/Alohandes_IT1/rest/consultas/frecuentes/apartamento
		// 'data/frecuentes.json'
		$http.get('http://localhost:8080/Alohandes_IT1/rest/consultas/frecuentes/apartamento').then(function (response) {
			$scope.lista_frecuentes = response.data;
		});




		// http://localhost:8080/Alohandes_IT1/rest/consultas/poca_demanda
		// 'data/pocos.json'
		$http.get('http://localhost:8080/Alohandes_IT1/rest/consultas/poca_demanda').then(function (response) {
			$scope.lista_pocos = response.data;
		});


		if (($state.params.id !== undefined) && ($state.params.id !== null)) {
			// 'http://localhost:8080/Alohandes_IT1/rest/personas/operadores/'+ $state.params.id +'/propuestas'
			// 'data/p-' + $state.params.id + '.json'

			$http.get('http://localhost:8080/Alohandes_IT1/rest/personas/operadores/'+ $state.params.id +'/propuestas').then(function (response) {
				$scope.props = response.data;
			});

			$http.get('http://localhost:8080/Alohandes_IT1/rest/personas/' + $state.params.id).then(function (response) {
				$scope.op = response.data;
			});


		} else {
			console.log('USUARIO INVALIDO >> ' +  $state.params.id );
		};


		$scope.deshabilitar = function ( id ) {

			console.log(id + '<< Se va a deshabilitar')
			$http.put('http://localhost:8080/Alohandes_IT1/rest/propuestas/deshabilitar/' + id).then(function (response) {
				$scope.operador = response.data;
			});

		}


		if (($state.params.f !== undefined) && ($state.params.f !== null)) {

			console.log('http://localhost:8080/Alohandes_IT1/rest/consultas/frecuentes/' + $state.params.f)
			
			$http.get('http://localhost:8080/Alohandes_IT1/rest/consultas/frecuentes/' + $state.params.f).then(function (response) {
				$scope.lista_frecuentes_f = response.data;
			});

		}



	}

	]);
})(window.angular);
