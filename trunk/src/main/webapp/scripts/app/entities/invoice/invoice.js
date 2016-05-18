'use strict';

angular.module('zetravelcloudApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('invoice', {
                parent: 'entity',
                url: '/invoices',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'zetravelcloudApp.invoice.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/invoice/invoices.html',
                        controller: 'InvoiceController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('invoice');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('invoice.detail', {
                parent: 'entity',
                url: '/invoice/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'zetravelcloudApp.invoice.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/invoice/invoice-detail.html',
                        controller: 'InvoiceDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('invoice');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Invoice', function($stateParams, Invoice) {
                        return Invoice.get({id : $stateParams.id});
                    }]
                }
            })
            .state('invoice.new', {
                parent: 'invoice',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/invoice/invoice-dialog.html',
                        controller: 'InvoiceDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    totalAmount: null,
                                    paid: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('invoice', null, { reload: true });
                    }, function() {
                        $state.go('invoice');
                    })
                }]
            })
            .state('invoice.edit', {
                parent: 'invoice',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/invoice/invoice-dialog.html',
                        controller: 'InvoiceDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Invoice', function(Invoice) {
                                return Invoice.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('invoice', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('invoice.delete', {
                parent: 'invoice',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/invoice/invoice-delete-dialog.html',
                        controller: 'InvoiceDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Invoice', function(Invoice) {
                                return Invoice.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('invoice', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
