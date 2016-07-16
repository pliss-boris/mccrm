(function() {
    'use strict';

    angular
        .module('mccrmApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('subscriber-wallet', {
            parent: 'entity',
            url: '/subscriber-wallet',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'mccrmApp.subscriberWallet.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/subscriber-wallet/subscriber-wallets.html',
                    controller: 'SubscriberWalletController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('subscriberWallet');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('subscriber-wallet-detail', {
            parent: 'entity',
            url: '/subscriber-wallet/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'mccrmApp.subscriberWallet.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/subscriber-wallet/subscriber-wallet-detail.html',
                    controller: 'SubscriberWalletDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('subscriberWallet');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'SubscriberWallet', function($stateParams, SubscriberWallet) {
                    return SubscriberWallet.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('subscriber-wallet.new', {
            parent: 'subscriber-wallet',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/subscriber-wallet/subscriber-wallet-dialog.html',
                    controller: 'SubscriberWalletDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                activationDate: null,
                                expired: null,
                                created: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('subscriber-wallet', null, { reload: true });
                }, function() {
                    $state.go('subscriber-wallet');
                });
            }]
        })
        .state('subscriber-wallet.edit', {
            parent: 'subscriber-wallet',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/subscriber-wallet/subscriber-wallet-dialog.html',
                    controller: 'SubscriberWalletDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SubscriberWallet', function(SubscriberWallet) {
                            return SubscriberWallet.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('subscriber-wallet', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('subscriber-wallet.delete', {
            parent: 'subscriber-wallet',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/subscriber-wallet/subscriber-wallet-delete-dialog.html',
                    controller: 'SubscriberWalletDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SubscriberWallet', function(SubscriberWallet) {
                            return SubscriberWallet.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('subscriber-wallet', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
