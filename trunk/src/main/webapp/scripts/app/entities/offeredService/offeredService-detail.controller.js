'use strict';

angular.module('zetravelcloudApp')
    .controller('OfferedServiceDetailController', function ($scope, $rootScope, $stateParams, entity, OfferedService, Currency, OfferedServiceType, ServiceProvider, TravelRequest) {
        $scope.offeredService = entity;
        $scope.load = function (id) {
            OfferedService.get({id: id}, function(result) {
                $scope.offeredService = result;
            });
        };
        var unsubscribe = $rootScope.$on('zetravelcloudApp:offeredServiceUpdate', function(event, result) {
            $scope.offeredService = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
