(function (ng) {

    var mod = ng.module("post_colectivasModule", ['ui.router']);

    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {

    }]);

})(window.angular);

(function (ng) {

    var mod = ng.module("post_colectivasModule");
    mod.constant("post_colectivasContext", "api/post_colectivas");

    mod.controller('post_colectivasController', ['$scope', '$http', 'post_colectivasContext',

        function ($scope, $http, post_colevtivasContext) {

            $scope.invoice = {
                    items: [{
                        id_cliente: 31,
                        id_reserva: 811,
                        cantidad_personas: 2
                    }]
                },

                $scope.addItem = function () {
                    $scope.invoice.items.push({
                        id_cliente: $scope.invoice.items[0].id_cliente + $scope.invoice.items.length,
                        id_reserva: $scope.invoice.items[0].id_reserva + $scope.invoice.items.length,
                        cantidad_personas: 2
                    });
                },

                $scope.removeItem = function (index) {
                    $scope.invoice.items.splice(index, 1);
                },

                $scope.enviar = function () {

                    var us = angular.toJson($scope.invoice.items);
                    console.log(us)
                console.log($scope.servicios);

                    var data = {
                        id: $scope.id,
                        duracion: $scope.duracion,
                        fecha_inicio_estadia: $scope.fecha,
                        tipo_inmueble: $scope.tipo,
                        privacidad: $scope.privacidad,
                        cantidad_inmuebles: $scope.cantidad,
                        servicios_deseados: $scope.servicios,
                        usuarios: us
                    };

                    // DIRECCION HTTP 
                    $http.post('http://localhost:8080/Alohandes_IT1/rest/reservasColectivas', data).then(function (response) {

                        $scope.post_data = response.data;

                    });

                }

        }

    ]);
})(window.angular);
