'use strict';

describe('Controller Tests', function() {

    describe('Lre Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockLre, MockCustomer, MockClassOfService;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockLre = jasmine.createSpy('MockLre');
            MockCustomer = jasmine.createSpy('MockCustomer');
            MockClassOfService = jasmine.createSpy('MockClassOfService');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Lre': MockLre,
                'Customer': MockCustomer,
                'ClassOfService': MockClassOfService
            };
            createController = function() {
                $injector.get('$controller')("LreDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'mccrmApp:lreUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
