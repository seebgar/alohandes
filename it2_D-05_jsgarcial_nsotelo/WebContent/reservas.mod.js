


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
        
    
    
    
    }]);
    
})(window.angular);



(function (ng) {
   
    var mod = ng.module("reservaModule");
    mod.constant("reservasContext", "api/reservas");
    
    mod.controller('reservasController', ['$scope', '$http', 'reservasContext',
        
        function ($scope, $http, reservasContext) {
            // http://localhost:8080/Alohandes_IT1/rest/personas/clientes/$scope.id_cliente/reservas
            $scope.get = function() {
                console.log($scope.id_cliente);
                    $http.get('http://localhost:8080/Alohandes_IT1/rest/personas/clientes/' + $scope.id_cliente + '/reservas').then(function (response) {
                    $scope.reservas = response.data;
                });
            }
            
            // http://localhost:8080/Alohandes_IT1/rest/personas/ 
            $scope.get_2 = function() {
                $http.get('http://localhost:8080/Alohandes_IT1/rest/personas/' + $scope.id_cliente).then(function (response) {
                $scope.cliente = response.data;
            });
            }
            
            
        }
                                          
    ]);
}
)(window.angular);