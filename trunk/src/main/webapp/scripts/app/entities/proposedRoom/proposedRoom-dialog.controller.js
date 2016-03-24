'use strict';

angular.module('zetravelcloudApp').controller('ProposedRoomDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'ProposedRoom', 'Currency', 'Proposal',
        function($scope, $stateParams, $uibModalInstance, entity, ProposedRoom, Currency, Proposal) {

        $scope.proposedRoom = entity;
        $scope.currencys = Currency.query();
        $scope.proposals = Proposal.query();
        $scope.load = function(id) {
            ProposedRoom.get({id : id}, function(result) {
                $scope.proposedRoom = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('zetravelcloudApp:proposedRoomUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.proposedRoom.id != null) {
                ProposedRoom.update($scope.proposedRoom, onSaveSuccess, onSaveError);
            } else {
                ProposedRoom.save($scope.proposedRoom, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
