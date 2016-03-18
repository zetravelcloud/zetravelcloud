'use strict';

angular.module('zetravelcloudApp').controller('HotelDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Hotel',
        function($scope, $stateParams, $uibModalInstance, entity, Hotel) {

        $scope.hotel = entity;
        $scope.load = function(id) {
            Hotel.get({id : id}, function(result) {
                $scope.hotel = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('zetravelcloudApp:hotelUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.hotel.id != null) {
                Hotel.update($scope.hotel, onSaveSuccess, onSaveError);
            } else {
                Hotel.save($scope.hotel, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
