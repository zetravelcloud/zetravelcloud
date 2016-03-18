'use strict';

angular.module('zetravelcloudApp')
    .controller('CurrencyDetailController', function ($scope, $rootScope, $stateParams, entity, Currency) {
        $scope.currency = entity;
        $scope.load = function (id) {
            Currency.get({id: id}, function(result) {
                $scope.currency = result;
            });
        };
        var unsubscribe = $rootScope.$on('zetravelcloudApp:currencyUpdate', function(event, result) {
            $scope.currency = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
