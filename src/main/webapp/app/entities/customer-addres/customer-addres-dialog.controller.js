(function() {
    'use strict';

    angular
        .module('mccrmApp')
        .controller('CustomerAddresDialogController', CustomerAddresDialogController);

    CustomerAddresDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CustomerAddres', 'Customer'];

    function CustomerAddresDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CustomerAddres, Customer) {
        var vm = this;

        vm.customerAddres = entity;
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
            if (vm.customerAddres.id !== null) {
                CustomerAddres.update(vm.customerAddres, onSaveSuccess, onSaveError);
            } else {
                CustomerAddres.save(vm.customerAddres, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('mccrmApp:customerAddresUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
