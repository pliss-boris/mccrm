(function() {
    'use strict';

    angular
        .module('mccrmApp')
        .controller('SubscriberWalletPropertyDetailController', SubscriberWalletPropertyDetailController);

    SubscriberWalletPropertyDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'SubscriberWalletProperty', 'SubscriberWallet'];

    function SubscriberWalletPropertyDetailController($scope, $rootScope, $stateParams, entity, SubscriberWalletProperty, SubscriberWallet) {
        var vm = this;

        vm.subscriberWalletProperty = entity;

        var unsubscribe = $rootScope.$on('mccrmApp:subscriberWalletPropertyUpdate', function(event, result) {
            vm.subscriberWalletProperty = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
