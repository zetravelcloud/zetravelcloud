'use strict';

describe('Controller Tests', function() {

    describe('Room Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockRoom, MockCurrency, MockHotel;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockRoom = jasmine.createSpy('MockRoom');
            MockCurrency = jasmine.createSpy('MockCurrency');
            MockHotel = jasmine.createSpy('MockHotel');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Room': MockRoom,
                'Currency': MockCurrency,
                'Hotel': MockHotel
            };
            createController = function() {
                $injector.get('$controller')("RoomDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'zetravelcloudApp:roomUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
