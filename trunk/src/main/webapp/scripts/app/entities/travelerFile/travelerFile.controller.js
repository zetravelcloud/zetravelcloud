'use strict';

angular.module('zetravelcloudApp')
    .controller('TravelerFileController', function ($scope, $state, DataUtils, TravelerFile) {

        $scope.travelerFiles = [];
        $scope.loadAll = function() {
            TravelerFile.query(function(result) {
               $scope.travelerFiles = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.travelerFile = {
                fileName: null,
                file: null,
                fileContentType: null,
                id: null
            };
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;
    });
