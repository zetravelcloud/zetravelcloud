'use strict';

describe('Controller Tests', function() {

    describe('Proposal Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockProposal, MockCity, MockTravelRequest;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockProposal = jasmine.createSpy('MockProposal');
            MockCity = jasmine.createSpy('MockCity');
            MockTravelRequest = jasmine.createSpy('MockTravelRequest');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Proposal': MockProposal,
                'City': MockCity,
                'TravelRequest': MockTravelRequest
            };
            createController = function() {
                $injector.get('$controller')("ProposalDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'zetravelcloudApp:proposalUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
