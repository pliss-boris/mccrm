(function() {
    'use strict';

    angular
        .module('mccrmApp')
        .controller('CustomerContactController', CustomerContactController);

    CustomerContactController.$inject = ['$scope', '$state', 'CustomerContact'];

    function CustomerContactController ($scope, $state, CustomerContact) {
        var vm = this;
        
        vm.customerContacts = [];

        loadAll();

        function loadAll() {
            CustomerContact.query(function(result) {
                vm.customerContacts = result;
            });
        }
    }
})();
