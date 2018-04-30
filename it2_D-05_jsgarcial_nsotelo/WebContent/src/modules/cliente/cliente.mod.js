(function (ng) {

    var mod = ng.module("clienteModule", ['ui.router']);

    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {

        $urlRouterProvider.otherwise("/x");

        $stateProvider

            .state('reservas', {
                url: "/clientes/:id/reservas",
                param: {
                    id: null
                },
                views: {
                    'content': {
                        templateUrl: 'cliente/reservas.html',
                        controller: 'clienteController'
                    }
                }
            })


            .state('reservas_colectivas', {
                url: "/clientes/:id/reservas_colectivas",
                param: {
                    id: null
                },
                views: {
                    'content': {
                        templateUrl: 'cliente/reservas_colectivas.html',
                        controller: 'clienteController'
                    }
                }
            });


	}]);

})(window.angular);



(function (ng) {

    var mod = ng.module("clienteModule");
    mod.constant("clienteContext", "api/cliente");

    mod.controller('clienteController', ['$scope', '$http', 'clienteContext', '$state',

		function ($scope, $http, reservasContext, $state) {


            if (($state.params.id !== undefined) && ($state.params.id !== null)) {

                // 'http://localhost:8080/Alohandes_IT1/rest/personas/operadores/'+ $state.params.id +'/propuestas'
                $http.get('http://localhost:8080/Alohandes_IT1/rest/personas/clientes/' + $state.params.id + '/reservas').then(function (response) {
                    $scope.reservas = response.data;
                });

                $http.get('http://localhost:8080/Alohandes_IT1/rest/reservasColectivas/clientes/' + $state.params.id).then(function (response) {
                    $scope.reservas_colectivas = response.data;
                });


            };


	}

	]);
})(window.angular);
