(function() {
    'use strict';

    angular
        .module('mccrmApp')
        .controller('CustomerAddresDetailController', CustomerAddresDetailController);

    CustomerAddresDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'CustomerAddres', 'Customer'];

    function CustomerAddresDetailController($scope, $rootScope, $stateParams, entity, CustomerAddres, Customer) {
        var vm = this;

        vm.customerAddres = entity;

        var unsubscribe = $rootScope.$on('mccrmApp:customerAddresUpdate', function(event, result) {
            vm.customerAddres = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
