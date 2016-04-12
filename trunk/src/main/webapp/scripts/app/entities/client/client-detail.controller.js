'use strict';

angular.module('zetravelcloudApp')
    .controller('ClientDetailController', function ($scope, $rootScope, $stateParams, DataUtils, entity, Client) {
        $scope.client = entity;
        $scope.load = function (id) {
            Client.get({id: id}, function(result) {
                $scope.client = result;
            });
        };
        var unsubscribe = $rootScope.$on('zetravelcloudApp:clientUpdate', function(event, result) {
            $scope.client = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.byteSize = DataUtils.byteSize;
    });
