(function() {
    'use strict';

    angular
        .module('mccrmApp')
        .controller('SubscriberWalletDetailController', SubscriberWalletDetailController);

    SubscriberWalletDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'SubscriberWallet', 'Subscriber', 'SubscriberWalletProperty'];

    function SubscriberWalletDetailController($scope, $rootScope, $stateParams, entity, SubscriberWallet, Subscriber, SubscriberWalletProperty) {
        var vm = this;

        vm.subscriberWallet = entity;

        var unsubscribe = $rootScope.$on('mccrmApp:subscriberWalletUpdate', function(event, result) {
            vm.subscriberWallet = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
