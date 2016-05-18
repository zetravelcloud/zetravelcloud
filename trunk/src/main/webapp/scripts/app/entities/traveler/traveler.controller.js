'use strict';

angular.module('zetravelcloudApp')
    .controller('TravelerController', function ($scope, $state, Traveler) {

        $scope.travelers = [];
        $scope.loadAll = function() {
            Traveler.query(function(result) {
               $scope.travelers = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.traveler = {
                name: null,
                email: null,
                phone: null,
                dateOfBirth: null,
                id: null
            };
        };
    });
