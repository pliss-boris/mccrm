(function() {
    'use strict';

    angular
        .module('mccrmApp')
        .controller('CustomerContactDetailController', CustomerContactDetailController);

    CustomerContactDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'CustomerContact', 'Customer'];

    function CustomerContactDetailController($scope, $rootScope, $stateParams, entity, CustomerContact, Customer) {
        var vm = this;

        vm.customerContact = entity;

        var unsubscribe = $rootScope.$on('mccrmApp:customerContactUpdate', function(event, result) {
            vm.customerContact = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
