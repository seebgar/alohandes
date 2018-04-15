

(function (ng) {

    var mod = ng.module("propuestaModule", ['ui.router']);
    
    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
        
        $urlRouterProvider.otherwise("/x");
        
        $stateProvider
                .state('propuestas', {
                                url: "/propuestas",
                                templateUrl: "panel_propuestas.html",
                                controller: 'propuestasController'
                            }) 
		
				.state('mayores', {
                                url: "/mayores",
                                templateUrl: "panel_consulta/panel_mayores.html",
                                controller: 'propuestasController'
                            }) 
		
				.state('ingresos', {
                                url: "/ingresos",
                                templateUrl: "panel_consulta/panel_ingresos.html",
                                controller: 'propuestasController'
                            }) 
		
				.state('menores', {
                                url: "/menores",
                                templateUrl: "panel_consulta/panel_menores.html",
                                controller: 'propuestasController'
                            }) 
		
				.state('frecuentes', {
                                url: "/frecuentes",
                                templateUrl: "panel_consulta/panel_frecuentes.html",
                                controller: 'propuestasController'
                            }) 
		
				.state('pocos', {
                                url: "/pocos",
                                templateUrl: "panel_consulta/panel_pocos.html",
                                controller: 'propuestasController'
                            })
        
    
    
    
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
			
			$http.get('data/mayores.json').then(function (response) {
                $scope.lista_mayores = response.data;
            });
			
			$http.get('data/ingresos.json').then(function (response) {
                $scope.lista_ingresos = response.data;
            });
			
			$http.get('data/menores.json').then(function (response) {
                $scope.lista_menores = response.data;
            });
			
			$http.get('data/frecuentes.json').then(function (response) {
                $scope.lista_frecuentes = response.data;
            });
			
			$http.get('data/pocos.json').then(function (response) {
                $scope.lista_pocos = response.data;
            });
        }
                                          
    ]);
}
)(window.angular);