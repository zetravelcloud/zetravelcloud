'use strict';

angular.module('zetravelcloudApp')
    .controller('ServiceProviderDetailController', function ($scope, $rootScope, $stateParams, entity, ServiceProvider) {
        $scope.serviceProvider = entity;
        $scope.load = function (id) {
            ServiceProvider.get({id: id}, function(result) {
                $scope.serviceProvider = result;
            });
        };
        var unsubscribe = $rootScope.$on('zetravelcloudApp:serviceProviderUpdate', function(event, result) {
            $scope.serviceProvider = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
