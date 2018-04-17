

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
        
                .state('perfil', {
                                url: "/perfil",
                                templateUrl: "panel_consulta/perfil.html",
                                controller: 'propuestasController'
                            })
        
    
    
    
    }]);
    
})(window.angular);



(function (ng) {
   
    var mod = ng.module("propuestaModule");
    mod.constant("propuestaContext", "api/propuestas");
    
    mod.controller('propuestasController', ['$scope', '$http', 'propuestaContext',
        
        function ($scope, $http, propuestaContext) {
            // id_operador
            //http://localhost:8080/Alohandes_IT1/rest/personas/operadores/143/propuestas
           $scope.get = function() { 
                   $http.get('data/propuestas.json').then(function (response) {
                    $scope.propuestas = response.data;
                });
                                   }
			
            // http://localhost:8080/Alohandes_IT1/rest/consultas/mayor/mes/apartamento
			$http.get('data/mayores.json').then(function (response) {
                $scope.lista_mayores = response.data;
            });
			
            // http://localhost:8080/Alohandes_IT1/rest/consultas/ingresos/mes/apartamento
			$http.get('data/ingresos.json').then(function (response) {
                $scope.lista_ingresos = response.data;
            });
			
            // http://localhost:8080/Alohandes_IT1/rest/consultas/menor/mes/apartamento
			$http.get('data/menores.json').then(function (response) {
                $scope.lista_menores = response.data;
            });
			
            //http://localhost:8080/Alohandes_IT1/rest/consultas/frecuentes/apartamento
			$http.get('data/frecuentes.json').then(function (response) {
                $scope.lista_frecuentes = response.data;
            });
			
            // http://localhost:8080/Alohandes_IT1/rest/consultas/poca_demanda
			$http.get('data/pocos.json').then(function (response) {
                $scope.lista_pocos = response.data;
            });
            
            // INFORMACION DE UN OPERADOR USUARIO
            // http://localhost:8080/Alohandes_IT1/rest/personas/' + $scope.id_operador
            $http.get('data/operador.json').then(function (response) {
                $scope.operador = response.data;
            });
        }
                                          
    ]);
}
)(window.angular);