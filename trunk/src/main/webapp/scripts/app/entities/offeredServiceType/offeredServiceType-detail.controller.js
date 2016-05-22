'use strict';

angular.module('zetravelcloudApp')
    .controller('OfferedServiceTypeDetailController', function ($scope, $rootScope, $stateParams, entity, OfferedServiceType) {
        $scope.offeredServiceType = entity;
        $scope.load = function (id) {
            OfferedServiceType.get({id: id}, function(result) {
                $scope.offeredServiceType = result;
            });
        };
        var unsubscribe = $rootScope.$on('zetravelcloudApp:offeredServiceTypeUpdate', function(event, result) {
            $scope.offeredServiceType = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
