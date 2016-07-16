'use strict';

describe('Controller Tests', function() {

    describe('CustomerAddres Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCustomerAddres, MockCustomer;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCustomerAddres = jasmine.createSpy('MockCustomerAddres');
            MockCustomer = jasmine.createSpy('MockCustomer');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'CustomerAddres': MockCustomerAddres,
                'Customer': MockCustomer
            };
            createController = function() {
                $injector.get('$controller')("CustomerAddresDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'mccrmApp:customerAddresUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
