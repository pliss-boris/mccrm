(function() {
    'use strict';

    angular
        .module('mccrmApp')
        .controller('CustomerAddresDeleteController',CustomerAddresDeleteController);

    CustomerAddresDeleteController.$inject = ['$uibModalInstance', 'entity', 'CustomerAddres'];

    function CustomerAddresDeleteController($uibModalInstance, entity, CustomerAddres) {
        var vm = this;

        vm.customerAddres = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CustomerAddres.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
