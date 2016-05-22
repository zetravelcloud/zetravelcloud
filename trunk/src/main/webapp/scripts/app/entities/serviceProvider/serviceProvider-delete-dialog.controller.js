'use strict';

angular.module('zetravelcloudApp')
	.controller('ServiceProviderDeleteController', function($scope, $uibModalInstance, entity, ServiceProvider) {

        $scope.serviceProvider = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            ServiceProvider.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
