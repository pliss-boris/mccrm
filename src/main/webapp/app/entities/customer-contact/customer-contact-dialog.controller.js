(function() {
    'use strict';

    angular
        .module('mccrmApp')
        .controller('CustomerContactDialogController', CustomerContactDialogController);

    CustomerContactDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CustomerContact', 'Customer'];

    function CustomerContactDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CustomerContact, Customer) {
        var vm = this;

        vm.customerContact = entity;
        vm.clear = clear;
        vm.save = save;
        vm.customers = Customer.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.customerContact.id !== null) {
                CustomerContact.update(vm.customerContact, onSaveSuccess, onSaveError);
            } else {
                CustomerContact.save(vm.customerContact, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('mccrmApp:customerContactUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
