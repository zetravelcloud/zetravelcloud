'use strict';

angular.module('zetravelcloudApp')
	.controller('OfferedServiceDeleteController', function($scope, $uibModalInstance, entity, OfferedService) {

        $scope.offeredService = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            OfferedService.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
