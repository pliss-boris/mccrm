(function() {
    'use strict';

    angular
        .module('mccrmApp')
        .controller('CustomerAddresController', CustomerAddresController);

    CustomerAddresController.$inject = ['$scope', '$state', 'CustomerAddres'];

    function CustomerAddresController ($scope, $state, CustomerAddres) {
        var vm = this;
        
        vm.customerAddres = [];

        loadAll();

        function loadAll() {
            CustomerAddres.query(function(result) {
                vm.customerAddres = result;
            });
        }
    }
})();
