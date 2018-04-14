


(function (ng) {

    var mod = ng.module("reservaModule", ['ui.router']);
    
    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
        
        $urlRouterProvider.otherwise("/x");
        
        $stateProvider
                .state('reservas', {
							url: "/reservas",
							templateUrl: "panel_reservas.html",
                            controller: 'reservasController'
						}) ;
        
    
    
    
    }]);
    
})(window.angular);



(function (ng) {
   
    var mod = ng.module("reservaModule");
    mod.constant("reservasContext", "api/reservas");
    
    mod.controller('reservasController', ['$scope', '$http', 'reservasContext',
        
        function ($scope, $http, reservasContext) {
            $http.get('data/reservas.json').then(function (response) {
                $scope.reservas = response.data;
            });
        }
                                          
    ]);
}
)(window.angular);