(function() {
    'use strict';

    angular
        .module('mccrmApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('subscriber', {
            parent: 'entity',
            url: '/subscriber',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'mccrmApp.subscriber.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/subscriber/subscribers.html',
                    controller: 'SubscriberController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('subscriber');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('subscriber-detail', {
            parent: 'entity',
            url: '/subscriber/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'mccrmApp.subscriber.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/subscriber/subscriber-detail.html',
                    controller: 'SubscriberDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('subscriber');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Subscriber', function($stateParams, Subscriber) {
                    return Subscriber.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('subscriber.new', {
            parent: 'subscriber',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/subscriber/subscriber-dialog.html',
                    controller: 'SubscriberDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                subscriberType: null,
                                subscriberPaymentClass: null,
                                creationDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('subscriber', null, { reload: true });
                }, function() {
                    $state.go('subscriber');
                });
            }]
        })
        .state('subscriber.edit', {
            parent: 'subscriber',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/subscriber/subscriber-dialog.html',
                    controller: 'SubscriberDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Subscriber', function(Subscriber) {
                            return Subscriber.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('subscriber', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('subscriber.delete', {
            parent: 'subscriber',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/subscriber/subscriber-delete-dialog.html',
                    controller: 'SubscriberDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Subscriber', function(Subscriber) {
                            return Subscriber.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('subscriber', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
