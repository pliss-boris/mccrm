(function() {
    'use strict';

    angular
        .module('mccrmApp')
        .controller('CustomerDetailController', CustomerDetailController);

    CustomerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Customer', 'Lre', 'CustomerAddres', 'CustomerContact'];

    function CustomerDetailController($scope, $rootScope, $stateParams, entity, Customer, Lre, CustomerAddres, CustomerContact) {
        var vm = this;

        vm.customer = entity;

        var unsubscribe = $rootScope.$on('mccrmApp:customerUpdate', function(event, result) {
            vm.customer = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
