'use strict';

angular.module('zetravelcloudApp')
    .controller('TravelerFileDetailController', function ($scope, $rootScope, $stateParams, DataUtils, entity, TravelerFile, Traveler) {
        $scope.travelerFile = entity;
        $scope.load = function (id) {
            TravelerFile.get({id: id}, function(result) {
                $scope.travelerFile = result;
            });
        };
        var unsubscribe = $rootScope.$on('zetravelcloudApp:travelerFileUpdate', function(event, result) {
            $scope.travelerFile = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.byteSize = DataUtils.byteSize;
    });
