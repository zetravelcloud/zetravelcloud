'use strict';

angular.module('zetravelcloudApp').controller('OfferedServiceDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'OfferedService', 'Currency', 'OfferedServiceType', 'ServiceProvider', 'TravelRequest',
        function($scope, $stateParams, $uibModalInstance, entity, OfferedService, Currency, OfferedServiceType, ServiceProvider, TravelRequest) {

        $scope.offeredService = entity;
        $scope.currencys = Currency.query();
        $scope.offeredservicetypes = OfferedServiceType.query();
        $scope.serviceproviders = ServiceProvider.query();
        $scope.travelrequests = TravelRequest.query();
        $scope.load = function(id) {
            OfferedService.get({id : id}, function(result) {
                $scope.offeredService = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('zetravelcloudApp:offeredServiceUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.offeredService.id != null) {
                OfferedService.update($scope.offeredService, onSaveSuccess, onSaveError);
            } else {
                OfferedService.save($scope.offeredService, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForConfirmationDate = {};

        $scope.datePickerForConfirmationDate.status = {
            opened: false
        };

        $scope.datePickerForConfirmationDateOpen = function($event) {
            $scope.datePickerForConfirmationDate.status.opened = true;
        };
}]);
