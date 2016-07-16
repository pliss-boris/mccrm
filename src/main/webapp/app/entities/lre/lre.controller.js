(function() {
    'use strict';

    angular
        .module('mccrmApp')
        .controller('LreController', LreController);

    LreController.$inject = ['$scope', '$state', 'Lre'];

    function LreController ($scope, $state, Lre) {
        var vm = this;
        
        vm.lres = [];

        loadAll();

        function loadAll() {
            Lre.query(function(result) {
                vm.lres = result;
            });
        }
    }
})();
