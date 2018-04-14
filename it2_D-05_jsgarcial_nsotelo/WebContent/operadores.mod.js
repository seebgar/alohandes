


(function (ng) {

    var mod = ng.module("operadorModule", ['ui.router']);
    
    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
        
        $urlRouterProvider.otherwise("/x");
        
        $stateProvider
                .state('operadores', {
							url: "/operadores",
							templateUrl: "operadores.html",
                            controller: 'operadoresController' 
						}) ;
        
    
    
    
    }]);
    
})(window.angular);

(function (ng) {
   
    var mod = ng.module("operadorModule");
    mod.constant("operadoresContext", "api/operadores");
    
    mod.controller('operadoresController', ['$scope', '$http', 'operadoresContext',
        
        function ($scope, $http, operadoresContext) {
            $http.get('data/operadores.json').then(function (response) {
                $scope.operadores = response.data;
            });
        }
                                          
    ]);
}
)(window.angular);