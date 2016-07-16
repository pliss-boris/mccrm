(function() {
    'use strict';

    angular
        .module('mccrmApp')
        .controller('SubscriberWalletPropertyDialogController', SubscriberWalletPropertyDialogController);

    SubscriberWalletPropertyDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SubscriberWalletProperty', 'SubscriberWallet'];

    function SubscriberWalletPropertyDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SubscriberWalletProperty, SubscriberWallet) {
        var vm = this;

        vm.subscriberWalletProperty = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.subscriberwallets = SubscriberWallet.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.subscriberWalletProperty.id !== null) {
                SubscriberWalletProperty.update(vm.subscriberWalletProperty, onSaveSuccess, onSaveError);
            } else {
                SubscriberWalletProperty.save(vm.subscriberWalletProperty, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('mccrmApp:subscriberWalletPropertyUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.created = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
