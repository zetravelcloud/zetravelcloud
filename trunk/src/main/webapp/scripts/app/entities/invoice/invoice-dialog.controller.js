'use strict';

angular.module('zetravelcloudApp').controller('InvoiceDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Invoice', 'Client',
        function($scope, $stateParams, $uibModalInstance, entity, Invoice, Client) {

        $scope.invoice = entity;
        $scope.clients = Client.query();
        $scope.load = function(id) {
            Invoice.get({id : id}, function(result) {
                $scope.invoice = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('zetravelcloudApp:invoiceUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.invoice.id != null) {
                Invoice.update($scope.invoice, onSaveSuccess, onSaveError);
            } else {
                Invoice.save($scope.invoice, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
