'use strict';

angular.module('zetravelcloudApp')
    .controller('ProposedRoomDetailController', function ($scope, $rootScope, $stateParams, entity, ProposedRoom, Currency) {
        $scope.proposedRoom = entity;
        $scope.load = function (id) {
            ProposedRoom.get({id: id}, function(result) {
                $scope.proposedRoom = result;
            });
        };
        var unsubscribe = $rootScope.$on('zetravelcloudApp:proposedRoomUpdate', function(event, result) {
            $scope.proposedRoom = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
