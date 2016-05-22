'use strict';

describe('Controller Tests', function() {

    describe('OfferedService Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockOfferedService, MockCurrency, MockOfferedServiceType, MockServiceProvider, MockTravelRequest;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockOfferedService = jasmine.createSpy('MockOfferedService');
            MockCurrency = jasmine.createSpy('MockCurrency');
            MockOfferedServiceType = jasmine.createSpy('MockOfferedServiceType');
            MockServiceProvider = jasmine.createSpy('MockServiceProvider');
            MockTravelRequest = jasmine.createSpy('MockTravelRequest');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'OfferedService': MockOfferedService,
                'Currency': MockCurrency,
                'OfferedServiceType': MockOfferedServiceType,
                'ServiceProvider': MockServiceProvider,
                'TravelRequest': MockTravelRequest
            };
            createController = function() {
                $injector.get('$controller')("OfferedServiceDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'zetravelcloudApp:offeredServiceUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
