'use strict';

describe('Controller Tests', function() {

    describe('ClientFinancials Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockClientFinancials, MockClient;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockClientFinancials = jasmine.createSpy('MockClientFinancials');
            MockClient = jasmine.createSpy('MockClient');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'ClientFinancials': MockClientFinancials,
                'Client': MockClient
            };
            createController = function() {
                $injector.get('$controller')("ClientFinancialsDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'zetravelcloudApp:clientFinancialsUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
