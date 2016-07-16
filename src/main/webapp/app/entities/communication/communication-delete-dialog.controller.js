(function() {
    'use strict';

    angular
        .module('mccrmApp')
        .controller('CommunicationDeleteController',CommunicationDeleteController);

    CommunicationDeleteController.$inject = ['$uibModalInstance', 'entity', 'Communication'];

    function CommunicationDeleteController($uibModalInstance, entity, Communication) {
        var vm = this;

        vm.communication = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Communication.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
