(function (ng) {

    var mod = ng.module("reservaModule", ['ui.router']);

    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {

        $urlRouterProvider.otherwise("/x");

        $stateProvider
            .state('reservas', {
                url: "/reservas",
                templateUrl: "panel_reservas.html",
                controller: 'reservasController'
            })

            .state('perfil_cliente', {
                url: "/perfil_cliente",
                templateUrl: "data/perfil_cliente.html",
                controller: 'reservasController'
            })

        
            .state('reservasID', {
                    url: "/reservasID/:id",
                    param: {
                        id: null
                    },
                    templateUrl: "panel_consultas/panel_reservas_porID.html.html",
                    controller: 'reservasController'
                })



    }]);

})(window.angular);



(function (ng) {

    var mod = ng.module("reservaModule");
    mod.constant("reservasContext", "api/reservas");

    mod.controller('reservasController', ['$scope', '$http', 'reservasContext',

        function ($scope, $http, reservasContext) {
            // http://localhost:8080/Alohandes_IT1/rest/personas/clientes/$scope.id_cliente/reservas
            // http://localhost:8080/Alohandes_IT1/rest/personas/clientes/20/reservas
            $http.get('data/reservas.json').then(function (response) {
                $scope.reservas = response.data;
            });

            // http://localhost:8080/Alohandes_IT1/rest/personas/20 
            $http.get('data/operador.json').then(function (response) {
                $scope.cliente = response.data;
            });

            if (($state.params.id !== undefined) && ($state.params.id !== null)) {
                // 'http://localhost:8080/Alohandes_IT1/rest/personas/operadores/'+ $state.params.id +'/propuestas'
                console.log($state.id + ' <<<<< sin params' );
                console.log($state.params.id + ' <<<<<' );
                // 'data/p-' + $state.params.id + '.json'
                $http.get('http://localhost:8080/Alohandes_IT1/rest/personas/operadores/'+ $state.params.id +'/propuestas').then(function (response) {
                    $scope.reservasID = response.data;
                });
            } else {
                console.log($state.id + ' <<<<< sin params' );
                console.log($state.params.id + ' <<<<<' );
                
            }

        }

    ]);
})(window.angular);
