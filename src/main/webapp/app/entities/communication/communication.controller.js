(function() {
    'use strict';

    angular
        .module('mccrmApp')
        .controller('CommunicationController', CommunicationController);

    CommunicationController.$inject = ['$scope', '$state', 'Communication'];

    function CommunicationController ($scope, $state, Communication) {
        var vm = this;
        
        vm.communications = [];

        loadAll();

        function loadAll() {
            Communication.query(function(result) {
                vm.communications = result;
            });
        }
    }
})();
