'use strict';

angular.module('zetravelcloudApp').controller('ServiceProviderDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'ServiceProvider',
        function($scope, $stateParams, $uibModalInstance, entity, ServiceProvider) {

        $scope.serviceProvider = entity;
        $scope.load = function(id) {
            ServiceProvider.get({id : id}, function(result) {
                $scope.serviceProvider = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('zetravelcloudApp:serviceProviderUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.serviceProvider.id != null) {
                ServiceProvider.update($scope.serviceProvider, onSaveSuccess, onSaveError);
            } else {
                ServiceProvider.save($scope.serviceProvider, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
