(function (ng) {

    var mod = ng.module("operadorModule", ['ui.router']);

    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {

        $urlRouterProvider.otherwise("/x");

        $stateProvider

            .state('propuestas', {
                url: "/operador/:id/propuestas",
                param: {
                    id: null
                },
                views: {
                    'content': {
                        templateUrl: 'operador/propuestas.html',
                        controller: 'operadorController'
                    }
                }
            })

            .state('mayores', {
                url: "/mayores",
                templateUrl: "consultas/panel_mayores.html",
                controller: 'operadorController'
            })

            .state('mayores.mes', {
                url: "/mayores",
                templateUrl: "consultas/mayores_mes.html",
                controller: 'operadorController'
            })

            .state('mayores.semana', {
                url: "/mayores",
                templateUrl: "consultas/mayores_sem.html",
                controller: 'operadorController'
            })

            .state('ingresos', {
                url: "/ingresos",
                templateUrl: "consultas/panel_ingresos.html",
                controller: 'operadorController'
            })

            .state('ingresos.mes', {
                url: "/ingresos/mes",
                templateUrl: "consultas/ingresos_mes.html",
                controller: 'operadorController'
            })

            .state('ingresos.semana', {
                url: "/ingresos/mes",
                templateUrl: "consultas/ingresos_sem.html",
                controller: 'operadorController'
            })

            .state('menores', {
                url: "/menores",
                templateUrl: "consultas/panel_menores.html",
                controller: 'operadorController'
            })

            .state('menores.mes', {
                url: "/menores",
                templateUrl: "consultas/menores_mes.html",
                controller: 'operadorController'
            })

            .state('menores.semana', {
                url: "/menores",
                templateUrl: "consultas/menores_sem.html",
                controller: 'operadorController'
            })

            .state('frecuentes', {
                url: "/frecuentes",
                templateUrl: "consultas/panel_frecuentes.html",
                controller: 'operadorController'
            })

            .state('frecuentes.param', {
                url: "/frecuentes/:f",
                param: {
                    f: null
                },
                templateUrl: "consultas/tabla_f.html",
                controller: 'operadorController'
            })

            .state('pocos', {
                url: "/pocos",
                templateUrl: "consultas/panel_pocos.html",
                controller: 'operadorController'
            })

        ;


	}]);

})(window.angular);



(function (ng) {

    var mod = ng.module("operadorModule");
    mod.constant("operadorContext", "api/operador");

    mod.controller('operadorController', ['$scope', '$http', 'operadorContext', '$state',

		function ($scope, $http, reservasContext, $state) {


            // http://localhost:8080/Alohandes_IT1/rest/consultas/mayor/mes/apartamento
            $http.get('http://localhost:8080/Alohandes_IT1/rest/consultas/mayor/mes/apartamento').then(function (response) {
                $scope.lista_mayores = response.data;
            });

            
            $http.get('http://localhost:8080/Alohandes_IT1/rest/consultas/mayor/semana/apartamento').then(function (response) {
                $scope.lista_mayores_sem = response.data;
            });

            
            // http://localhost:8080/Alohandes_IT1/rest/consultas/ingresos/mes/apartamento
            $http.get('http://localhost:8080/Alohandes_IT1/rest/consultas/ingresos/mes/apartamento').then(function (response) {
                $scope.lista_ingresos = response.data;
            });
            

            // http://localhost:8080/Alohandes_IT1/rest/consultas/ingresos/semana/apartamento
            $http.get('http://localhost:8080/Alohandes_IT1/rest/consultas/ingresos/semana/apartamento').then(function (response) {
                $scope.lista_ingresos_sem = response.data;
            });


            // http://localhost:8080/Alohandes_IT1/rest/consultas/menor/mes/apartamento
            $http.get('http://localhost:8080/Alohandes_IT1/rest/consultas/menor/mes/apartamento').then(function (response) {
                $scope.lista_menores = response.data;
            });
            

            $http.get('http://localhost:8080/Alohandes_IT1/rest/consultas/menor/semana/apartamento').then(function (response) {
                $scope.lista_menores_sem = response.data;
            });


            //http://localhost:8080/Alohandes_IT1/rest/consultas/frecuentes/apartamento
            $http.get('http://localhost:8080/Alohandes_IT1/rest/consultas/frecuentes/apartamento').then(function (response) {
                $scope.lista_frecuentes = response.data;
            });


            // http://localhost:8080/Alohandes_IT1/rest/consultas/poca_demanda
            $http.get('http://localhost:8080/Alohandes_IT1/rest/consultas/poca_demanda').then(function (response) {
                $scope.lista_pocos = response.data;
            });

            

            if (($state.params.id !== undefined) && ($state.params.id !== null)) {

                // 'http://localhost:8080/Alohandes_IT1/rest/personas/operadores/'+ $state.params.id +'/propuestas'
                $http.get('http://localhost:8080/Alohandes_IT1/rest/personas/operadores/' + $state.params.id + '/propuestas').then(function (response) {
                    $scope.props = response.data;
                });

            };


            $scope.deshabilitar = function (id) {

                console.log(id + '<< Se va a deshabilitar')
                $http.put('http://localhost:8080/Alohandes_IT1/rest/propuestas/deshabilitar/' + id).then(function (response) {
                    $scope.operador = response.data;
                });

            }


            if (($state.params.f !== undefined) && ($state.params.f !== null)) {

                console.log('http://localhost:8080/Alohandes_IT1/rest/consultas/frecuentes/' + $state.params.f)

                $http.get('http://localhost:8080/Alohandes_IT1/rest/consultas/frecuentes/' + $state.params.f).then(function (response) {
                    $scope.lista_frecuentes_f = response.data;
                });

            }


	}

	]);
})(window.angular);
