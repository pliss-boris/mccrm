(function() {
    'use strict';

    angular
        .module('mccrmApp')
        .controller('SubscriberDetailController', SubscriberDetailController);

    SubscriberDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Subscriber', 'Customer', 'ClassOfService', 'SubscriberWallet'];

    function SubscriberDetailController($scope, $rootScope, $stateParams, entity, Subscriber, Customer, ClassOfService, SubscriberWallet) {
        var vm = this;

        vm.subscriber = entity;

        var unsubscribe = $rootScope.$on('mccrmApp:subscriberUpdate', function(event, result) {
            vm.subscriber = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
