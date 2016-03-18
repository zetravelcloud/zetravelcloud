'use strict';

angular.module('zetravelcloudApp')
    .controller('HotelDetailController', function ($scope, $rootScope, $stateParams, entity, Hotel) {
        $scope.hotel = entity;
        $scope.load = function (id) {
            Hotel.get({id: id}, function(result) {
                $scope.hotel = result;
            });
        };
        var unsubscribe = $rootScope.$on('zetravelcloudApp:hotelUpdate', function(event, result) {
            $scope.hotel = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
