(function (ng) {

    var mod = ng.module("post_propuestaModule", ['ui.router']);

    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {

    }]);

})(window.angular);

(function (ng) {

    var mod = ng.module("post_propuestaModule");
    mod.constant("post_propuestaContext", "api/post_propuesta");

    mod.controller('post_propuestaController', ['$scope', '$http', 'post_propuestaContext',

        function ($scope, $http, post_propuestaContext) {

            $scope.date = new Date(),

            $scope.enviar = function () {

                    var data = {
                        id: $scope.id,
                        tipo_inmueble: $scope.tipo_select,
                        capacidad_maxima: $scope.capacidad_maxima,
                        persona: $scope.persona,
                        fecha_final_disponibilidad: null,
                        fecha_inicio_disponibilidad: $scope.fecha_inicio_disponibilidad === null ? $scope.date : $scope.fecha_inicio_disponibilidad,
                        disponible: true,
                        se_va_retirar: false,
                        id_persona: $scope.persona,
                        sub_total: $scope.sub_total,
                        vivienda_universitarias: 
                        $scope.tipo_select === 'vivienda universitaria' ? $scope.id_inmueble : null,
                        apartamento: 
                        $scope.tipo_select === 'apartamento' ? $scope.id_inmueble : null,
                        habitacion:
                        $scope.tipo_select === 'habitacion' ? $scope.id_inmueble : null,
                        hostel:
                        $scope.tipo_select === 'hostel' ? $scope.id_inmueble : null,
                        hotel:
                        $scope.tipo_select === 'hotel' ? $scope.id_inmueble : null,
                        vivienda_express:
                        $scope.tipo_select === 'vivienda express' ? $scope.id_inmueble : null
                    };
                
                console.log(data)

                    // DIRECCION HTTP 
                    $http.post('http://localhost:8080/Alohandes_IT1/rest/personas/operadores/' + $scope.persona + '/propuestas', data).then(function (response) {

                        $scope.post_data = response.data;

                    });

                }

        }

    ]);
})(window.angular);
