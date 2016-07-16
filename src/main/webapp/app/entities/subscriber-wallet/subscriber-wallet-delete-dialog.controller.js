(function() {
    'use strict';

    angular
        .module('mccrmApp')
        .controller('SubscriberWalletDeleteController',SubscriberWalletDeleteController);

    SubscriberWalletDeleteController.$inject = ['$uibModalInstance', 'entity', 'SubscriberWallet'];

    function SubscriberWalletDeleteController($uibModalInstance, entity, SubscriberWallet) {
        var vm = this;

        vm.subscriberWallet = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SubscriberWallet.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
