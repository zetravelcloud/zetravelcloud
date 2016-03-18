'use strict';

angular.module('zetravelcloudApp')
    .controller('CurrencyController', function ($scope, $state, Currency) {

        $scope.currencys = [];
        $scope.loadAll = function() {
            Currency.query(function(result) {
               $scope.currencys = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.currency = {
                symbol: null,
                name: null,
                id: null
            };
        };
    });
