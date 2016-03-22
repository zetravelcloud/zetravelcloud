'use strict';

angular.module('zetravelcloudApp')
    .controller('RoomDetailController', function ($scope, $rootScope, $stateParams, entity, Room, Currency, Hotel) {
        $scope.room = entity;
        $scope.load = function (id) {
            Room.get({id: id}, function(result) {
                $scope.room = result;
            });
        };
        var unsubscribe = $rootScope.$on('zetravelcloudApp:roomUpdate', function(event, result) {
            $scope.room = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
