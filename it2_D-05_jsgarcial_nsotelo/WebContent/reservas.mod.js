


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
            // http://localhost:8080/Alohandes_IT1/rest/personas/clientes/40/reservas
            $http.get('data/reservas.json').then(function (response) {
                $scope.reservas = response.data;
            });
            
            // http://localhost:8080/Alohandes_IT1/rest/personas/400
            $http.get('data/operador.json').then(function (response) {
                $scope.cliente = response.data;
            });
            
        }
                                          
    ]);
}
)(window.angular);