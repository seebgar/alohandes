

(function (ng) {

    var mod = ng.module("propuestaModule", ['ui.router']);
    
    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
        
        $urlRouterProvider.otherwise("/x");
        
        $stateProvider
                .state('propuestas', {
                                url: "/propuestas",
                                templateUrl: "panel_propuestas.html",
                                controller: 'propuestasController'
                            }) ;
        
    
    
    
    }]);
    
})(window.angular);



(function (ng) {
   
    var mod = ng.module("propuestaModule");
    mod.constant("propuestaContext", "api/propuestas");
    
    mod.controller('propuestasController', ['$scope', '$http', 'propuestaContext',
        
        function ($scope, $http, propuestaContext) {
            $http.get('data/propuestas.json').then(function (response) {
                $scope.propuestas = response.data;
            });
        }
                                          
    ]);
}
)(window.angular);