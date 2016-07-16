'use strict';

describe('Controller Tests', function() {

    describe('Communication Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCommunication, MockCustomer, MockRemark;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCommunication = jasmine.createSpy('MockCommunication');
            MockCustomer = jasmine.createSpy('MockCustomer');
            MockRemark = jasmine.createSpy('MockRemark');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Communication': MockCommunication,
                'Customer': MockCustomer,
                'Remark': MockRemark
            };
            createController = function() {
                $injector.get('$controller')("CommunicationDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'mccrmApp:communicationUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
