(function() {
    'use strict';

    angular
        .module('mccrmApp')
        .controller('SubscriberWalletPropertyController', SubscriberWalletPropertyController);

    SubscriberWalletPropertyController.$inject = ['$scope', '$state', 'SubscriberWalletProperty'];

    function SubscriberWalletPropertyController ($scope, $state, SubscriberWalletProperty) {
        var vm = this;
        
        vm.subscriberWalletProperties = [];

        loadAll();

        function loadAll() {
            SubscriberWalletProperty.query(function(result) {
                vm.subscriberWalletProperties = result;
            });
        }
    }
})();
