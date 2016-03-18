'use strict';

angular.module('zetravelcloudApp')
    .controller('ClientController', function ($scope, $state, Client) {

        $scope.clients = [];
        $scope.loadAll = function() {
            Client.query(function(result) {
               $scope.clients = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.client = {
                name: null,
                email: null,
                mobile: null,
                fax: null,
                id: null
            };
        };
    });
