'use strict';

angular.module('zetravelcloudApp')
	.controller('ProposalDeleteController', function($scope, $uibModalInstance, entity, Proposal) {

        $scope.proposal = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Proposal.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
