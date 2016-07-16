(function() {
    'use strict';

    angular
        .module('mccrmApp')
        .controller('RemarkDetailController', RemarkDetailController);

    RemarkDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Remark', 'Communication'];

    function RemarkDetailController($scope, $rootScope, $stateParams, entity, Remark, Communication) {
        var vm = this;

        vm.remark = entity;

        var unsubscribe = $rootScope.$on('mccrmApp:remarkUpdate', function(event, result) {
            vm.remark = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
