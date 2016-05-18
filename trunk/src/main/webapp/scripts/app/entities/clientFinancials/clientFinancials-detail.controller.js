'use strict';

angular.module('zetravelcloudApp')
    .controller('ClientFinancialsDetailController', function ($scope, $rootScope, $stateParams, entity, ClientFinancials, Client) {
        $scope.clientFinancials = entity;
        $scope.load = function (id) {
            ClientFinancials.get({id: id}, function(result) {
                $scope.clientFinancials = result;
            });
        };
        var unsubscribe = $rootScope.$on('zetravelcloudApp:clientFinancialsUpdate', function(event, result) {
            $scope.clientFinancials = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
