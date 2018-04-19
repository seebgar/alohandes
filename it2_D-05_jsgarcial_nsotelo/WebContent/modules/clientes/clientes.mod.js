


(function (ng) {

    var mod = ng.module("clienteModule", ['ui.router']);
    
    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
        
        $urlRouterProvider.otherwise("/x");
        
        $stateProvider
                .state('clientes', {
							url: "/clientes",
							templateUrl: "modules/clientes/clientes.html",
                            controller: 'clientesController' 
						}) ;
        
    
    
    
    }]);
    
})(window.angular);

(function (ng) {
   
    var mod = ng.module("clienteModule");
    mod.constant("clientesContext", "api/clientes");
    
    mod.controller('clientesController', ['$scope', '$http', 'clientesContext',
        
        function ($scope, $http, clientesContext) {
			//http://localhost:8080/Alohandes_IT1/rest/personas/clientes
            //data/clientes.json
            $http.get('http://localhost:8080/Alohandes_IT1/rest/personas/clientes').then(function (response) {
                $scope.clientes = response.data;
            });
        }
                                          
    ]);
}
)(window.angular);