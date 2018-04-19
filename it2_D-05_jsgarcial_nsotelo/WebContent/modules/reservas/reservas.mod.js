(function (ng) {

    var mod = ng.module("reservaModule", ['ui.router']);

    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {

        $urlRouterProvider.otherwise("/x");

        $stateProvider
            .state('reservas', {
                url: "/reservas",
                templateUrl: "../../modules/reservas/panel_reservas.html",
                controller: 'reservasController'
            })

            .state('perfil_cliente', {
                url: "/perfil_cliente",
                templateUrl: "../../modules/reservas/perfil.html",
                controller: 'reservasController'
            })

        
            .state('reservasID', {
                    url: "/reservasID/:id",
                    param: {
                        id: null
                    },
                    templateUrl: "../../modules/reservas/panel_reservas_porID.html",
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
                



    }]);

})(window.angular);



(function (ng) {

    var mod = ng.module("reservaModule");
    mod.constant("reservasContext", "api/reservas");

    mod.controller('reservasController', ['$scope', '$http', 'reservasContext', '$state',

        function ($scope, $http, reservasContext, $state) {
            // http://localhost:8080/Alohandes_IT1/rest/personas/clientes/$scope.id_cliente/reservas
            // http://localhost:8080/Alohandes_IT1/rest/personas/clientes/20/reservas
            $http.get('data/reservas.json').then(function (response) {
                $scope.reservas = response.data;
            });

            

            if (($state.params.id !== undefined) && ($state.params.id !== null)) {
                // 'http://localhost:8080/Alohandes_IT1/rest/personas/operadores/'+ $state.params.id +'/propuestas'
                // 'data/p-' + $state.params.id + '.json'
                $http.get('http://localhost:8080/Alohandes_IT1/rest/personas/clientes/'+ $state.params.id +'/reservas').then(function (response) {
                    $scope.reservasID = response.data;
                });
                
                
                $http.get('http://localhost:8080/Alohandes_IT1/rest/reservasColectivas/clientes/' + $state.params.id ).then(function (response) {
                    $scope.reservasID_co = response.data;
                });
                
                // http://localhost:8080/Alohandes_IT1/rest/personas/20 
                $http.get('http://localhost:8080/Alohandes_IT1/rest/personas/' + $state.params.id).then(function (response) {
                    $scope.cliente = response.data;
                });
                
                
                
            } else {
                console.log($state.id + ' <<<<< Entra en el else' );
                // http://localhost:8080/Alohandes_IT1/rest/personas/20 
                $http.get('data/operador.json').then(function (response) {
                    $scope.cliente = response.data;
                });
            }

        }

    ]);
})(window.angular);
