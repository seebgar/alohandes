


(function (ng) {

    var mod = ng.module("clienteModule", ['ui.router']);
    
    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
        
        $urlRouterProvider.otherwise("/x");
        
        $stateProvider
                .state('clientes', {
							url: "/clientes",
							templateUrl: "clientes.html",
                            controller: 'clientesController' 
						}) ;
        
    
    
    
    }]);
    
})(window.angular);

(function (ng) {
   
    var mod = ng.module("clienteModule");
    mod.constant("clientesContext", "api/clientes");
    
    mod.controller('clientesController', ['$scope', '$http', 'clientesContext',
        
        function ($scope, $http, clientesContext) {
            $http.get('data/clientes.json').then(function (response) {
                $scope.clientes = response.data;
            });
        }
                                          
    ]);
}
)(window.angular);