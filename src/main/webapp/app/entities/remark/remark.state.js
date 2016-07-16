(function() {
    'use strict';

    angular
        .module('mccrmApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('remark', {
            parent: 'entity',
            url: '/remark',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'mccrmApp.remark.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/remark/remarks.html',
                    controller: 'RemarkController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('remark');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('remark-detail', {
            parent: 'entity',
            url: '/remark/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'mccrmApp.remark.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/remark/remark-detail.html',
                    controller: 'RemarkDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('remark');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Remark', function($stateParams, Remark) {
                    return Remark.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('remark.new', {
            parent: 'remark',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/remark/remark-dialog.html',
                    controller: 'RemarkDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                remark: null,
                                type: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('remark', null, { reload: true });
                }, function() {
                    $state.go('remark');
                });
            }]
        })
        .state('remark.edit', {
            parent: 'remark',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/remark/remark-dialog.html',
                    controller: 'RemarkDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Remark', function(Remark) {
                            return Remark.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('remark', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('remark.delete', {
            parent: 'remark',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/remark/remark-delete-dialog.html',
                    controller: 'RemarkDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Remark', function(Remark) {
                            return Remark.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('remark', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
