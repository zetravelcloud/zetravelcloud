'use strict';

angular.module('zetravelcloudApp')
	.controller('ClientFinancialsDeleteController', function($scope, $uibModalInstance, entity, ClientFinancials) {

        $scope.clientFinancials = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            ClientFinancials.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
