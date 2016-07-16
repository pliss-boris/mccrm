(function() {
    'use strict';

    angular
        .module('mccrmApp')
        .controller('SubscriberDialogController', SubscriberDialogController);

    SubscriberDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Subscriber', 'Customer', 'ClassOfService', 'SubscriberWallet'];

    function SubscriberDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Subscriber, Customer, ClassOfService, SubscriberWallet) {
        var vm = this;

        vm.subscriber = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.customers = Customer.query();
        vm.classofservices = ClassOfService.query();
        vm.subscriberwallets = SubscriberWallet.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.subscriber.id !== null) {
                Subscriber.update(vm.subscriber, onSaveSuccess, onSaveError);
            } else {
                Subscriber.save(vm.subscriber, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('mccrmApp:subscriberUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.creationDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
