(function (ng) {

    var mod = ng.module("appModule", ['ui.router']);

    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {

        $urlRouterProvider.otherwise("/home");
        
    }
               ]);

})(window.angular);


(function (ng) {

    var mod = ng.module("appModule");

    mod.controller('appController', ['$scope', '$http', '$state',

        function ($scope, $http, $state) {
            
            // TODO 
            
            /*
            $http.get('').then(function (response) {
                $scope.total_reservas = response.data;
            });
            
            $http.get('').then(function (response) {
                $scope.total_ofertas = response.data;
            });
            
            $http.get('').then(function (response) {
                $scope.total_clientes = response.data;
            });
            
            $http.get('').then(function (response) {
                $scope.total_operadores = response.data;
            });

*/
        } /*END FUNCTION CONTROLLER*/
    ]); /*END CONTROLLER*/
})(window.angular);
