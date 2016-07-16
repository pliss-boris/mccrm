(function() {
    'use strict';

    angular
        .module('mccrmApp')
        .controller('LreDialogController', LreDialogController);

    LreDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Lre', 'Customer'];

    function LreDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Lre, Customer) {
        var vm = this;

        vm.lre = entity;
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
            if (vm.lre.id !== null) {
                Lre.update(vm.lre, onSaveSuccess, onSaveError);
            } else {
                Lre.save(vm.lre, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('mccrmApp:lreUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
