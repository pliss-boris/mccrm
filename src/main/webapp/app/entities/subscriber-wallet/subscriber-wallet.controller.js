(function() {
    'use strict';

    angular
        .module('mccrmApp')
        .controller('SubscriberWalletController', SubscriberWalletController);

    SubscriberWalletController.$inject = ['$scope', '$state', 'SubscriberWallet'];

    function SubscriberWalletController ($scope, $state, SubscriberWallet) {
        var vm = this;
        
        vm.subscriberWallets = [];

        loadAll();

        function loadAll() {
            SubscriberWallet.query(function(result) {
                vm.subscriberWallets = result;
            });
        }
    }
})();
