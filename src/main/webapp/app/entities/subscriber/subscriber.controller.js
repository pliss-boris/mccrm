(function() {
    'use strict';

    angular
        .module('mccrmApp')
        .controller('SubscriberController', SubscriberController);

    SubscriberController.$inject = ['$scope', '$state', 'Subscriber'];

    function SubscriberController ($scope, $state, Subscriber) {
        var vm = this;
        
        vm.subscribers = [];

        loadAll();

        function loadAll() {
            Subscriber.query(function(result) {
                vm.subscribers = result;
            });
        }
    }
})();
