'use strict';

describe('Controller Tests', function() {

    describe('Subscriber Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockSubscriber, MockCustomer, MockClassOfService, MockSubscriberWallet;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockSubscriber = jasmine.createSpy('MockSubscriber');
            MockCustomer = jasmine.createSpy('MockCustomer');
            MockClassOfService = jasmine.createSpy('MockClassOfService');
            MockSubscriberWallet = jasmine.createSpy('MockSubscriberWallet');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Subscriber': MockSubscriber,
                'Customer': MockCustomer,
                'ClassOfService': MockClassOfService,
                'SubscriberWallet': MockSubscriberWallet
            };
            createController = function() {
                $injector.get('$controller')("SubscriberDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'mccrmApp:subscriberUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
