'use strict';

describe('Controller Tests', function() {

    describe('Remark Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockRemark, MockCommunication;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockRemark = jasmine.createSpy('MockRemark');
            MockCommunication = jasmine.createSpy('MockCommunication');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Remark': MockRemark,
                'Communication': MockCommunication
            };
            createController = function() {
                $injector.get('$controller')("RemarkDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'mccrmApp:remarkUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
