(function() {
    'use strict';

    angular
        .module('mccrmApp')
        .controller('ClassOfServiceDetailController', ClassOfServiceDetailController);

    ClassOfServiceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'ClassOfService', 'Lre', 'Subscriber'];

    function ClassOfServiceDetailController($scope, $rootScope, $stateParams, entity, ClassOfService, Lre, Subscriber) {
        var vm = this;

        vm.classOfService = entity;

        var unsubscribe = $rootScope.$on('mccrmApp:classOfServiceUpdate', function(event, result) {
            vm.classOfService = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
