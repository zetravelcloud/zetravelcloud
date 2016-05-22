'use strict';

angular.module('zetravelcloudApp').controller('OfferedServiceTypeDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'OfferedServiceType',
        function($scope, $stateParams, $uibModalInstance, entity, OfferedServiceType) {

        $scope.offeredServiceType = entity;
        $scope.load = function(id) {
            OfferedServiceType.get({id : id}, function(result) {
                $scope.offeredServiceType = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('zetravelcloudApp:offeredServiceTypeUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.offeredServiceType.id != null) {
                OfferedServiceType.update($scope.offeredServiceType, onSaveSuccess, onSaveError);
            } else {
                OfferedServiceType.save($scope.offeredServiceType, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
