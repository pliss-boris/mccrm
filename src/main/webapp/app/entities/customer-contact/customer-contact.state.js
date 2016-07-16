(function() {
    'use strict';

    angular
        .module('mccrmApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('customer-contact', {
            parent: 'entity',
            url: '/customer-contact',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'mccrmApp.customerContact.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/customer-contact/customer-contacts.html',
                    controller: 'CustomerContactController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('customerContact');
                    $translatePartialLoader.addPart('contactType');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('customer-contact-detail', {
            parent: 'entity',
            url: '/customer-contact/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'mccrmApp.customerContact.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/customer-contact/customer-contact-detail.html',
                    controller: 'CustomerContactDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('customerContact');
                    $translatePartialLoader.addPart('contactType');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CustomerContact', function($stateParams, CustomerContact) {
                    return CustomerContact.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('customer-contact.new', {
            parent: 'customer-contact',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-contact/customer-contact-dialog.html',
                    controller: 'CustomerContactDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                greeting: null,
                                firstName: null,
                                lastName: null,
                                mobile: null,
                                fax: null,
                                email: null,
                                memo: null,
                                contactType: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('customer-contact', null, { reload: true });
                }, function() {
                    $state.go('customer-contact');
                });
            }]
        })
        .state('customer-contact.edit', {
            parent: 'customer-contact',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-contact/customer-contact-dialog.html',
                    controller: 'CustomerContactDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CustomerContact', function(CustomerContact) {
                            return CustomerContact.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('customer-contact', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('customer-contact.delete', {
            parent: 'customer-contact',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-contact/customer-contact-delete-dialog.html',
                    controller: 'CustomerContactDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CustomerContact', function(CustomerContact) {
                            return CustomerContact.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('customer-contact', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
