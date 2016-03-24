'use strict';

angular.module('zetravelcloudApp')
    .controller('ProposalDetailController', function ($scope, $rootScope, $stateParams, entity, Proposal, City, TravelRequest, ProposedRoom) {
        $scope.proposal = entity;
        $scope.load = function (id) {
            Proposal.get({id: id}, function(result) {
                $scope.proposal = result;
            });
        };
        var unsubscribe = $rootScope.$on('zetravelcloudApp:proposalUpdate', function(event, result) {
            $scope.proposal = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
