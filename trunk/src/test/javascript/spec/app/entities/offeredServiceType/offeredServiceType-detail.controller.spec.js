'use strict';

describe('Controller Tests', function() {

    describe('OfferedServiceType Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockOfferedServiceType;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockOfferedServiceType = jasmine.createSpy('MockOfferedServiceType');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'OfferedServiceType': MockOfferedServiceType
            };
            createController = function() {
                $injector.get('$controller')("OfferedServiceTypeDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'zetravelcloudApp:offeredServiceTypeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
