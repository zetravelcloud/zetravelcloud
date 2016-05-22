'use strict';

angular.module('zetravelcloudApp')
    .controller('OfferedServiceTypeController', function ($scope, $state, OfferedServiceType) {

        $scope.offeredServiceTypes = [];
        $scope.loadAll = function() {
            OfferedServiceType.query(function(result) {
               $scope.offeredServiceTypes = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.offeredServiceType = {
                name: null,
                id: null
            };
        };
    });
