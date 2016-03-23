'use strict';

angular.module('zetravelcloudApp')
    .controller('CityDetailController', function ($scope, $rootScope, $stateParams, entity, City, Country) {
        $scope.city = entity;
        $scope.load = function (id) {
            City.get({id: id}, function(result) {
                $scope.city = result;
            });
        };
        var unsubscribe = $rootScope.$on('zetravelcloudApp:cityUpdate', function(event, result) {
            $scope.city = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
