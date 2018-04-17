


(function (ng) {

    var mod = ng.module("loginModule", ['ui.router']);
    
    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
                
        $stateProvider
                .state('enviado', {
							url: "/y",
							templateUrl: "?.html",
                            controller: 'loginController' 
						}) ;
        
    
    
    
    }]);
    
})(window.angular);

(function (ng) {
   
    var mod = ng.module("loginModule");
    mod.constant("loginContext", "api/login");
    
    mod.controller('loginController', ['$scope', '$http', 'loginContext',
        
        function ($scope, $http, loginContext) {
			
            $scope.enviar = function() {
                
                var data ={
                    id: $scope.id,
                    nombre: $scope.nombre,
                    apellido: $scope.apellido,
                    tipo: $scope.tipo,
                    cedula: $scope.cedula,
                    rol: $scope.rol,
                    email: $scope.email
                };
                
                // DIRECCION HTTP 
                $http.post( 'http://localhost:8080/Alohandes_IT1/rest/personas', data ).then(function (response) {
                    
                    $scope.post_data = response.data;
                
                });
                
            }
            
        }
                                          
    ]);
}
)(window.angular);