(function() {
    'use strict';

    angular
        .module('mccrmApp')
        .controller('LreDeleteController',LreDeleteController);

    LreDeleteController.$inject = ['$uibModalInstance', 'entity', 'Lre'];

    function LreDeleteController($uibModalInstance, entity, Lre) {
        var vm = this;

        vm.lre = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Lre.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
