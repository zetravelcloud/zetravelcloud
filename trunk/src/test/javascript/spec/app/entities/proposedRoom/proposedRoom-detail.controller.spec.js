'use strict';

describe('Controller Tests', function() {

    describe('ProposedRoom Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockProposedRoom, MockCurrency;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockProposedRoom = jasmine.createSpy('MockProposedRoom');
            MockCurrency = jasmine.createSpy('MockCurrency');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'ProposedRoom': MockProposedRoom,
                'Currency': MockCurrency
            };
            createController = function() {
                $injector.get('$controller')("ProposedRoomDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'zetravelcloudApp:proposedRoomUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
