'use strict';

angular.module('zetravelcloudApp')
	.controller('ProposedRoomDeleteController', function($scope, $uibModalInstance, entity, ProposedRoom) {

        $scope.proposedRoom = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            ProposedRoom.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
