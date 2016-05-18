'use strict';

angular.module('zetravelcloudApp').controller('ClientFinancialsDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'ClientFinancials', 'Client',
        function($scope, $stateParams, $uibModalInstance, $q, entity, ClientFinancials, Client) {

        $scope.clientFinancials = entity;
        $scope.clients = Client.query({filter: 'financials-is-null'});
        $q.all([$scope.financials.$promise, $scope.clients.$promise]).then(function() {
            if (!$scope.financials.client || !$scope.financials.client.id) {
                return $q.reject();
            }
            return Client.get({id : $scope.financials.client.id}).$promise;
        }).then(function(client) {
            $scope.clients.push(client);
        });
        $scope.load = function(id) {
            ClientFinancials.get({id : id}, function(result) {
                $scope.clientFinancials = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('zetravelcloudApp:clientFinancialsUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.clientFinancials.id != null) {
                ClientFinancials.update($scope.clientFinancials, onSaveSuccess, onSaveError);
            } else {
                ClientFinancials.save($scope.clientFinancials, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
