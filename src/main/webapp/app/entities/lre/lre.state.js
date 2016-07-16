(function() {
    'use strict';

    angular
        .module('mccrmApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('lre', {
            parent: 'entity',
            url: '/lre',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'mccrmApp.lre.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/lre/lres.html',
                    controller: 'LreController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('lre');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('lre-detail', {
            parent: 'entity',
            url: '/lre/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'mccrmApp.lre.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/lre/lre-detail.html',
                    controller: 'LreDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('lre');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Lre', function($stateParams, Lre) {
                    return Lre.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('lre.new', {
            parent: 'lre',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lre/lre-dialog.html',
                    controller: 'LreDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('lre', null, { reload: true });
                }, function() {
                    $state.go('lre');
                });
            }]
        })
        .state('lre.edit', {
            parent: 'lre',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lre/lre-dialog.html',
                    controller: 'LreDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Lre', function(Lre) {
                            return Lre.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('lre', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('lre.delete', {
            parent: 'lre',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lre/lre-delete-dialog.html',
                    controller: 'LreDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Lre', function(Lre) {
                            return Lre.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('lre', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
