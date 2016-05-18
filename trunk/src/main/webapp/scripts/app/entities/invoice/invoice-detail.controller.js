'use strict';

angular.module('zetravelcloudApp')
    .controller('InvoiceDetailController', function ($scope, $rootScope, $stateParams, entity, Invoice, Client) {
        $scope.invoice = entity;
        $scope.load = function (id) {
            Invoice.get({id: id}, function(result) {
                $scope.invoice = result;
            });
        };
        var unsubscribe = $rootScope.$on('zetravelcloudApp:invoiceUpdate', function(event, result) {
            $scope.invoice = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
