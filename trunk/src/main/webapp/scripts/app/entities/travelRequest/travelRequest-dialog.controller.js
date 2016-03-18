'use strict';

angular.module('zetravelcloudApp').controller('TravelRequestDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'TravelRequest', 'Client', 'User',
        function($scope, $stateParams, $uibModalInstance, entity, TravelRequest, Client, User) {

        $scope.travelRequest = entity;
        $scope.clients = Client.query();
        $scope.users = User.query();
        $scope.load = function(id) {
            TravelRequest.get({id : id}, function(result) {
                $scope.travelRequest = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('zetravelcloudApp:travelRequestUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.travelRequest.id != null) {
                TravelRequest.update($scope.travelRequest, onSaveSuccess, onSaveError);
            } else {
                TravelRequest.save($scope.travelRequest, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForCheckin = {};

        $scope.datePickerForCheckin.status = {
            opened: false
        };

        $scope.datePickerForCheckinOpen = function($event) {
            $scope.datePickerForCheckin.status.opened = true;
        };
        $scope.datePickerForCheckout = {};

        $scope.datePickerForCheckout.status = {
            opened: false
        };

        $scope.datePickerForCheckoutOpen = function($event) {
            $scope.datePickerForCheckout.status.opened = true;
        };
}]);
