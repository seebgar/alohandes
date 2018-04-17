


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
            //http://localhost:8080/Alohandes_IT1/rest/personas/operadores
            $http.get('http://localhost:8080/Alohandes_IT1/rest/personas/operadores').then(function (response) {
                $scope.operadores = response.data;
            });
        }
                                          
    ]);
}
)(window.angular);