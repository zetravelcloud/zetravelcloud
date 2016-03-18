'use strict';

angular.module('zetravelcloudApp')
    .controller('TravelRequestDetailController', function ($scope, $rootScope, $stateParams, entity, TravelRequest, Client, User) {
        $scope.travelRequest = entity;
        $scope.load = function (id) {
            TravelRequest.get({id: id}, function(result) {
                $scope.travelRequest = result;
            });
        };
        var unsubscribe = $rootScope.$on('zetravelcloudApp:travelRequestUpdate', function(event, result) {
            $scope.travelRequest = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
