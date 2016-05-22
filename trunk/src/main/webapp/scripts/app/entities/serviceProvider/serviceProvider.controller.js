'use strict';

angular.module('zetravelcloudApp')
    .controller('ServiceProviderController', function ($scope, $state, ServiceProvider) {

        $scope.serviceProviders = [];
        $scope.loadAll = function() {
            ServiceProvider.query(function(result) {
               $scope.serviceProviders = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.serviceProvider = {
                name: null,
                id: null
            };
        };
    });
