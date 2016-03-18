'use strict';

describe('Controller Tests', function() {

    describe('TravelRequest Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockTravelRequest, MockClient, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockTravelRequest = jasmine.createSpy('MockTravelRequest');
            MockClient = jasmine.createSpy('MockClient');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'TravelRequest': MockTravelRequest,
                'Client': MockClient,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("TravelRequestDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'zetravelcloudApp:travelRequestUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
