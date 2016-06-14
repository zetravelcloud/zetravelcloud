'use strict';

angular.module('zetravelcloudApp').controller('TravelRequestDialogController',
    ['$scope', '$state', '$stateParams', 'entity', 'TravelRequest', 'Client', 'User', 'Traveler',
        function($scope, $state, $stateParams, entity, TravelRequest, Client, User, Traveler) {

        $scope.travelRequest = entity;
//        var travelerEx = {id: 1};
//        $scope.travelRequest.travelers.push(travelerEx);
        $scope.clients = Client.query();
        $scope.users = User.query();
        $scope.load = function(id) {
            TravelRequest.get({id : id}, function(result) {
                $scope.travelRequest = result;
                debugger;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('zetravelcloudApp:travelRequestUpdate', result);
            $scope.isSaving = false;
            $state.go('travelRequest');
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.travelRequest.id != null) {
                TravelRequest.update($scope.travelRequest, onSaveSuccess, onSaveError);
            } else {
                TravelRequest.save($scope.travelRequest, onSaveSuccess, onSaveError);
            }
        };

        $scope.datePickerForCheckin = {};

        $scope.datePickerForCheckin.status = {
            opened: false
        };

        $scope.datePickerForCheckinOpen = function($event) {
            $scope.datePickerForCheckin.status.opened = true;
        };
        $scope.datePickerForCheckout = {};

        $scope.datePickerForCheckout.status = {
            opened: false
        };

        $scope.datePickerForCheckoutOpen = function($event) {
            $scope.datePickerForCheckout.status.opened = true;
        };
        $scope.datePickerForDate = {};

        $scope.datePickerForDate.status = {
            opened: false
        };

        $scope.datePickerForDateOpen = function($event) {
            $scope.datePickerForDate.status.opened = true;
        };
        $scope.datePickerForDateSentToAccounting = {};

        $scope.datePickerForDateSentToAccounting.status = {
            opened: false
        };

        $scope.datePickerForDateSentToAccountingOpen = function($event) {
            $scope.datePickerForDateSentToAccounting.status.opened = true;
        };
        
        $scope.travelers = [];
        $scope.selectedTravelers = [];
        // TODO load travelers using remote-url in the angucomplete-alt
        $scope.loadAllTravelers = function() {
            Traveler.query(function(result) {
               $scope.travelers = result;
            });
        };
        $scope.loadAllTravelers();
        $scope.selectedTraveler = {};
        $scope.selectTraveler = function(selected) {
        	
        	if(!(typeof $scope.travelRequest.travelers != 'undefined' || $scope.travelRequest.travelers instanceof Array)){
        		$scope.travelRequest.travelers = [];
        	}
        	for (var i = 0; i < $scope.travelRequest.travelers.length; i++) {
        		if($scope.travelRequest.travelers[i].id == selected.originalObject.id){
        			return;
        		}
        	}
        	$scope.travelRequest.travelers.push(selected.originalObject);
        }
        
        $scope.removeTraveler = function(index){
        			$scope.travelRequest.travelers.splice(index,1);

        }

}]);
