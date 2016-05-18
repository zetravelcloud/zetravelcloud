'use strict';

angular.module('zetravelcloudApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('clientFinancials', {
                parent: 'entity',
                url: '/clientFinancialss',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'zetravelcloudApp.clientFinancials.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/clientFinancials/clientFinancialss.html',
                        controller: 'ClientFinancialsController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('clientFinancials');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('clientFinancials.detail', {
                parent: 'entity',
                url: '/clientFinancials/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'zetravelcloudApp.clientFinancials.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/clientFinancials/clientFinancials-detail.html',
                        controller: 'ClientFinancialsDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('clientFinancials');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'ClientFinancials', function($stateParams, ClientFinancials) {
                        return ClientFinancials.get({id : $stateParams.id});
                    }]
                }
            })
            .state('clientFinancials.new', {
                parent: 'clientFinancials',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/clientFinancials/clientFinancials-dialog.html',
                        controller: 'ClientFinancialsDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    creditLimit: null,
                                    balance: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('clientFinancials', null, { reload: true });
                    }, function() {
                        $state.go('clientFinancials');
                    })
                }]
            })
            .state('clientFinancials.edit', {
                parent: 'clientFinancials',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/clientFinancials/clientFinancials-dialog.html',
                        controller: 'ClientFinancialsDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['ClientFinancials', function(ClientFinancials) {
                                return ClientFinancials.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('clientFinancials', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('clientFinancials.delete', {
                parent: 'clientFinancials',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/clientFinancials/clientFinancials-delete-dialog.html',
                        controller: 'ClientFinancialsDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['ClientFinancials', function(ClientFinancials) {
                                return ClientFinancials.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('clientFinancials', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
