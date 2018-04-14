'use strict';

var app = angular.module('main',['ui.router']);

app.config(['$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider){
				// For any unmatched url, send to /business
				$urlRouterProvider.otherwise("/x")

				$stateProvider
						.state('clientes', {
							url: "/clientes",
							templateUrl: "clientes.html",
                            controller: 'clientesController' 
						})
    
                        .state('reservas', {
							url: "/reservas",
							templateUrl: "panel_reservas.html",
                            controller: function($scope){
                                    $scope.reservas = ["Selling", "Support", "Delivery", "Reparation", "A", "B", "C", "D"];
                                }
						})
    
                        .state('operadores', {
                                url: "/operadores",
                                templateUrl: "operadores.html",
                                controller: function($scope){
                                    $scope.operadores = ["Computer", "Printers", "Phones", "Bags"];
                                }
                            })
    
                        .state('propuestas', {
                                url: "/propuestas",
                                templateUrl: "panel_propuestas.html",
                                controller: function($scope){
                                    $scope.propuestas = ["Computer", "Printers", "Phones", "Bags"];
                                }
                            }) ;
						
			}]);



/* CLIENTES CONTROLLER*/
app.constant("clientesContext", "api/clientes");
app.controller('clientesController', ['$scope', '$http', 'clientesContext',
                                      
                                      function ($scope, $http, clientesContext) {
                                          $http.get('data/clientes.json').then(function (response) {
                                              $scope.clientes = response.data;
                                          });
                                      }
                                      
                                     ]);


/* OPERADORES CONTROLLER */
app.constant("operadoresContext", "api/operadores");
app.controller('operadoresController', ['$scope', '$http', 'operadoresContext',
                                      
                                      function ($scope, $http, operadoresContext) {
                                          $http.get('data/operadores.json').then(function (response) {
                                              $scope.operadores = response.data;
                                          });
                                      }
                                      
                                     ]);

