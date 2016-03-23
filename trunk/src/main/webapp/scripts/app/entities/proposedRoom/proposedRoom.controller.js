'use strict';

angular.module('zetravelcloudApp')
    .controller('ProposedRoomController', function ($scope, $state, ProposedRoom) {

        $scope.proposedRooms = [];
        $scope.loadAll = function() {
            ProposedRoom.query(function(result) {
               $scope.proposedRooms = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.proposedRoom = {
                hotelName: null,
                type: null,
                url: null,
                price: null,
                id: null
            };
        };
    });
