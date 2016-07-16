(function() {
    'use strict';

    angular
        .module('mccrmApp')
        .controller('LreDetailController', LreDetailController);

    LreDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Lre', 'Customer', 'ClassOfService'];

    function LreDetailController($scope, $rootScope, $stateParams, entity, Lre, Customer, ClassOfService) {
        var vm = this;

        vm.lre = entity;

        var unsubscribe = $rootScope.$on('mccrmApp:lreUpdate', function(event, result) {
            vm.lre = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
