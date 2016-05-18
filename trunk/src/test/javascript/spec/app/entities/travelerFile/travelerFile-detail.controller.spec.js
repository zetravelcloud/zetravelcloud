'use strict';

describe('Controller Tests', function() {

    describe('TravelerFile Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockTravelerFile, MockTraveler;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockTravelerFile = jasmine.createSpy('MockTravelerFile');
            MockTraveler = jasmine.createSpy('MockTraveler');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'TravelerFile': MockTravelerFile,
                'Traveler': MockTraveler
            };
            createController = function() {
                $injector.get('$controller')("TravelerFileDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'zetravelcloudApp:travelerFileUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
