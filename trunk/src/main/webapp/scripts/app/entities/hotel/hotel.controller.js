'use strict';

angular.module('zetravelcloudApp')
    .controller('HotelController', function ($scope, $state, Hotel, ParseLinks, ProposalService) {

        $scope.hotels = [];
        $scope.searchCriteria = {};
//        $scope.predicate = 'id';
//        $scope.reverse = true;
//        $scope.page = 0;
//        $scope.loadAll = function() {
//            Hotel.query({page: $scope.page, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
//                $scope.links = ParseLinks.parse(headers('link'));
//                for (var i = 0; i < result.length; i++) {
//                    $scope.hotels.push(result[i]);
//                }
//            });
//        };
        $scope.findHotel = function(){
            Hotel.query($scope.searchCriteria.hotel, function(result) {
                 $scope.hotels = result;
            });
            ProposalService.setProposalAttributes($scope.searchCriteria.checkin, $scope.searchCriteria.checkout);

        };
        $scope.reset = function() {
            $scope.page = 0;
            $scope.hotels = [];
            $scope.findHotel();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.findHotel();
        };


        $scope.refresh = function () {
            $scope.reset();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.hotel = {
                name: null,
                address: null,
                stars: null,
                provider: null,
                id: null
            };
        };
        $scope.addHotelToProposal = function(hotel) {
        	ProposalService.addHotelToProposal(hotel);
        }
        $scope.proposalIsEmpty = function(){
        	return ProposalService.proposalIsEmpty();
        }
        $scope.nbOfHotelsInProposal = function(){
        	return ProposalService.nbOfHotelsInProposal();
        }
    });
