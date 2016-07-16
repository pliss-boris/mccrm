(function() {
    'use strict';

    angular
        .module('mccrmApp')
        .controller('CommunicationDialogController', CommunicationDialogController);

    CommunicationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Communication', 'Customer', 'Remark'];

    function CommunicationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Communication, Customer, Remark) {
        var vm = this;

        vm.communication = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.customers = Customer.query();
        vm.remarks = Remark.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.communication.id !== null) {
                Communication.update(vm.communication, onSaveSuccess, onSaveError);
            } else {
                Communication.save(vm.communication, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('mccrmApp:communicationUpdate', result);
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
