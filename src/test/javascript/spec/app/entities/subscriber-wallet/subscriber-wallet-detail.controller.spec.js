'use strict';

describe('Controller Tests', function() {

    describe('SubscriberWallet Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockSubscriberWallet, MockSubscriber, MockSubscriberWalletProperty;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockSubscriberWallet = jasmine.createSpy('MockSubscriberWallet');
            MockSubscriber = jasmine.createSpy('MockSubscriber');
            MockSubscriberWalletProperty = jasmine.createSpy('MockSubscriberWalletProperty');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'SubscriberWallet': MockSubscriberWallet,
                'Subscriber': MockSubscriber,
                'SubscriberWalletProperty': MockSubscriberWalletProperty
            };
            createController = function() {
                $injector.get('$controller')("SubscriberWalletDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'mccrmApp:subscriberWalletUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
