(function() {
    'use strict';

    angular
        .module('mccrmApp')
        .controller('SubscriberWalletPropertyDeleteController',SubscriberWalletPropertyDeleteController);

    SubscriberWalletPropertyDeleteController.$inject = ['$uibModalInstance', 'entity', 'SubscriberWalletProperty'];

    function SubscriberWalletPropertyDeleteController($uibModalInstance, entity, SubscriberWalletProperty) {
        var vm = this;

        vm.subscriberWalletProperty = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SubscriberWalletProperty.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
