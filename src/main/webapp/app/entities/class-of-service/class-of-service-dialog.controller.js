(function() {
    'use strict';

    angular
        .module('mccrmApp')
        .controller('ClassOfServiceDialogController', ClassOfServiceDialogController);

    ClassOfServiceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ClassOfService', 'Lre', 'Subscriber'];

    function ClassOfServiceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ClassOfService, Lre, Subscriber) {
        var vm = this;

        vm.classOfService = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.lres = Lre.query();
        vm.subscribers = Subscriber.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.classOfService.id !== null) {
                ClassOfService.update(vm.classOfService, onSaveSuccess, onSaveError);
            } else {
                ClassOfService.save(vm.classOfService, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('mccrmApp:classOfServiceUpdate', result);
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
