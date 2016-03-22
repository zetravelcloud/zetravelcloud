'use strict';

angular.module('zetravelcloudApp')
	.controller('RoomDeleteController', function($scope, $uibModalInstance, entity, Room) {

        $scope.room = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Room.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
