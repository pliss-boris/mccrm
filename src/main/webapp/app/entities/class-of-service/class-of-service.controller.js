(function() {
    'use strict';

    angular
        .module('mccrmApp')
        .controller('ClassOfServiceController', ClassOfServiceController);

    ClassOfServiceController.$inject = ['$scope', '$state', 'ClassOfService'];

    function ClassOfServiceController ($scope, $state, ClassOfService) {
        var vm = this;
        
        vm.classOfServices = [];

        loadAll();

        function loadAll() {
            ClassOfService.query(function(result) {
                vm.classOfServices = result;
            });
        }
    }
})();
