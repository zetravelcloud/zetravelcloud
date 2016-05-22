'use strict';

angular.module('zetravelcloudApp')
    .controller('OfferedServiceController', function ($scope, $state, OfferedService) {

        $scope.offeredServices = [];
        $scope.loadAll = function() {
            OfferedService.query(function(result) {
               $scope.offeredServices = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.offeredService = {
                sellingPrice: null,
                cost: null,
                detailsId: null,
                confirmationDate: null,
                id: null
            };
        };
    });
