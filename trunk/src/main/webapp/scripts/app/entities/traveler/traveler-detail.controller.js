'use strict';

angular.module('zetravelcloudApp')
    .controller('TravelerDetailController', function ($scope, $rootScope, $stateParams, entity, Traveler, TravelerFile) {
        $scope.traveler = entity;
        $scope.load = function (id) {
            Traveler.get({id: id}, function(result) {
                $scope.traveler = result;
            });
        };
        
        $scope.travelerFiles = [];
        $scope.loadTravelerFiles = function() {
            TravelerFile.query(function(result) {
               $scope.travelerFiles = result;
            });
        };
        $scope.loadTravelerFiles();
        
        var unsubscribe = $rootScope.$on('zetravelcloudApp:travelerUpdate', function(event, result) {
            $scope.traveler = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
