'use strict';

describe('Controller Tests', function() {

    describe('SubscriberWalletProperty Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockSubscriberWalletProperty, MockSubscriberWallet;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockSubscriberWalletProperty = jasmine.createSpy('MockSubscriberWalletProperty');
            MockSubscriberWallet = jasmine.createSpy('MockSubscriberWallet');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'SubscriberWalletProperty': MockSubscriberWalletProperty,
                'SubscriberWallet': MockSubscriberWallet
            };
            createController = function() {
                $injector.get('$controller')("SubscriberWalletPropertyDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'mccrmApp:subscriberWalletPropertyUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
