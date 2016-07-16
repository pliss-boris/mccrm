(function() {
    'use strict';

    angular
        .module('mccrmApp')
        .controller('RemarkDeleteController',RemarkDeleteController);

    RemarkDeleteController.$inject = ['$uibModalInstance', 'entity', 'Remark'];

    function RemarkDeleteController($uibModalInstance, entity, Remark) {
        var vm = this;

        vm.remark = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Remark.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
