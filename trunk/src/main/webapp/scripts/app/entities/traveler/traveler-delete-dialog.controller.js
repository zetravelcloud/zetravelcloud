'use strict';

angular.module('zetravelcloudApp')
	.controller('TravelerDeleteController', function($scope, $uibModalInstance, entity, Traveler) {

        $scope.traveler = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Traveler.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
