'use strict';

angular.module('zetravelcloudApp')
	.controller('TravelRequestDeleteController', function($scope, $uibModalInstance, entity, TravelRequest) {

        $scope.travelRequest = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            TravelRequest.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
