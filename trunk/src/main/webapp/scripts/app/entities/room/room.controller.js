'use strict';

angular.module('zetravelcloudApp')
    .controller('RoomController', function ($scope, $state, Room) {

        $scope.rooms = [];
        $scope.loadAll = function() {
            Room.query(function(result) {
               $scope.rooms = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.room = {
                type: null,
                nbOfAdults: null,
                price: null,
                id: null
            };
        };
    });
