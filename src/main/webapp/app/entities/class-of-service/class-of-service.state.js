(function() {
    'use strict';

    angular
        .module('mccrmApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('class-of-service', {
            parent: 'entity',
            url: '/class-of-service',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'mccrmApp.classOfService.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/class-of-service/class-of-services.html',
                    controller: 'ClassOfServiceController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('classOfService');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('class-of-service-detail', {
            parent: 'entity',
            url: '/class-of-service/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'mccrmApp.classOfService.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/class-of-service/class-of-service-detail.html',
                    controller: 'ClassOfServiceDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('classOfService');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ClassOfService', function($stateParams, ClassOfService) {
                    return ClassOfService.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('class-of-service.new', {
            parent: 'class-of-service',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/class-of-service/class-of-service-dialog.html',
                    controller: 'ClassOfServiceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                description: null,
                                created: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('class-of-service', null, { reload: true });
                }, function() {
                    $state.go('class-of-service');
                });
            }]
        })
        .state('class-of-service.edit', {
            parent: 'class-of-service',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/class-of-service/class-of-service-dialog.html',
                    controller: 'ClassOfServiceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ClassOfService', function(ClassOfService) {
                            return ClassOfService.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('class-of-service', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('class-of-service.delete', {
            parent: 'class-of-service',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/class-of-service/class-of-service-delete-dialog.html',
                    controller: 'ClassOfServiceDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ClassOfService', function(ClassOfService) {
                            return ClassOfService.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('class-of-service', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
