(function() {
    'use strict';

    angular
        .module('mccrmApp')
        .controller('SubscriberWalletDialogController', SubscriberWalletDialogController);

    SubscriberWalletDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SubscriberWallet', 'Subscriber', 'SubscriberWalletProperty'];

    function SubscriberWalletDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SubscriberWallet, Subscriber, SubscriberWalletProperty) {
        var vm = this;

        vm.subscriberWallet = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.subscribers = Subscriber.query();
        vm.subscriberwalletproperties = SubscriberWalletProperty.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.subscriberWallet.id !== null) {
                SubscriberWallet.update(vm.subscriberWallet, onSaveSuccess, onSaveError);
            } else {
                SubscriberWallet.save(vm.subscriberWallet, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('mccrmApp:subscriberWalletUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.activationDate = false;
        vm.datePickerOpenStatus.created = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
