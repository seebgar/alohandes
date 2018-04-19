(function (ng) {

	var mod = ng.module("reservaModule", ['ui.router']);

	mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {

		$urlRouterProvider.otherwise("/x");

		$stateProvider
//		.state('reservas', {
//			url: "/reservas",
//			templateUrl: "../../modules/reservas/panel_reservas.html",
//			controller: 'reservasController'
//		})

		.state('perfil_c', {
			url: "/perfil_cliente/:id",
			param: {
				id: null
			},
			templateUrl: "../../modules/reservas/perfil_cliente.html",
			controller: 'reservasController'
		})

		.state('reservas_colectivas', {
			url: "/reservas_colectivas/:id",
			param: {
				id: null
			},
			templateUrl: "../../modules/reservas/panel_reservas_porID_co.html",
			controller: 'reservasController'
		})
		
		.state('r_ID', {
			url: "/clientes/:id/reservas",
			param: {
				id: null
			},
			templateUrl: "../../modules/reservas/panel_lista_reservas.html",
			controller: 'reservasController'
		})




	}]);

})(window.angular);



(function (ng) {

	var mod = ng.module("reservaModule");
	mod.constant("reservasContext", "api/reservas");

	mod.controller('reservasController', ['$scope', '$http', 'reservasContext', '$state',

		function ($scope, $http, reservasContext, $state) {
		
		
		if (($state.params.id !== undefined) && ($state.params.id !== null)) {
			// 'http://localhost:8080/Alohandes_IT1/rest/personas/operadores/'+ $state.params.id +'/propuestas'
			// 'data/p-' + $state.params.id + '.json'
			$http.get('http://localhost:8080/Alohandes_IT1/rest/personas/clientes/'+ $state.params.id +'/reservas').then(function (response) {
				$scope.res = response.data;
			});


			$http.get('http://localhost:8080/Alohandes_IT1/rest/reservasColectivas/clientes/' + $state.params.id ).then(function (response) {
				$scope.reservasID_co = response.data;
			});

			// http://localhost:8080/Alohandes_IT1/rest/personas/20  $state.params.id
			let perfil = 'http://localhost:8080/Alohandes_IT1/rest/personas/5';
			$http.get( perfil ).then(function (response) {
				$scope.c = response.data;
			}).then(function (response) {
				console.log("FAILED SACANDO EL PERFIL");
				console.log(response)
			});



		} else {
			
			console.log("USUARIO INVALIDO >> " + $state.params.id );
			
//			$http.get('../../modules/data/operador.json').then(function (response) {
//				$scope.cliente = response.data;
//			});
//			
//			// http://localhost:8080/Alohandes_IT1/rest/personas/clientes/$scope.id_cliente/reservas
//			// http://localhost:8080/Alohandes_IT1/rest/personas/clientes/20/reservas
//			$http.get('../../modules/data/reservas.json').then(function (response) {
//				$scope.reservas = response.data;
//			});
		};

		
		
		$scope.eliminar = function ( id_reserva, id_cliente ) {

			console.log(id_reserva + '<< Se va a eliminar del cliente >> ' + id_cliente )
			/*$http.delete('http://localhost:8080/Alohandes_IT1/rest/personas/clientes/' + id_cliente + '/reservas/' + id_reserva).then(function (response) {
				$scope.operador = response.data;
			});*/

		};
		
		$scope.eliminar_co = function ( id_reserva_colectiva ) {

			console.log(id_reserva_colectiva + '<< Se va a eliminar colectiva' )
			/*$http.delete('http://localhost:8080/Alohandes_IT1/rest/reservasColectivas/' + id_reserva_colectiva).then(function (response) {
				$scope.operador = response.data;
			});*/

		};

	}

	]);
})(window.angular);
