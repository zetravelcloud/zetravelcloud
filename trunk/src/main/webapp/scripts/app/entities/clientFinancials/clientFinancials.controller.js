'use strict';

angular.module('zetravelcloudApp')
    .controller('ClientFinancialsController', function ($scope, $state, ClientFinancials) {

        $scope.clientFinancialss = [];
        $scope.loadAll = function() {
            ClientFinancials.query(function(result) {
               $scope.clientFinancialss = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.clientFinancials = {
                creditLimit: null,
                balance: null,
                id: null
            };
        };
    });
