(function() {
    'use strict';

    angular
        .module('mccrmApp')
        .controller('CustomerDetailController', CustomerDetailController);

    CustomerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Customer', 'Lre', 'CustomerAddres', 'CustomerContact', 'Communication', 'Subscriber'];

    function CustomerDetailController($scope, $rootScope, $stateParams, entity, Customer, Lre, CustomerAddres, CustomerContact, Communication, Subscriber) {
        var vm = this;

        vm.customer = entity;
        vm.customerAdresses = CustomerAddres.queryByCustomerId({id: vm.customer.id});
        vm.customerContact = CustomerContact.queryByCustomerId({id: vm.customer.id});

        var unsubscribe = $rootScope.$on('mccrmApp:customerUpdate', function(event, result) {
            vm.customer = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
