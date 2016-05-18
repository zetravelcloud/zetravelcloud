'use strict';

angular.module('zetravelcloudApp')
	.controller('TravelerFileDeleteController', function($scope, $uibModalInstance, entity, TravelerFile) {

        $scope.travelerFile = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            TravelerFile.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
