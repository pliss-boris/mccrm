(function() {
    'use strict';

    angular
        .module('mccrmApp')
        .controller('RemarkController', RemarkController);

    RemarkController.$inject = ['$scope', '$state', 'Remark'];

    function RemarkController ($scope, $state, Remark) {
        var vm = this;
        
        vm.remarks = [];

        loadAll();

        function loadAll() {
            Remark.query(function(result) {
                vm.remarks = result;
            });
        }
    }
})();
