(function() {
    'use strict';

    angular
        .module('mccrmApp')
        .controller('RemarkDialogController', RemarkDialogController);

    RemarkDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Remark', 'Communication'];

    function RemarkDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Remark, Communication) {
        var vm = this;

        vm.remark = entity;
        vm.clear = clear;
        vm.save = save;
        vm.communications = Communication.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.remark.id !== null) {
                Remark.update(vm.remark, onSaveSuccess, onSaveError);
            } else {
                Remark.save(vm.remark, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('mccrmApp:remarkUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
