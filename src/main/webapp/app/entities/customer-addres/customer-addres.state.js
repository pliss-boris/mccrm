(function() {
    'use strict';

    angular
        .module('mccrmApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('customer-addres', {
            parent: 'entity',
            url: '/customer-addres',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'mccrmApp.customerAddres.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/customer-addres/customer-addres.html',
                    controller: 'CustomerAddresController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('customerAddres');
                    $translatePartialLoader.addPart('adressType');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('customer-addres-detail', {
            parent: 'entity',
            url: '/customer-addres/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'mccrmApp.customerAddres.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/customer-addres/customer-addres-detail.html',
                    controller: 'CustomerAddresDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('customerAddres');
                    $translatePartialLoader.addPart('adressType');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CustomerAddres', function($stateParams, CustomerAddres) {
                    return CustomerAddres.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('customer-addres.new', {
            parent: 'customer-addres',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-addres/customer-addres-dialog.html',
                    controller: 'CustomerAddresDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                adressType: null,
                                country: null,
                                city: null,
                                home: null,
                                homeLetter: null,
                                flat: null,
                                zip: null,
                                isActive: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('customer-addres', null, { reload: true });
                }, function() {
                    $state.go('customer-addres');
                });
            }]
        })
        .state('customer-addres.edit', {
            parent: 'customer-addres',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-addres/customer-addres-dialog.html',
                    controller: 'CustomerAddresDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CustomerAddres', function(CustomerAddres) {
                            return CustomerAddres.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('customer-addres', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('customer-addres.delete', {
            parent: 'customer-addres',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-addres/customer-addres-delete-dialog.html',
                    controller: 'CustomerAddresDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CustomerAddres', function(CustomerAddres) {
                            return CustomerAddres.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('customer-addres', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
