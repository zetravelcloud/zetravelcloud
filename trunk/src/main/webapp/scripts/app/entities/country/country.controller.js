'use strict';

angular.module('zetravelcloudApp')
    .controller('CountryController', function ($scope, $state, Country) {

        $scope.countrys = [];
        $scope.loadAll = function() {
            Country.query(function(result) {
               $scope.countrys = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.country = {
                name: null,
                id: null
            };
        };
    });
