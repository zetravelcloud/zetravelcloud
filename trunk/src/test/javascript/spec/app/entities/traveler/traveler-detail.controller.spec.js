'use strict';

describe('Controller Tests', function() {

    describe('Traveler Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockTraveler, MockTravelerFile;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockTraveler = jasmine.createSpy('MockTraveler');
            MockTravelerFile = jasmine.createSpy('MockTravelerFile');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Traveler': MockTraveler,
                'TravelerFile': MockTravelerFile
            };
            createController = function() {
                $injector.get('$controller')("TravelerDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'zetravelcloudApp:travelerUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
