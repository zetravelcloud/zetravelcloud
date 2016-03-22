'use strict';

angular.module('zetravelcloudApp').controller('RoomDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Room', 'Currency', 'Hotel',
        function($scope, $stateParams, $uibModalInstance, entity, Room, Currency, Hotel) {

        $scope.room = entity;
        $scope.currencys = Currency.query();
        $scope.hotels = Hotel.query();
        $scope.load = function(id) {
            Room.get({id : id}, function(result) {
                $scope.room = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('zetravelcloudApp:roomUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.room.id != null) {
                Room.update($scope.room, onSaveSuccess, onSaveError);
            } else {
                Room.save($scope.room, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
