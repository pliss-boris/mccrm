(function() {
    'use strict';

    angular
        .module('mccrmApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('communication', {
            parent: 'entity',
            url: '/communication',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'mccrmApp.communication.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/communication/communications.html',
                    controller: 'CommunicationController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('communication');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('communication-detail', {
            parent: 'entity',
            url: '/communication/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'mccrmApp.communication.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/communication/communication-detail.html',
                    controller: 'CommunicationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('communication');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Communication', function($stateParams, Communication) {
                    return Communication.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('communication.new', {
            parent: 'communication',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/communication/communication-dialog.html',
                    controller: 'CommunicationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                title: null,
                                contactNumber: null,
                                contactPerson: null,
                                contactDescripton: null,
                                created: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('communication', null, { reload: true });
                }, function() {
                    $state.go('communication');
                });
            }]
        })
        .state('communication.edit', {
            parent: 'communication',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/communication/communication-dialog.html',
                    controller: 'CommunicationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Communication', function(Communication) {
                            return Communication.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('communication', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('communication.delete', {
            parent: 'communication',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/communication/communication-delete-dialog.html',
                    controller: 'CommunicationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Communication', function(Communication) {
                            return Communication.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('communication', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
