'use strict';

angular.module('zetravelcloudApp').controller('ProposalDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Proposal', 'City', 'TravelRequest',
        function($scope, $stateParams, $uibModalInstance, entity, Proposal, City, TravelRequest) {

        $scope.proposal = entity;
        $scope.citys = City.query();
        $scope.travelrequests = TravelRequest.query();
        $scope.load = function(id) {
            Proposal.get({id : id}, function(result) {
                $scope.proposal = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('zetravelcloudApp:proposalUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.proposal.id != null) {
                Proposal.update($scope.proposal, onSaveSuccess, onSaveError);
            } else {
                Proposal.save($scope.proposal, onSaveSuccess, onSaveError);
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
