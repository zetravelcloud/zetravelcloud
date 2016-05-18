'use strict';

angular.module('zetravelcloudApp').controller('TravelerDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Traveler', 'TravelerFile',
        function($scope, $stateParams, $uibModalInstance, entity, Traveler, TravelerFile) {

        $scope.traveler = entity;
        $scope.travelerfiles = TravelerFile.query();
        $scope.load = function(id) {
            Traveler.get({id : id}, function(result) {
                $scope.traveler = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('zetravelcloudApp:travelerUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.traveler.id != null) {
                Traveler.update($scope.traveler, onSaveSuccess, onSaveError);
            } else {
                Traveler.save($scope.traveler, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForDateOfBirth = {};

        $scope.datePickerForDateOfBirth.status = {
            opened: false
        };

        $scope.datePickerForDateOfBirthOpen = function($event) {
            $scope.datePickerForDateOfBirth.status.opened = true;
        };
}]);
