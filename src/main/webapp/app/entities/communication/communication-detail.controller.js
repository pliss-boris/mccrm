(function() {
    'use strict';

    angular
        .module('mccrmApp')
        .controller('CommunicationDetailController', CommunicationDetailController);

    CommunicationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Communication', 'Customer', 'Remark'];

    function CommunicationDetailController($scope, $rootScope, $stateParams, entity, Communication, Customer, Remark) {
        var vm = this;

        vm.communication = entity;

        var unsubscribe = $rootScope.$on('mccrmApp:communicationUpdate', function(event, result) {
            vm.communication = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
