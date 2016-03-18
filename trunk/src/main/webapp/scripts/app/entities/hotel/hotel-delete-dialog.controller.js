'use strict';

angular.module('zetravelcloudApp')
	.controller('HotelDeleteController', function($scope, $uibModalInstance, entity, Hotel) {

        $scope.hotel = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Hotel.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
