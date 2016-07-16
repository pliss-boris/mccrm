(function() {
    'use strict';

    angular
        .module('mccrmApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('subscriber-wallet-property', {
            parent: 'entity',
            url: '/subscriber-wallet-property',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'mccrmApp.subscriberWalletProperty.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/subscriber-wallet-property/subscriber-wallet-properties.html',
                    controller: 'SubscriberWalletPropertyController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('subscriberWalletProperty');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('subscriber-wallet-property-detail', {
            parent: 'entity',
            url: '/subscriber-wallet-property/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'mccrmApp.subscriberWalletProperty.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/subscriber-wallet-property/subscriber-wallet-property-detail.html',
                    controller: 'SubscriberWalletPropertyDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('subscriberWalletProperty');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'SubscriberWalletProperty', function($stateParams, SubscriberWalletProperty) {
                    return SubscriberWalletProperty.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('subscriber-wallet-property.new', {
            parent: 'subscriber-wallet-property',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/subscriber-wallet-property/subscriber-wallet-property-dialog.html',
                    controller: 'SubscriberWalletPropertyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                balance: null,
                                created: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('subscriber-wallet-property', null, { reload: true });
                }, function() {
                    $state.go('subscriber-wallet-property');
                });
            }]
        })
        .state('subscriber-wallet-property.edit', {
            parent: 'subscriber-wallet-property',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/subscriber-wallet-property/subscriber-wallet-property-dialog.html',
                    controller: 'SubscriberWalletPropertyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SubscriberWalletProperty', function(SubscriberWalletProperty) {
                            return SubscriberWalletProperty.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('subscriber-wallet-property', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('subscriber-wallet-property.delete', {
            parent: 'subscriber-wallet-property',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/subscriber-wallet-property/subscriber-wallet-property-delete-dialog.html',
                    controller: 'SubscriberWalletPropertyDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SubscriberWalletProperty', function(SubscriberWalletProperty) {
                            return SubscriberWalletProperty.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('subscriber-wallet-property', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
