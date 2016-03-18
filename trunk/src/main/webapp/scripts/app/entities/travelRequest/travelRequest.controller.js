'use strict';

angular.module('zetravelcloudApp')
    .controller('TravelRequestController', function ($scope, $state, TravelRequest) {

        $scope.travelRequests = [];
        $scope.loadAll = function() {
            TravelRequest.query(function(result) {
               $scope.travelRequests = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.travelRequest = {
                title: null,
                description: null,
                checkin: null,
                checkout: null,
                id: null
            };
        };
    });
