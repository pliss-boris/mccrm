(function() {
    'use strict';

    angular
        .module('mccrmApp')
        .controller('CustomerContactDeleteController',CustomerContactDeleteController);

    CustomerContactDeleteController.$inject = ['$uibModalInstance', 'entity', 'CustomerContact'];

    function CustomerContactDeleteController($uibModalInstance, entity, CustomerContact) {
        var vm = this;

        vm.customerContact = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CustomerContact.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
