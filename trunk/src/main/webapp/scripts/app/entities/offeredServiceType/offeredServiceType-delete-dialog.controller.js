'use strict';

angular.module('zetravelcloudApp')
	.controller('OfferedServiceTypeDeleteController', function($scope, $uibModalInstance, entity, OfferedServiceType) {

        $scope.offeredServiceType = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            OfferedServiceType.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
