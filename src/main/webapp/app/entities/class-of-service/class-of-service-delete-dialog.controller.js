(function() {
    'use strict';

    angular
        .module('mccrmApp')
        .controller('ClassOfServiceDeleteController',ClassOfServiceDeleteController);

    ClassOfServiceDeleteController.$inject = ['$uibModalInstance', 'entity', 'ClassOfService'];

    function ClassOfServiceDeleteController($uibModalInstance, entity, ClassOfService) {
        var vm = this;

        vm.classOfService = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ClassOfService.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
