'use strict';

describe('Controller Tests', function() {

    describe('Customer Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCustomer, MockLre, MockCustomerAddres, MockCustomerContact, MockCommunication, MockSubscriber;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCustomer = jasmine.createSpy('MockCustomer');
            MockLre = jasmine.createSpy('MockLre');
            MockCustomerAddres = jasmine.createSpy('MockCustomerAddres');
            MockCustomerContact = jasmine.createSpy('MockCustomerContact');
            MockCommunication = jasmine.createSpy('MockCommunication');
            MockSubscriber = jasmine.createSpy('MockSubscriber');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Customer': MockCustomer,
                'Lre': MockLre,
                'CustomerAddres': MockCustomerAddres,
                'CustomerContact': MockCustomerContact,
                'Communication': MockCommunication,
                'Subscriber': MockSubscriber
            };
            createController = function() {
                $injector.get('$controller')("CustomerDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'mccrmApp:customerUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
